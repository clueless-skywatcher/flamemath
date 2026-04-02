# ModInverse

Computes the modular multiplicative inverse of $a$ modulo $m$ — the unique integer $x \in [0, m)$ such that $ax \equiv 1 \pmod{m}$.

## Syntax
```
ModInverse(a, m)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Integer | The number to invert |
| `m` | Integer | The modulus (must be positive) |

## Returns
- An integer $x \in [0, m)$ such that $ax \equiv 1 \pmod{m}$
- Returns unevaluated if $\gcd(a, m) \neq 1$ (inverse does not exist), $m \leq 0$, or arguments are non-integer

## Examples

Basic usage:
```
Flame> ModInverse(3, 7)
5
Flame> ModInverse(2, 5)
3
Flame> ModInverse(10, 17)
12
```

Negative input:
```
Flame> ModInverse(-1, 7)
6
Flame> ModInverse(-3, 7)
2
```

No inverse exists (unevaluated):
```
Flame> ModInverse(2, 4)
ModInverse(2, 4)
Flame> ModInverse(6, 9)
ModInverse(6, 9)
```

## Notes
- Uses `ExtGCD` to find the Bézout coefficient, then reduces modulo $m$
- The inverse exists if and only if $\gcd(a, m) = 1$
- Defined in `ntheory.flame`

## See Also
- [ExtGCD](ExtGCD.md) — extended GCD with Bézout coefficients
- [ChineseRemainder](ChineseRemainder.md) — Chinese Remainder Theorem (uses ModInverse internally)
- [Mod](../math/Mod.md) — modular remainder
- [GCD](GCD.md) — greatest common divisor
