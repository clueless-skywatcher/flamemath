# ArcTan2

Computes the two-argument arctangent of y and x, returning the angle in radians between the positive x-axis and the point (x, y). Unlike `ArcTan`, this function is quadrant-aware.

## Syntax
```
ArcTan2(y, x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `y` | Any | The y-coordinate |
| `x` | Any | The x-coordinate |

## Returns
- A numeric atom if both arguments are numeric
- An unevaluated `ArcTan2(y, x)` if either argument is symbolic

## Examples

Numeric evaluation:
```
Flame> ArcTan2(0, 1)
0
Flame> ArcTan2(1, 0)
1.5707963267948966
Flame> ArcTan2(1, 1)
0.7853981633974483
Flame> ArcTan2(0, -1)
3.141592653589793
Flame> ArcTan2(-1, 0)
-1.5707963267948966
```

Symbolic (unevaluated):
```
Flame> ArcTan2(x, y)
ArcTan2(x, y)
```

## Notes
- Range is (-Pi, Pi]
- `ArcTan2(y, x)` is equivalent to `Math.atan2(y, x)` for numeric arguments
- Unlike `ArcTan(y/x)`, `ArcTan2` correctly handles all four quadrants and the case where x = 0
- `ArcTan2(0, 0)` is undefined and throws an error

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws `ArithmeticException` if both arguments are zero

## See Also
- [ArcTan](ArcTan.md) — single-argument arctangent
- [ArcSin](ArcSin.md) — inverse sine
- [ArcCos](ArcCos.md) — inverse cosine
- [Num](Num.md) — force numeric evaluation
