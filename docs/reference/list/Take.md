# Take

Returns the first (or last) `n` elements of a list.

## Syntax
```
Take(list, n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to take from |
| `n` | Integer | Number of elements to take |

## Returns
- A new `List` containing the first `n` elements when `n >= 0`
- A new `List` containing the last `|n|` elements when `n < 0`
- If either argument is not the expected type, returns unevaluated

## Examples

Take from the front:
```
Flame> Take([1, 2, 3, 4], 2)
[1, 2]
Flame> Take([1, 2, 3], 0)
[]
```

Take from the back (negative n):
```
Flame> Take([1, 2, 3, 4], -2)
[3, 4]
Flame> Take([1, 2, 3], -1)
[3]
```

Symbolic arguments:
```
Flame> Take(x, 2)
Take(x, 2)
```

## Notes
- Defined in `list.flame` using `Slice`.
- Does not mutate the original list. Returns a new list.
- Complement of `Drop`: `Take(l, n)` and `Drop(l, n)` together reconstruct the original list.

## See Also
- [Drop](Drop.md) — remove the first or last n elements
- [Slice](Slice.md) — extract a sub-list by index range
- [First](First.md) — get the first element
- [Last](Last.md) — get the last element
