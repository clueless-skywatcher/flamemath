# Len

Returns the number of elements in a list.

## Syntax
```
Len(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to measure |

## Returns
An `Integer` representing the number of elements. Returns `0` for non-list arguments.

## Examples
```
Flame> Len([1, 2, 3])
3
Flame> Len([])
0
Flame> Len([[1, 2], [3, 4]])
2
Flame> Len(42)
0
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [List](List.md) — list construction
- [At](At.md) — access elements by index
