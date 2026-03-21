# FlameMath Roadmap

## v1.1.0 — Quality of Life & Completeness

### Language Features (Java)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 1 | Comments | `//` line comments — token already lexed, needs parser support | TODO |
| 2 | `For` loop | `For(var, list, body)` — iterate over lists | TODO |

### List Functions (Java builtins)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 3 | `Sort` | `Sort([3,1,2])` → `[1,2,3]`, optional comparator | TODO |
| 4 | `Slice` | `Slice(list, start, end)` — sublist extraction | TODO |

### List Functions (stdlib)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 5 | `Reverse` | `Reverse([1,2,3])` → `[3,2,1]` — via Fold + Prepend | TODO |
| 6 | `Flatten` | `Flatten([[1,2],[3,[4]]])` → `[1,2,3,4]` — recursive with IsList | TODO |
| 7 | `Zip` | `Zip([1,2],[3,4])` → `[[1,3],[2,4]]` — via Range + Map | TODO |

### String Functions (Java builtins)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 8 | `StrJoin` | `StrJoin(["a","b","c"], ",")` → `"a,b,c"` | TODO |
| 9 | `SubStr` | `SubStr("hello", 1, 3)` → `"ell"` | TODO |
| 10 | `StrSplit` | `StrSplit("a,b,c", ",")` → `["a","b","c"]` | TODO |

### Math (stdlib)

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 11 | `Min` / `Max` | Work on both two args and lists — via Fold | TODO |
| 12 | `Product` | `Product([2,3,4])` → `24` — via Fold with Mul | TODO |