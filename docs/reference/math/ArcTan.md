# ArcTan

Computes the inverse tangent (arctangent) of an expression. Returns exact symbolic values for well-known tangent values, and numeric results for numeric arguments.

## Syntax
```
ArcTan(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The value |

## Returns
- A `RealAtom` if the argument is a numeric integer or real
- An exact symbolic value for well-known tangent results (0, 1/Sqrt(3), 1, Sqrt(3) and their negatives)
- An unevaluated `ArcTan(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> ArcTan(0)
0
Flame> ArcTan(1)
0.7853981633974483
Flame> ArcTan(0.5)
0.46364760900080615
```

Exact values at well-known points:
```
Flame> ArcTan(Sqrt(3))
(1/3)*Pi
```

Negative arguments:
```
Flame> ArcTan(-1*Sqrt(3))
-1*(1/3)*Pi
```

Symbolic (unevaluated):
```
Flame> ArcTan(x)
ArcTan(x)
```

## Notes
- ArcTan is the inverse of Tan
- Domain is all real numbers; range is (-Pi/2, Pi/2)
- For two-argument arctangent (quadrant-aware), see ArcTan2

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [ArcTan2](ArcTan2.md) — two-argument arctangent
- [ArcSin](ArcSin.md) — inverse sine
- [ArcCos](ArcCos.md) — inverse cosine
- [Tan](Tan.md) — tangent
- [Num](Num.md) — force numeric evaluation
