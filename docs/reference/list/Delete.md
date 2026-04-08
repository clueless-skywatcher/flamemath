# Delete

Removes the first occurrence of an element from a list. Mutates the list in place. If the element is not found, the list is unchanged.

## Syntax
```
Delete(list, element)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to delete from |
| `element` | Any | The element to remove |

## Returns
`Null`

## Examples
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> Delete(l, 2)
Null
Flame> l
[1, 3]
```

Only the first occurrence is removed:
```
Flame> l = [1, 2, 3, 2]
[1, 2, 3, 2]
Flame> Delete(l, 2)
Null
Flame> l
[1, 3, 2]
```

Element not found:
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> Delete(l, 5)
Null
Flame> l
[1, 2, 3]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a list

## See Also
- [Append](Append.md) — add an element to the end of a list
- [Filter](Filter.md) — remove elements by predicate
