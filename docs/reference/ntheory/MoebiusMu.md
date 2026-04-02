# MoebiusMu

Returns the Möbius function $\mu(n)$. Returns $0$ if $n$ has a squared prime factor, $(-1)^k$ if $n$ is a product of $k$ distinct primes, and $1$ if $n = 1$.

## Syntax
```
MoebiusMu(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A positive integer |

## Returns
- $1$ if $n = 1$
- $(-1)^k$ if $n$ is a product of $k$ distinct primes
- $0$ if $n$ has a squared prime factor
- An unevaluated `MoebiusMu(n)` for symbolic, zero, or negative arguments

## Examples

Squarefree with odd number of prime factors:
```
Flame> MoebiusMu(2)
-1
Flame> MoebiusMu(30)
-1
```

Squarefree with even number of prime factors:
```
Flame> MoebiusMu(6)
1
Flame> MoebiusMu(10)
1
```

Has squared factor:
```
Flame> MoebiusMu(4)
0
Flame> MoebiusMu(12)
0
```

Edge case:
```
Flame> MoebiusMu(1)
1
```

Symbolic (unevaluated):
```
Flame> MoebiusMu(x)
MoebiusMu(x)
```

## Notes
- Computed via $\delta(\Omega(n), \omega(n)) \cdot (-1)^{\Omega(n)}$ using a single `PrimeFactors` call
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [LiouvilleLambda](LiouvilleLambda.md) — Liouville function
- [PrimeBigW](PrimeBigW.md) — number of prime factors with multiplicity
- [PrimeLittleW](PrimeLittleW.md) — number of distinct prime factors
- [PrimeFactors](PrimeFactors.md) — prime factorization
