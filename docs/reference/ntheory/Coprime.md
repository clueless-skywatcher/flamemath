# Coprime

Checks whether all given integers are pairwise coprime — that is, every pair has $\gcd = 1$.

## Syntax
```
Coprime(a, b)
Coprime(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a, b, ...` | Integer (variadic) | Two or more integers to test |

## Returns
- `True` if all arguments are pairwise coprime
- `False` if any two arguments share a common factor greater than 1
- Returns unevaluated if any argument is non-integer

## Examples

Two arguments:
```
Flame> Coprime(3, 5)
True
Flame> Coprime(6, 9)
False
```

Multiple arguments:
```
Flame> Coprime(3, 5, 7)
True
Flame> Coprime(3, 5, 6)
False
Flame> Coprime(2, 3, 5, 7)
True
```

Symbolic (unevaluated):
```
Flame> Coprime(x, 5)
Coprime(x, 5)
```

## Notes
- Uses the running-product GCD trick: checks $\gcd(\text{product\_so\_far}, \text{next}) = 1$ at each step, which is $O(n)$ GCD calls rather than $O(n^2)$ pairwise checks
- A single argument trivially returns `True`
- Defined in `ntheory.flame`

## See Also
- [GCD](GCD.md) — greatest common divisor
- [ChineseRemainder](ChineseRemainder.md) — CRT (requires pairwise coprime moduli)
- [EulerPhi](EulerPhi.md) — Euler's totient function
