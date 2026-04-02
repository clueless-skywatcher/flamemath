# ChineseRemainder

Solves a system of simultaneous congruences using the Chinese Remainder Theorem. Given remainders `[a1, a2, ...]` and moduli `[m1, m2, ...]`, finds the unique `x` in `[0, M)` such that `x ≡ ai (mod mi)` for all `i`, where `M = m1 * m2 * ...`.

## Syntax
```
ChineseRemainder(remainders, moduli)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `remainders` | List of integers | The remainders `[a1, a2, ...]` |
| `moduli` | List of integers | The moduli `[m1, m2, ...]` (must be pairwise coprime and positive) |

## Returns
- An integer `x` in `[0, M)` satisfying all congruences, where `M` is the product of the moduli
- Returns unevaluated if moduli are not pairwise coprime, lists differ in length, or any argument is non-integer

## Examples

Two congruences:
```
Flame> ChineseRemainder([2, 3], [3, 5])
8
Flame> ChineseRemainder([1, 2], [3, 5])
7
```

Three congruences (`x ≡ 2 mod 3, x ≡ 3 mod 5, x ≡ 2 mod 7`):
```
Flame> ChineseRemainder([2, 3, 2], [3, 5, 7])
23
```

Four congruences:
```
Flame> ChineseRemainder([1, 2, 3, 4], [5, 7, 11, 13])
4216
```

Non-coprime moduli (unevaluated):
```
Flame> ChineseRemainder([1, 2], [4, 6])
ChineseRemainder([1, 2], [4, 6])
```

## Notes
- The moduli must be pairwise coprime; use `Coprime` to check this
- Uses `ModInverse` to compute modular inverses of partial products
- Defined in `ntheory.flame`

## See Also
- [ModInverse](ModInverse.md) — modular multiplicative inverse
- [Coprime](Coprime.md) — pairwise coprimality check
- [ExtGCD](ExtGCD.md) — extended GCD with Bezout coefficients
- [Mod](../math/Mod.md) — modular remainder
