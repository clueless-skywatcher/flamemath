# Floor

Returns the greatest integer less than or equal to the argument.

## Syntax
```
Floor(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to floor |

## Returns
- An `IntegerAtom` for numeric arguments (integer, real, rational)
- An unevaluated `Floor(x)` for symbolic arguments

## Examples

```
Flame> Floor(10)
10
Flame> Floor(1.25)
1
Flame> Floor(-1.25)
-2
Flame> Floor(3.0)
3
Flame> Floor(1 / 3)
0
Flame> Floor(3 / 2)
1
```

Symbolic (unevaluated):
```
Flame> Floor(x)
Floor(x)
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Ceil](Ceil.md) — ceiling (round up)
- [Round](Round.md) — round to nearest
