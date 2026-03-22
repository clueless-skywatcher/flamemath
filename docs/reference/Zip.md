# Zip

Combines two lists element-wise into a list of pairs.

## Syntax
```
Zip(list1, list2)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list1` | List | The first list |
| `list2` | List | The second list |

## Returns
- A new `List` of `[a, b]` pairs, where `a` and `b` are corresponding elements from each list.
- If the lists are different lengths, the result is truncated to the shorter list.
- If either argument is not a list, returns unevaluated.

## Examples
```
Flame> Zip([1, 2], [3, 4])
[[1, 3], [2, 4]]
Flame> Zip(["a", "b"], [1, 2])
[["a", 1], ["b", 2]]
Flame> Zip([1, 2, 3], [4, 5])
[[1, 4], [2, 5]]
Flame> Zip([], [])
[]
```

Symbolic arguments:
```
Flame> Zip(x, y)
Zip(x, y)
```

## Notes
- Defined in `list.flame` using `Range` and `Map`.
- Does not mutate the original lists. Returns a new list.

## See Also
- [Map](Map.md) — apply a function to each element
- [Range](Range.md) — generate a range of integers
