# ArcCos

Computes the inverse cosine (arccosine) of an expression. Returns exact symbolic values for well-known cosine values, and numeric results for numeric arguments.

## Syntax
```
ArcCos(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The value (must be in [-1, 1] for numeric evaluation) |

## Returns
- A `RealAtom` if the argument is a numeric integer or real in [-1, 1]
- An exact symbolic value for well-known cosine results (including negative values)
- An unevaluated `ArcCos(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> ArcCos(0)
1.5707963267948966
Flame> ArcCos(1)
0
Flame> ArcCos(0.5)
1.0471975511965976
```

Exact values at well-known points:
```
Flame> ArcCos((1/2)*Sqrt(3))
(1/6)*Pi
Flame> ArcCos((1/2)*Sqrt(2))
(1/4)*Pi
Flame> ArcCos(1/2)
(1/3)*Pi
```

Negative arguments (range extends to Pi):
```
Flame> ArcCos(-1/2)
(2/3)*Pi
Flame> ArcCos(-1*(1/2)*Sqrt(2))
(3/4)*Pi
Flame> ArcCos(-1*(1/2)*Sqrt(3))
(5/6)*Pi
```

Symbolic (unevaluated):
```
Flame> ArcCos(x)
ArcCos(x)
```

## Notes
- ArcCos is the inverse of Cos: ArcCos(Cos(Pi/3)) = Pi/3
- Domain for numeric evaluation is [-1, 1]; values outside this range throw an error
- Range is [0, Pi]
- Unlike ArcSin, negative arguments map to angles in (Pi/2, Pi]

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws `ArithmeticException` if numeric argument is outside [-1, 1]

## See Also
- [ArcSin](ArcSin.md) — inverse sine
- [ArcTan](ArcTan.md) — inverse tangent
- [Cos](Cos.md) — cosine
- [Num](Num.md) — force numeric evaluation
