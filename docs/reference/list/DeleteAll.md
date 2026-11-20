# DeleteAll

Removes all occurrences of an element from a list. Mutates the list in place.

## Syntax
```
DeleteAll(list, element)
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
Flame> l = [1, 2, 3, 2, 4, 2]
[1, 2, 3, 2, 4, 2]
Flame> DeleteAll(l, 2)
Null
Flame> l
[1, 3, 4]
```

Element not found:
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> DeleteAll(l, 5)
Null
Flame> l
[1, 2, 3]
```

All elements removed:
```
Flame> l = [2, 2, 2]
[2, 2, 2]
Flame> DeleteAll(l, 2)
Null
Flame> l
[]
```

## Implementation
Stdlib function defined as:
```
DeleteAll = (l, elem) => {
    If(!IsList(l), Return(Raw(DeleteAll, l, elem)));
    While(Has(l, elem), Delete(l, elem));
    Null
}
```

## See Also
- [Delete](Delete.md) — remove the first occurrence of an element
- [Filter](Filter.md) — remove elements by predicate (returns a new list)
