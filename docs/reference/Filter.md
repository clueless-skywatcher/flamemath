# Filter

Returns elements of a list for which a predicate returns `True`.

## Syntax
```
Filter(f, list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `f` | Function | A predicate function taking one argument |
| `list` | List | The list to filter |

## Returns
A new `List` containing only the elements where `f(element)` is `True`.

## Examples
```
Flame> Filter((x) => x > 3, [1, 2, 3, 4, 5])
[4, 5]
Flame> Filter((x) => Mod(x, 2) == 0, [1, 2, 3, 4, 5, 6])
[2, 4, 6]
Flame> Filter((x) => x > 10, [1, 2, 3])
[]
```

## Notes
- Defined in `list.flame` using a `While` loop.
- Returns an empty list if no elements match.

## See Also
- [Map](Map.md) — apply a function to each element
- [Fold](Fold.md) — reduce a list to a single value
