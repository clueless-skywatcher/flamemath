# Flatten

Recursively flattens a nested list into a single flat list.

## Syntax
```
Flatten(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to flatten |

## Returns
- A new `List` with all nested lists flattened into a single level.
- If the argument is not a list, returns unevaluated.

## Examples
```
Flame> Flatten([[1, 2], [3, 4]])
[1, 2, 3, 4]
Flame> Flatten([[1, [2]], [3, [4]]])
[1, 2, 3, 4]
Flame> Flatten([1, [2, 3], [[4, 5]]])
[1, 2, 3, 4, 5]
Flame> Flatten([])
[]
```

Symbolic argument:
```
Flame> Flatten(x)
Flatten(x)
```

## Notes
- Defined in `list.flame` using a `While` loop.
- Flattens all levels of nesting, not just one.
- Non-list elements are kept as-is.

## See Also
- [Extend](Extend.md) — concatenate two lists
- [Map](Map.md) — apply a function to each element
