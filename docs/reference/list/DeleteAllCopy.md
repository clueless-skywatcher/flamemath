# DeleteAllCopy

Returns a new list with all occurrences of an element removed. The original list is not modified.

## Syntax
```
DeleteAllCopy(list, element)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to copy and delete from |
| `element` | Any | The element to remove |

## Returns
A new list with all occurrences of `element` removed. If `element` is not found, returns a copy of the original list.

## Examples
```
Flame> DeleteAllCopy([1, 2, 3, 2, 4, 2], 2)
[1, 3, 4]
Flame> DeleteAllCopy([1, 2, 3], 5)
[1, 2, 3]
```

Original list is unchanged:
```
Flame> l = [1, 2, 3, 2]
[1, 2, 3, 2]
Flame> DeleteAllCopy(l, 2)
[1, 3]
Flame> l
[1, 2, 3, 2]
```

## Implementation
Stdlib function defined as:
```
DeleteAllCopy = (l, elem) => {
    If(!IsList(l), Return(Raw(DeleteAllCopy, l, elem)));
    Filter((e) => e != elem, l)
}
```

## See Also
- [DeleteAll](DeleteAll.md) — remove all occurrences in place (mutating)
- [DeleteCopy](DeleteCopy.md) — remove first occurrence, returning a new list
- [Filter](Filter.md) — remove elements by predicate
