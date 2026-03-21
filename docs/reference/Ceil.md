# Ceil

Returns the smallest integer greater than or equal to the argument.

## Syntax
```
Ceil(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to ceil |

## Returns
- An `IntegerAtom` for numeric arguments (integer, real, rational)
- An unevaluated `Ceil(x)` for symbolic arguments

## Examples

```
Flame> Ceil(10)
10
Flame> Ceil(1.25)
2
Flame> Ceil(-1.25)
-1
Flame> Ceil(3.0)
3
Flame> Ceil(0.5)
1
Flame> Ceil(-0.5)
0
```

Rational arguments:
```
Flame> Ceil(1 / 3)
1
Flame> Ceil(3 / 2)
2
Flame> Ceil(-1 / 3)
0
```

Symbolic (unevaluated):
```
Flame> Ceil(x)
Ceil(x)
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Floor](Floor.md) — floor (round down)
- [Round](Round.md) — round to nearest
