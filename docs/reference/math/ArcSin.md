# ArcSin

Computes the inverse sine (arcsine) of an expression. Returns exact symbolic values for well-known sine values, and numeric results for numeric arguments.

## Syntax
```
ArcSin(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The value (must be in [-1, 1] for numeric evaluation) |

## Returns
- A `RealAtom` if the argument is a numeric integer or real in [-1, 1]
- An exact symbolic value for well-known sine results (0, 1/2, Sqrt(2)/2, Sqrt(3)/2, 1 and their negatives)
- An unevaluated `ArcSin(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> ArcSin(0)
0
Flame> ArcSin(1)
1.5707963267948966
Flame> ArcSin(0.5)
0.5235987755982988
```

Exact values at well-known points:
```
Flame> ArcSin(1/2)
(1/6)*Pi
Flame> ArcSin((1/2)*Sqrt(2))
(1/4)*Pi
Flame> ArcSin((1/2)*Sqrt(3))
(1/3)*Pi
```

Negative arguments:
```
Flame> ArcSin(-1/2)
-1*(1/6)*Pi
Flame> ArcSin(-1*(1/2)*Sqrt(3))
-1*(1/3)*Pi
```

Symbolic (unevaluated):
```
Flame> ArcSin(x)
ArcSin(x)
```

## Notes
- ArcSin is the inverse of Sin: ArcSin(Sin(Pi/6)) = Pi/6
- Domain for numeric evaluation is [-1, 1]; values outside this range throw an error
- Range is [-Pi/2, Pi/2]
- Symbolic expressions not matching known values remain unevaluated

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws `ArithmeticException` if numeric argument is outside [-1, 1]

## See Also
- [ArcCos](ArcCos.md) — inverse cosine
- [ArcTan](ArcTan.md) — inverse tangent
- [Sin](Sin.md) — sine
- [Num](Num.md) — force numeric evaluation
