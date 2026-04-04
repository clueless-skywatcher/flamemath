# StirlingII

Computes the Stirling number of the second kind S(n, k).

## Syntax
```
StirlingII(n, k)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer (number of elements) |
| `k` | Integer | A non-negative integer (number of non-empty subsets) |

## Returns
An `Integer` — the number of ways to partition a set of `n` elements into exactly `k` non-empty subsets.

## Examples
```
Flame> StirlingII(0, 0)
1
Flame> StirlingII(5, 3)
25
Flame> StirlingII(4, 2)
7
Flame> StirlingII(10, 5)
42525
```

## Notes
- Defined in `combinatorics.flame` using the recurrence `S(n, k) = k * S(n-1, k) + S(n-1, k-1)`.
- `StirlingII(0, 0)` returns `1`, `StirlingII(n, 0)` and `StirlingII(0, k)` return `0` for positive arguments.
- `StirlingII(n, k)` returns `0` when `k > n`.
- Returns unevaluated `StirlingII(n, k)` for non-integer or negative arguments.

## See Also
- [Binomial](Binomial.md) — binomial coefficient
- [CatalanNumber](Catalan.md) — nth Catalan number
