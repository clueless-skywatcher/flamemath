# SetAt

Sets the element at a given index in a list. Mutates the list in place.

## Syntax
```
SetAt(list, index, value)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to modify |
| `index` | Integer | The index to set (supports negative indexing) |
| `value` | Any | The value to set |

## Returns
`Null`

## Examples
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> SetAt(l, 1, 20)
Null
Flame> l
[1, 20, 3]
```

Negative indexing:
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> SetAt(l, -1, 30)
Null
Flame> l
[1, 2, 30]
```

## Errors
- Throws `FlameArityException` if not exactly 3 arguments are provided
- Throws an error if the first argument is not a list
- Throws an error if the index is not an integer
- Throws an error if the index is out of bounds

## See Also
- [At](../general/At.md) — read an element by index
- [Append](Append.md) — add to the end of a list
