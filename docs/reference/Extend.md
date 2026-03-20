# Extend

Concatenates the elements of the second list onto the end of the first list. Mutates the first list in place.

## Syntax
```
Extend(list1, list2)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list1` | List | The list to extend |
| `list2` | List | The list whose elements are added |

## Returns
`Null`

## Examples
```
Flame> l = [1, 2]
[1, 2]
Flame> Extend(l, [3, 4])
Null
Flame> l
[1, 2, 3, 4]
```

Extending with an empty list is a no-op:
```
Flame> l = [1, 2]
[1, 2]
Flame> Extend(l, [])
Null
Flame> l
[1, 2]
```

Multiple extends:
```
Flame> l = [1]
[1]
Flame> Extend(l, [2, 3]); Extend(l, [4, 5]); l
[1, 2, 3, 4, 5]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if either argument is not a list

## See Also
- [Append](Append.md) — add a single element to the end
- [Prepend](Prepend.md) — add a single element to the beginning
