# DeleteCopy

Returns a copy of the list with the first occurrence of an element removed. The original list is not modified.

## Syntax
```
DeleteCopy(list, element)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to copy and delete from |
| `element` | Any | The element to remove |

## Returns
A new list with the first occurrence of `element` removed. If `element` is not found, returns a copy of the original list.

## Examples
```
Flame> DeleteCopy([1, 2, 3], 2)
[1, 3]
Flame> DeleteCopy([1, 2, 3, 2], 2)
[1, 3, 2]
```

Original list is unchanged:
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> DeleteCopy(l, 2)
[1, 3]
Flame> l
[1, 2, 3]
```

Element not found:
```
Flame> DeleteCopy([1, 2, 3], 5)
[1, 2, 3]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a list

## See Also
- [Delete](Delete.md) — remove the first occurrence in place (mutating)
- [DeleteAll](DeleteAll.md) — remove all occurrences in place
