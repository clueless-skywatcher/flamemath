# At

Accesses an element of a list by index. Supports negative indexing.

## Syntax
```
list[index]
At(list, index)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to index into |
| `index` | Integer | Zero-based index. Negative values count from the end |

## Returns
The element at the given index.

## Examples

Basic indexing:
```
Flame> [10, 20, 30][0]
10
Flame> [10, 20, 30][2]
30
```

Negative indexing:
```
Flame> [10, 20, 30][-1]
30
Flame> [10, 20, 30][-3]
10
```

Nested list indexing:
```
Flame> [[1, 2], [3, 4]][1][0]
3
```

Returns unevaluated when applied to non-list expressions:
```
Flame> x[0]
x[0]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the index is out of bounds

## See Also
- [List](../list/List.md) — list construction
- [Len](../list/Len.md) — list length
