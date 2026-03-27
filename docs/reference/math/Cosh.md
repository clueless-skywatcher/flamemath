# Cosh

Computes the hyperbolic cosine of an expression.

## Syntax
```
Cosh(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The argument |

## Returns
- A numeric atom if the argument is a numeric integer or real
- An unevaluated `Cosh(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Cosh(0)
1
Flame> Cosh(1)
1.5430806348152437
Flame> Cosh(1.5)
2.352409615243247
```

Symbolic (unevaluated):
```
Flame> Cosh(x)
Cosh(x)
```

## Notes
- Cosh(x) = (Exp(x) + Exp(-x)) / 2
- Cosh(0) = 1
- Cosh(x) >= 1 for all real x
- Numeric evaluation uses `Math.cosh`

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Sinh](Sinh.md) — hyperbolic sine
- [Tanh](Tanh.md) — hyperbolic tangent
- [Cos](Cos.md) — trigonometric cosine
- [Exp](Exp.md) — exponential function
- [Num](Num.md) — force numeric evaluation
