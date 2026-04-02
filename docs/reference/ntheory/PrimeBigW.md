# PrimeBigW

Returns $\Omega(n)$, the number of prime factors of $n$ counted with multiplicity.

## Syntax
```
PrimeBigW(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A positive integer |

## Returns
- The total number of prime factors of `n`, counting multiplicity
- An unevaluated `PrimeBigW(n)` for symbolic, zero, or negative arguments

## Examples

```
Flame> PrimeBigW(1)
0
Flame> PrimeBigW(7)
1
Flame> PrimeBigW(12)
3
Flame> PrimeBigW(360)
6
```

Symbolic (unevaluated):
```
Flame> PrimeBigW(x)
PrimeBigW(x)
```

## Notes
- Computed as `Sum(Values(PrimeFactors(n)))` — the sum of all exponents in the prime factorization
- $\Omega(12) = 3$ because $12 = 2^2 \cdot 3$, and $2 + 1 = 3$
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [PrimeLittleW](PrimeLittleW.md) — number of distinct prime factors
- [LiouvilleLambda](LiouvilleLambda.md) — Liouville function ($(-1)^{\Omega(n)}$)
- [PrimeFactors](PrimeFactors.md) — prime factorization
