# Zip

Combines multiple lists element-wise into a list of tuples. Accepts any number of lists (variadic).

## Syntax
```
Zip(list1, list2, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `lists...` | List | Two or more lists to zip together |

## Returns
- A new `List` of sub-lists, where each sub-list contains the corresponding elements from each input list.
- If the lists are different lengths, the result is truncated to the shortest list.
- If any argument is not a list, returns unevaluated.

## Examples

Two lists:
```
Flame> Zip([1, 2], [3, 4])
[[1, 3], [2, 4]]
Flame> Zip(["a", "b"], [1, 2])
[["a", 1], ["b", 2]]
```

Three or more lists:
```
Flame> Zip([1, 2, 3], [4, 5, 6], [7, 8, 9])
[[1, 4, 7], [2, 5, 8], [3, 6, 9]]
Flame> Zip([1], [2], [3], [4])
[[1, 2, 3, 4]]
```

Unequal lengths (truncates to shortest):
```
Flame> Zip([1, 2, 3], [4, 5])
[[1, 4], [2, 5]]
Flame> Zip([1, 2, 3], [4, 5], [7])
[[1, 4, 7]]
```

Empty lists:
```
Flame> Zip([], [])
[]
Flame> Zip([1, 2], [], [3, 4])
[]
```

Single list:
```
Flame> Zip([1, 2, 3])
[[1], [2], [3]]
```

Symbolic arguments:
```
Flame> Zip(x, y)
Zip(x, y)
Flame> Zip([1, 2], 42, [3, 4])
Zip([1, 2], 42, [3, 4])
```

## Notes
- Defined in `list.flame` as a variadic function using `For` loops.
- Does not mutate the original lists. Returns a new list.
- An empty list among the inputs produces an empty result.

## See Also
- [Map](Map.md) — apply a function to each element
- [Outer](Outer.md) — compute the outer product of two lists
