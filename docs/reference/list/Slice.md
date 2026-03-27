# Slice

Extracts a sublist from a list given a start index (inclusive) and end index (exclusive).

## Syntax
```
Slice(list, start, end)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to slice |
| `start` | Integer | Start index (inclusive, 0-based) |
| `end` | Integer | End index (exclusive, 0-based) |

## Returns
A new `List` containing elements from index `start` up to (but not including) index `end`.

## Examples

Basic slicing:
```
Flame> Slice([1, 2, 3, 4], 1, 3)
[2, 3]
Flame> Slice([1, 2, 3, 4], 0, 2)
[1, 2]
Flame> Slice(["a", "b", "c", "d"], 1, 3)
["b", "c"]
```

Single element:
```
Flame> Slice([1, 2, 3], 1, 2)
[2]
```

Empty range:
```
Flame> Slice([1, 2, 3], 1, 1)
[]
```

Nested lists:
```
Flame> Slice([[1, 2], [3, 4], [5, 6]], 1, 2)
[[3, 4]]
```

## Notes
- Does not mutate the original list. Returns a new list.
- If the first argument is not a list, the expression is returned unevaluated.

## Errors
- Throws `FlameArityException` if the number of arguments is not exactly 3

## See Also
- [Append](Append.md) — add an element to the end of a list
- [Len](Len.md) — get the length of a list
- [Range](Range.md) — generate a list of integers
