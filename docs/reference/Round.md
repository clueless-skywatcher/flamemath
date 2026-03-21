# Round

Rounds the argument to the nearest integer. Half values round toward positive infinity.

## Syntax
```
Round(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to round |

## Returns
- An `IntegerAtom` for numeric arguments (integer, real, rational)
- An unevaluated `Round(x)` for symbolic arguments

## Examples

```
Flame> Round(10)
10
Flame> Round(1.25)
1
Flame> Round(1.75)
2
Flame> Round(-1.25)
-1
Flame> Round(-1.75)
-2
Flame> Round(3.0)
3
```

Half values:
```
Flame> Round(0.5)
1
Flame> Round(-0.5)
0
```

Rational arguments:
```
Flame> Round(1 / 3)
0
Flame> Round(2 / 3)
1
Flame> Round(3 / 2)
2
```

Symbolic (unevaluated):
```
Flame> Round(x)
Round(x)
```

## Notes
- Uses `Math.round`, which rounds half values toward positive infinity (e.g., `0.5` rounds to `1`, `-0.5` rounds to `0`)

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Floor](Floor.md) — floor (round down)
- [Ceil](Ceil.md) — ceiling (round up)
