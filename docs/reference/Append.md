# Append

Adds an element to the end of a list. Mutates the list in place.

## Syntax
```
Append(list, element)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to append to |
| `element` | Any | The element to add |

## Returns
`Null`

## Examples
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> Append(l, 4)
Null
Flame> l
[1, 2, 3, 4]
```

Appending a list as an element (does not flatten):
```
Flame> l = [1, 2]
[1, 2]
Flame> Append(l, [3, 4])
Null
Flame> l
[1, 2, [3, 4]]
```

Multiple appends:
```
Flame> l = []
[]
Flame> Append(l, 1); Append(l, 2); Append(l, 3); l
[1, 2, 3]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a list

## See Also
- [Prepend](Prepend.md) — add to the beginning of a list
- [Extend](Extend.md) — concatenate two lists
