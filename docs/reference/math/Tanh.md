# Tanh

Computes the hyperbolic tangent of an expression.

## Syntax
```
Tanh(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The argument |

## Returns
- A numeric atom if the argument is a numeric integer or real
- An unevaluated `Tanh(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Tanh(0)
0
Flame> Tanh(1)
0.7615941559557649
Flame> Tanh(0.5)
0.46211715726000974
```

Symbolic (unevaluated):
```
Flame> Tanh(x)
Tanh(x)
```

## Notes
- Tanh(x) = Sinh(x) / Cosh(x)
- Tanh(0) = 0
- Range is (-1, 1) for all real x
- Numeric evaluation uses `Math.tanh`

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Sinh](Sinh.md) — hyperbolic sine
- [Cosh](Cosh.md) — hyperbolic cosine
- [Tan](Tan.md) — trigonometric tangent
- [Exp](Exp.md) — exponential function
- [Num](Num.md) — force numeric evaluation
