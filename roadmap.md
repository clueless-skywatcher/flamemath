# FlameMath Roadmap

## v1.1.0 — Quality of Life & Completeness

### Language Features (Java)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 1 | Comments | `//` line comments — token already lexed, needs parser support | Done |
| 2 | `For` loop | `For(var, list, body)` — iterate over lists | Done |
| 3 | Variadic arguments | `(a, b, ...rest) => body` — rest parameter collects remaining args into a list | Done |

### List Functions (Java builtins)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 4 | `Sort` | `Sort([3,1,2])` → `[1,2,3]`, optional comparator | Done |
| 5 | `Slice` | `Slice(list, start, end)` — sublist extraction | Done |

### List Functions (stdlib)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 6 | `Reverse` | `Reverse([1,2,3])` → `[3,2,1]` — via While + Prepend | Done |
| 7 | `Flatten` | `Flatten([[1,2],[3,[4]]])` → `[1,2,3,4]` — recursive with IsList | Done |
| 8 | `Zip` | `Zip([1,2],[3,4])` → `[[1,3],[2,4]]` — via Range + Map | Done |
| 9 | `Outer` | `Outer(f, [a,b], [x,y])` → `[[f(a,x),f(a,y)],[f(b,x),f(b,y)]]` — via GenList + Map | Done |

### String Functions (Java builtins)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 10 | `StrJoin` | `StrJoin(["a","b","c"], ",")` → `"a,b,c"` | Done |
| 11 | `SubStr` | `SubStr("hello", 1, 3)` → `"ell"` | Done |
| 12 | `StrSplit` | `StrSplit("a,b,c", ",")` → `["a","b","c"]` | Done |

### Math (stdlib)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 13 | `Min` / `Max` | Work on both two args and lists — via Fold | Done |
| 14 | `Product` | `Product([2,3,4])` → `24` — via Fold with Mul | Done |