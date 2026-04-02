# LiouvilleLambda

Returns the Liouville function $\lambda(n) = (-1)^{\Omega(n)}$, where $\Omega(n)$ is the number of prime factors of $n$ counted with multiplicity.

## Syntax
```
LiouvilleLambda(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A positive integer |

## Returns
- $1$ if $n$ has an even number of prime factors (with multiplicity)
- $-1$ if $n$ has an odd number of prime factors (with multiplicity)
- An unevaluated `LiouvilleLambda(n)` for symbolic, zero, or negative arguments

## Examples

```
Flame> LiouvilleLambda(1)
1
Flame> LiouvilleLambda(2)
-1
Flame> LiouvilleLambda(4)
1
Flame> LiouvilleLambda(8)
-1
Flame> LiouvilleLambda(36)
1
```

Symbolic (unevaluated):
```
Flame> LiouvilleLambda(x)
LiouvilleLambda(x)
```

## Notes
- Computed as `(-1)^Sum(Values(PrimeFactors(n)))`
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [MoebiusMu](MoebiusMu.md) — Möbius function
- [PrimeBigW](PrimeBigW.md) — number of prime factors with multiplicity
- [PrimeFactors](PrimeFactors.md) — prime factorization
