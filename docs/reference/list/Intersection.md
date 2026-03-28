# Intersection

Returns the intersection of two or more lists, keeping only elements present in all lists.

## Syntax
```
Intersection(list1, list2)
Intersection(list1, list2, list3, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list1`, `list2`, ... | List | Two or more lists to intersect |

## Returns
- A `List` of distinct elements that appear in every input list, in first-occurrence order (from the first list)
- If any argument is not a `List`, returns unevaluated

## Examples

```
Flame> Intersection([1, 2, 3], [2, 3, 4])
[2, 3]
Flame> Intersection([1, 2], [3, 4])
[]
Flame> Intersection([1, 2, 3], [1, 2, 3])
[1, 2, 3]
```

Duplicates are removed:
```
Flame> Intersection([1, 1, 2], [1, 3])
[1]
```

Multiple lists:
```
Flame> Intersection([1, 2, 3], [2, 3, 4], [3, 4, 5])
[3]
```

Nested lists:
```
Flame> Intersection([[1, 2], [3, 4]], [[1, 2], [5, 6]])
[[1, 2]]
```

Symbolic (unevaluated):
```
Flame> Intersection(x, [1, 2])
Intersection(x, [1, 2])
```

## Errors
- Throws `FlameArityException` if fewer than 2 arguments are provided

## Notes
- Uses `==` for comparison, so it works with any element type including nested lists.

## See Also
- [Union](Union.md) — elements from any of the lists
- [Count](Count.md) — count occurrences of a specific value
