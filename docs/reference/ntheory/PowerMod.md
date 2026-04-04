# PowMod

Computes modular exponentiation: $\text{base}^{\text{exp}} \bmod m$. Handles large values without overflow.

## Syntax
```
PowMod(base, exp, m)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `base` | Integer | The base |
| `exp` | Integer | The exponent |
| `m` | Integer | The modulus |

## Returns
- An `IntegerAtom`: the result of $\text{base}^{\text{exp}} \bmod m$

## Examples

Basic modular exponentiation:
```
Flame> PowMod(2, 10, 1000)
24
Flame> PowMod(2, 4, 5)
1
Flame> PowMod(3, 3, 7)
6
```

Exponent edge cases:
```
Flame> PowMod(2, 0, 5)
1
Flame> PowMod(2, 1, 7)
2
```

Fermat's little theorem ($a^{p-1} \bmod p = 1$ for prime $p$):
```
Flame> PowMod(2, 12, 13)
1
Flame> PowMod(3, 96, 97)
1
```

Wieferich prime verification ($2^{p-1} \bmod p^2 = 1$):
```
Flame> PowMod(2, 1092, 1194649)
1
Flame> PowMod(2, 3510, 12327121)
1
```

Negative base:
```
Flame> PowMod(-2, 3, 5)
2
```

## Notes
- Uses `BigInteger.modPow` internally, so it handles arbitrarily large intermediate values without overflow
- `PowMod(a, 0, m)` returns `1` for any non-zero `m`
- `PowMod(0, n, m)` returns `0` for any positive `n`

## Errors
- Throws `FlameArityException` if not exactly 3 arguments are provided
- Throws `ArithmeticException` if the modulus is zero
- Throws an exception if any argument is not an integer

## See Also
- [Mod](../math/Mod.md) — modular remainder
- [IsPrime](IsPrime.md) — primality test
