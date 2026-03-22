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

### 1. Lexer — Done

The `TRIPLE_DOT` token (`...`) already existed in `FMTokenType.java` and `FMLexer.java`. The `'.'` case was moved to a dedicated `lexVariadic()` method, and errors for bare `.` and `..` were added.

### 2. Parser — Done

- `TRIPLE_DOT` is handled as a prefix operator in `parsePrefix()`, yielding a `VariadicArgument(symbol)` record.
- In the `LPAREN` branch, `parseCommaSeparated()` naturally picks up `VariadicArgument` nodes. When `FAT_ARROW` follows, the param list is iterated: `Symbol` nodes become regular params, a `VariadicArgument` sets `variadic = true` on the `Flambda` and must be last.
- `Flambda` record has a new `boolean variadic` field.
- `VariadicArgument` is a new record implementing the sealed `Expr` interface.

### 3. Evaluator (`FlameValuator.java`) — TODO

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

### 4. Printer (`ExprPrinter.java`) — TODO

Update `Flambda` printing to emit `...` before the last param when `variadic` is true, so round-tripping works:

```
(a, b, ...rest) => body
```

---

## Files Changed

| File | Change | Status |
|------|--------|--------|
| `FMLexer.java` | Moved `...` recognition to `lexVariadic()` | Done |
| `FMTokenType.java` | `TRIPLE_DOT` already existed | — |
| `VariadicArgument.java` | New `Expr` record wrapping a `Symbol` | Done |
| `Expr.java` | Added `VariadicArgument` to sealed permits | Done |
| `Flambda.java` | Added `boolean variadic` field | Done |
| `FlameParser.java` | `TRIPLE_DOT` prefix → `VariadicArgument`; LPAREN branch detects it | Done |
| `FlameValuator.java` | Rest-arg binding in `applyLambda()`, overload dispatch | TODO |
| `ExprPrinter.java` | Print `...` prefix for variadic lambdas | TODO |

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
