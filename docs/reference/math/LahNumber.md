# LahNumber

Computes the Lah number L(n, k), which counts the number of ways to partition a set of n elements into k non-empty linearly ordered subsets.

## Syntax
```
LahNumber(n, k)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer |
| `k` | Integer | A non-negative integer with k <= n |

## Returns
An `Integer` — the Lah number L(n, k), equal to $\binom{n-1}{k-1} \cdot \frac{n!}{k!}$.

## Examples
```
Flame> LahNumber(0, 0)
1
Flame> LahNumber(3, 1)
6
Flame> LahNumber(3, 2)
6
Flame> LahNumber(4, 2)
36
Flame> LahNumber(5, 3)
120
Flame> LahNumber(10, 5)
5715360
```

## Notes
- Defined in `combinatorics.flame` using the formula `Binomial(n - 1, k - 1) * (Factorial(n) / Factorial(k))`.
- L(n, 1) = n! for all n >= 1.
- L(n, n) = 1 for all n >= 0.
- Returns unevaluated `LahNumber(n, k)` for non-integer arguments.

## See Also
- [Binomial](../math/Binomial.md) — binomial coefficient
- [Factorial](Factorial.md) — factorial of n
- [StirlingII](StirlingII.md) — Stirling numbers of the second kind
