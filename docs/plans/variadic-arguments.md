# Variadic Arguments Implementation Plan

**Syntax:** `(a, b, ...rest) => body`

The `...rest` parameter collects all remaining arguments into a list. Only one rest parameter is allowed and it must be the last parameter.

---

## Examples

```
F = (first, ...rest) => rest;
F(1, 2, 3, 4)        → [2, 3, 4]

Sum = (x, ...xs) => If(Eq(Len(xs), 0), x, x + Sum(Head(xs), ...Tail(xs)));
```

---

## Pipeline Changes

### 1. Lexer (`FMLexer.java`)

Add a new token type `DOTDOTDOT` (`...`).

In `lexOperatorOrDelimiter()`, when the current character is `.`, peek ahead for two more `.` characters. If found, emit `DOTDOTDOT`. Otherwise, fall through to existing behavior.

**Token type addition** in `FMTokenType.java`:
```java
DOTDOTDOT,  // ...
```

### 2. Parser (`FlameParser.java`)

In the `LPAREN` branch of `parsePrefix()`, after parsing comma-separated params and before the `FAT_ARROW` check:

- When iterating params to build the `symbols` list, check if the last element was preceded by a `DOTDOTDOT` token.
- If so, mark it as a rest parameter.

Two options for AST representation:

**Option A — New AST node:** Create a `RestParam` wrapper that extends `Expr`, wrapping a `Symbol`. The parser emits `RestParam(Symbol("rest"))` for `...rest`. `Flambda` keeps its `List<Symbol>` but the last entry is special-cased.

**Option B — Flag on Flambda:** Add a `boolean variadic` field to the `Flambda` record. When `true`, the last param in the list is the rest parameter.

**Recommended: Option B.** It avoids a new AST node and keeps changes localized. The `Flambda` record becomes:

```java
record Flambda(
    List<Symbol> params,
    Expr body,
    FlameVironment env,
    boolean variadic
) implements Expr
```

**Parser validation:**
- `...` is only allowed before the last parameter. Error otherwise.
- At most one `...` parameter per lambda.

### 3. Evaluator (`FlameValuator.java`)

Modify `applyLambda()`:

```
if lambda is variadic:
    requiredCount = params.size() - 1
    if args.size() < requiredCount:
        throw FlameArityException
    bind params[0..requiredCount-1] to args[0..requiredCount-1]
    bind params[last] to ListExpr(args[requiredCount..])
else:
    (existing exact-arity logic)
```

Also update the **overloaded dispatch** logic (the `Seq` of lambdas branch). A variadic clause should match when `args.size() >= params.size() - 1`. Non-variadic clauses should still be checked first for exact matches.

### 4. Printer (`ExprPrinter.java`)

Update `Flambda` printing to emit `...` before the last param when `variadic` is true, so round-tripping works:

```
(a, b, ...rest) => body
```

---

## Files to Change

| File | Change |
|------|--------|
| `FMTokenType.java` | Add `DOTDOTDOT` |
| `FMLexer.java` | Recognize `...` |
| `Flambda.java` | Add `boolean variadic` field |
| `FlameParser.java` | Parse `...param`, set variadic flag |
| `FlameValuator.java` | Rest-arg binding in `applyLambda()`, overload dispatch |
| `ExprPrinter.java` | Print `...` prefix for variadic lambdas |

---

## Test Cases

**Parsing:**
- `(a, ...rest) => rest` — parses successfully
- `(...rest) => rest` — all-variadic (0 required params)
- `(a, ...b, c) => body` — parser error (rest not last)
- `(a, ...b, ...c) => body` — parser error (multiple rest params)

**Evaluation:**
- `((a, ...rest) => rest)(1, 2, 3)` → `[2, 3]`
- `((a, ...rest) => rest)(1)` → `[]` (empty rest)
- `((a, ...rest) => a)(1, 2, 3)` → `1`
- `((...all) => Len(all))(1, 2, 3)` → `3`
- Arity error when fewer args than required params

**Overloaded dispatch:**
- Non-variadic clause preferred on exact arity match
- Variadic clause used as fallback

**Printing:**
- Round-trip: printed form re-parses correctly
