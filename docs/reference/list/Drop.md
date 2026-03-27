# Drop

Removes the first (or last) `n` elements of a list, returning the rest.

## Syntax
```
Drop(list, n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to drop from |
| `n` | Integer | Number of elements to drop |

## Returns
- A new `List` with the first `n` elements removed when `n >= 0`
- A new `List` with the last `|n|` elements removed when `n < 0`
- If either argument is not the expected type, returns unevaluated

## Examples

Drop from the front:
```
Flame> Drop([1, 2, 3, 4], 2)
[3, 4]
Flame> Drop([1, 2, 3], 3)
[]
```

Drop from the back (negative n):
```
Flame> Drop([1, 2, 3, 4], -2)
[1, 2]
Flame> Drop([1, 2, 3], -1)
[1, 2]
```

Symbolic arguments:
```
Flame> Drop(x, 2)
Drop(x, 2)
```

## Notes
- Defined in `list.flame` using `Slice`.
- Does not mutate the original list. Returns a new list.
- Complement of `Take`: `Take(l, n)` and `Drop(l, n)` together reconstruct the original list.

## See Also
- [Take](Take.md) — keep the first or last n elements
- [Slice](Slice.md) — extract a sub-list by index range
- [First](First.md) — get the first element
- [Last](Last.md) — get the last element
