# WieferichPrime

Finds the smallest Wieferich prime up to `n`. A Wieferich prime is a prime `p` such that `2^(p-1) ≡ 1 (mod p²)`.

## Syntax
```
WieferichPrime(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The upper bound of the search (inclusive) |

## Returns
- The smallest Wieferich prime `p ≤ n`, or `Null` if none exists in range
- An unevaluated `WieferichPrime(n)` for symbolic arguments

## Examples

Finding the first Wieferich prime:
```
Flame> WieferichPrime(1093)
1093
Flame> WieferichPrime(2000)
1093
```

No Wieferich prime in range:
```
Flame> WieferichPrime(1000)
Null
Flame> WieferichPrime(100)
Null
```

Symbolic (unevaluated):
```
Flame> WieferichPrime(x)
WieferichPrime(x)
```

## Notes
- Only two Wieferich primes are known: 1093 and 3511
- The search tests each prime `p` in range using `PowMod(2, p - 1, p^2) == 1`
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [IsPrime](IsPrime.md) — primality test
- [PowMod](PowMod.md) — modular exponentiation
