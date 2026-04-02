# Mod

Computes the modular remainder of two numbers. Uses the Mathematica convention where the result has the same sign as the divisor.

## Syntax
```
Mod(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Any | The dividend |
| `b` | Any | The divisor |

## Returns
- An `IntegerAtom` when both arguments are integers and the result is exact
- A `RationalAtom` when arguments are integers or rationals and the result is not an integer
- A `RealAtom` when either argument is a real
- An unevaluated `Mod(a, b)` for symbolic arguments

## Examples

Basic modular arithmetic:
```
Flame> Mod(5, 3)
2
Flame> Mod(6, 3)
0
Flame> Mod(7, 2)
1
Flame> Mod(3, 7)
3
```

Negative arguments (result sign matches divisor):
```
Flame> Mod(-5, 3)
1
Flame> Mod(5, -3)
-1
Flame> Mod(-5, -3)
-2
```

Rational arguments (exact arithmetic):
```
Flame> Mod(5 / 2, 1)
(1/2)
Flame> Mod(7 / 3, 1 / 2)
(1/3)
```

Real arguments:
```
Flame> Mod(5.5, 2.5)
0.5
```

Symbolic (unevaluated):
```
Flame> Mod(x, 3)
Mod(x, 3)
```

## Notes
- Follows the Mathematica/Python convention: the result has the same sign as the divisor `b`, not the dividend `a`. This differs from Java's `%` operator for negative numbers.
- `Mod(a, b)` is equivalent to $a - \lfloor a / b \rfloor \cdot b$
- Integer and rational arguments use exact long arithmetic — no floating-point involved

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws `ArithmeticException` if the divisor is zero

## See Also
- [Floor](Floor.md) — floor division
- [Ceil](Ceil.md) — ceiling
- [Round](Round.md) — rounding
