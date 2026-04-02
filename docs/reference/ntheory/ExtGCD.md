# ExtGCD

Computes the extended greatest common divisor. Returns the GCD along with Bézout coefficients such that $c_1 n_1 + c_2 n_2 + \cdots = \gcd$.

## Syntax
```
ExtGCD(a, b)
ExtGCD(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a, b, ...` | Integer (variadic) | Two or more integers |

## Returns
- A list `[g, [c1, c2, ...]]` where `g` is the GCD and `c1, c2, ...` are Bézout coefficients satisfying $c_1 a + c_2 b + \cdots = g$
- The GCD is always non-negative
- Returns unevaluated if any argument is non-integer

## Examples

Two arguments:
```
Flame> ExtGCD(12, 8)
[4, [1, -1]]
Flame> ExtGCD(35, 23)
[1, [2, -3]]
Flame> ExtGCD(6, 18)
[6, [1, 0]]
```

Negative arguments:
```
Flame> ExtGCD(-12, 8)
[4, [-1, -1]]
Flame> ExtGCD(12, -8)
[4, [1, 1]]
```

Multiple arguments:
```
Flame> ExtGCD(6, 15, 30)
[3, [-2, 1, 0]]
Flame> ExtGCD(12, 18, 24, 30)
[6, [-1, 1, 0, 0]]
```

Symbolic arguments return unevaluated:
```
Flame> ExtGCD(x, 5)
ExtGCD(x, 5)
```

## Notes
- Requires at least two arguments
- Uses the extended Euclidean algorithm pairwise, chaining coefficients across all arguments
- Equivalent to Mathematica's `ExtendedGCD`

## See Also
- [GCD](GCD.md) — greatest common divisor (without Bézout coefficients)
- [Mod](../math/Mod.md) — modular remainder
