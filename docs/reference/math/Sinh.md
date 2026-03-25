# Sinh

Computes the hyperbolic sine of an expression.

## Syntax
```
Sinh(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The argument |

## Returns
- A numeric atom if the argument is a numeric integer or real
- An unevaluated `Sinh(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Sinh(0)
0
Flame> Sinh(1)
1.1752011936438014
Flame> Sinh(1.5)
2.1292794550948173
```

Symbolic (unevaluated):
```
Flame> Sinh(x)
Sinh(x)
```

## Notes
- Sinh(x) = (Exp(x) - Exp(-x)) / 2
- Sinh(0) = 0
- Numeric evaluation uses `Math.sinh`

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Cosh](Cosh.md) — hyperbolic cosine
- [Tanh](Tanh.md) — hyperbolic tangent
- [Sin](Sin.md) — trigonometric sine
- [Exp](Exp.md) — exponential function
- [Num](Num.md) — force numeric evaluation
