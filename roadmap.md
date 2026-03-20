# FlameMath 1.0 Roadmap

## Milestone 1 — Core Language Completeness

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 1 | Comparison builtins | `Eq`, `NotEq`, `Less`, `LessEq`, `Greater`, `GreaterEq` | Done |
| 2 | Logical builtins | `And`, `Or`, `Not` — short-circuit evaluation | Done |
| 3 | `While` loop | `While(cond, body)` — only looping construct needed for v1 | Done |
| 4 | `Return()` | Exception-based early exit from blocks/lambdas | Done |
| 5 | `Print()` | Output without returning — essential for loops and debugging | Done |
| 6 | Curly-brace blocks | `{ stmt1; stmt2 }` desugars to `Seq(...)` | Done |

## Milestone 2 — Lists

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 7 | `At` builtin | Evaluate `x[0]` indexing at runtime | Done |
| 8 | `Len` | `Len([1,2,3])` → `3` | Done |
| 9 | `Append` / `Prepend` / `Extend` | Build lists incrementally | Done |
| 10 | `Map` | `Map(f, [1,2,3])` → `[f(1), f(2), f(3)]` | Done |
| 11 | `Range` | `Range(1, 5)` → `[1, 2, 3, 4, 5]` | Done |
| 12 | `Table` | `Table(f(i), i, 1, 10)` — Mathematica-style list generation | TODO |

## Milestone 3 — Math Builtins

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 13 | Trig functions | `Sin`, `Cos`, `Tan` — numeric for numbers, symbolic for symbols | TODO |
| 14 | `Sqrt`, `Abs`, `Log`, `Exp` | Core math functions | TODO |
| 15 | `Mod` | Modular arithmetic | TODO |
| 16 | `N()` | Force numeric evaluation (e.g. `N(Sqrt(2))` → `1.41421...`) | TODO |
| 17 | `Floor`, `Ceil`, `Round` | Rounding functions | TODO |
| 18 | Constants | `Pi`, `E` as built-in symbols with numeric values via `N()` | TODO |

## Milestone 4 — Usability

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 19 | `%` / last result | Reference the previous evaluation result | TODO |
| 20 | Multi-line input | Detect incomplete expressions and continue reading | TODO |
| 21 | Error positions | Include line/column info in parse errors | TODO |
| 22 | `Type()` | Runtime type checking — `Type(42)` → `"Integer"` | TODO |
| 23 | String builtins | `ToString`, `StringJoin` — minimal string support | TODO |

## Already Done

| Feature | Description |
|---------|-------------|
| Expression system | Sealed `Expr` interface with 8 record types |
| Arithmetic | `Add`, `Mul`, `Pow` with symbolic simplification |
| Comparisons | `Eq`, `NotEq`, `Less`, `LessEq`, `Greater`, `GreaterEq` |
| Control flow | `If(cond, then, else)` |
| Assignment | `Set` (`x = 5`) |
| Lambdas | `(x) => x + 1`, closures, arity-based overloading |
| Sequences | `Seq` (`;` operator) |
| Canonical ordering | `CanonicalComparator` for deterministic output |
| Expression printer | Precedence-aware infix printing |
| Parser | Pratt parser with all operators, lists, indexing, lambdas |
| `Head`, `Exit` | Introspection and REPL exit |
