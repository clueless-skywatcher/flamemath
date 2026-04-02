# PrimeLittleW

Returns $\omega(n)$, the number of distinct prime factors of $n$.

## Syntax
```
PrimeLittleW(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A positive integer |

## Returns
- The number of distinct prime factors of `n`
- An unevaluated `PrimeLittleW(n)` for symbolic, zero, or negative arguments

## Examples

```
Flame> PrimeLittleW(1)
0
Flame> PrimeLittleW(7)
1
Flame> PrimeLittleW(12)
2
Flame> PrimeLittleW(360)
3
```

Symbolic (unevaluated):
```
Flame> PrimeLittleW(x)
PrimeLittleW(x)
```

## Notes
- Computed as `Len(Keys(PrimeFactors(n)))` — the number of keys in the prime factorization dictionary
- $\omega(12) = 2$ because $12 = 2^2 \cdot 3$, and there are 2 distinct primes
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [PrimeBigW](PrimeBigW.md) — number of prime factors with multiplicity
- [MoebiusMu](MoebiusMu.md) — Möbius function
- [PrimeFactors](PrimeFactors.md) — prime factorization
