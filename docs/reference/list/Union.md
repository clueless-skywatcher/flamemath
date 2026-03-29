# Union

Returns the union of two or more lists, removing duplicates and preserving first-occurrence order.

## Syntax
```
Union(list1, list2)
Union(list1, list2, list3, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list1`, `list2`, ... | List | Two or more lists to combine |

## Returns
- A `List` of distinct elements from all input lists, in first-occurrence order
- If any argument is not a `List`, returns unevaluated

## Examples

```
Flame> Union([1, 2, 3], [2, 3, 4])
[1, 2, 3, 4]
Flame> Union([1, 2], [3, 4])
[1, 2, 3, 4]
Flame> Union([1, 2, 3], [1, 2, 3])
[1, 2, 3]
```

Duplicates within a list are also removed:
```
Flame> Union([1, 1, 2], [2, 3])
[1, 2, 3]
```

Multiple lists:
```
Flame> Union([1, 2], [3, 4], [4, 5])
[1, 2, 3, 4, 5]
```

Nested lists:
```
Flame> Union([[1, 2], [3, 4]], [[3, 4], [5, 6]])
[[1, 2], [3, 4], [5, 6]]
```

Symbolic (unevaluated):
```
Flame> Union(x, [1, 2])
Union(x, [1, 2])
```

## Errors
- Throws `FlameArityException` if fewer than 2 arguments are provided

## Notes
- Uses `==` for comparison, so it works with any element type including nested lists.

## See Also
- [Intersection](Intersection.md) — elements common to all lists
- [Join](Join.md) — concatenate lists without removing duplicates
