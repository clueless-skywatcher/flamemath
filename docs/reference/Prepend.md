# Prepend

Adds an element to the beginning of a list. Mutates the list in place.

## Syntax
```
Prepend(list, element)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to prepend to |
| `element` | Any | The element to add |

## Returns
`Null`

## Examples
```
Flame> l = [1, 2, 3]
[1, 2, 3]
Flame> Prepend(l, 0)
Null
Flame> l
[0, 1, 2, 3]
```

Multiple prepends insert in reverse order:
```
Flame> l = []
[]
Flame> Prepend(l, 3); Prepend(l, 2); Prepend(l, 1); l
[1, 2, 3]
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a list

## See Also
- [Append](Append.md) — add to the end of a list
- [Extend](Extend.md) — concatenate two lists
