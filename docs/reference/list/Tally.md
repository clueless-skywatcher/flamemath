# Tally

Counts the frequency of each distinct element in a list, returning a list of `[element, count]` pairs in first-occurrence order.

## Syntax
```
Tally(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to tally |

## Returns
- A `List` of `[element, count]` pairs, ordered by first occurrence
- An unevaluated `Tally(list)` if the argument is not a list

## Examples

```
Flame> Tally([1, 2, 1, 2, 1, 3])
[[1, 3], [2, 2], [3, 1]]
Flame> Tally([1, 2, 3])
[[1, 1], [2, 1], [3, 1]]
Flame> Tally([5, 5, 5])
[[5, 3]]
Flame> Tally([])
[]
```

Works with any element type, including nested lists:
```
Flame> Tally(["a", "b", "a"])
[["a", 2], ["b", 1]]
Flame> Tally([[1, 2], [3, 4], [1, 2]])
[[[1, 2], 2], [[3, 4], 1]]
```

Symbolic (unevaluated):
```
Flame> Tally(x)
Tally(x)
```

## Notes
- Defined in `list.flame`.
- Uses `==` for comparison.
- Returns a nested list rather than a Dict, so it works with unhashable elements (lists, dicts).

## See Also
- [Count](Count.md) — count occurrences of a specific value
- [Union](Union.md) — distinct elements across lists
