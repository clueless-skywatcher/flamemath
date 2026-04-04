# DivisorSigma

Returns the sum of the $k$-th powers of the divisors of $n$.

## Syntax
```
DivisorSigma(n, k)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number whose divisors to sum |
| `k` | Integer | The power to raise each divisor to |

## Returns
- The sum $\sigma_k(n) = \sum_{d \mid n} d^k$
- An unevaluated `DivisorSigma(n, k)` for symbolic arguments or $n \leq 0$

## Examples

Sum of divisors (k=1):
```
Flame> DivisorSigma(12, 1)
28
Flame> DivisorSigma(28, 1)
56
```

Number of divisors (k=0):
```
Flame> DivisorSigma(12, 0)
6
Flame> DivisorSigma(36, 0)
9
```

Sum of squares of divisors (k=2):
```
Flame> DivisorSigma(6, 2)
50
```

Symbolic (unevaluated):
```
Flame> DivisorSigma(x, 1)
DivisorSigma(x, 1)
```

## Notes
- Uses `Divisors(n)` to generate the divisor list, then maps $d^k$ and sums
- $\sigma_0(n)$ counts divisors, $\sigma_1(n)$ is the classical sum-of-divisors function
- Defined in `ntheory.flame`

## Errors
- Requires exactly 2 arguments

## See Also
- [Divisors](Divisors.md) — list of all divisors
- [EulerPhi](EulerPhi.md) — Euler's totient function
- [MoebiusMu](MoebiusMu.md) — Möbius function
