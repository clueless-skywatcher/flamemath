# CatalanNumber

Computes the nth CatalanNumber number.

## Syntax
```
CatalanNumber(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer |

## Returns
An `Integer` — the nth CatalanNumber number, equal to $\frac{1}{n+1}\binom{2n}{n}$.

## Examples
```
Flame> CatalanNumber(0)
1
Flame> CatalanNumber(1)
1
Flame> CatalanNumber(5)
42
Flame> CatalanNumber(10)
16796
```

## Notes
- Defined in `combinatorics.flame` using the formula `(1 / (n + 1)) * Binomial(2*n, n)`.
- Returns unevaluated `CatalanNumber(n)` for non-integer arguments.
- The first few CatalanNumber numbers are: 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, ...

## See Also
- [Binomial](Binomial.md) — binomial coefficient
- [Factorial](Factorial.md) — factorial of n
