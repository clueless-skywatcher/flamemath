# Last

Returns the last element of a list, or `Null` if the list is empty.

## Syntax
```
Last(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to get the last element from |

## Returns
- The last element of the list
- `Null` if the list is empty
- If the argument is not a list, returns unevaluated

## Examples

```
Flame> Last([1, 2, 3])
3
Flame> Last(["a", "b", "c"])
"c"
Flame> Last([[1, 2], [3, 4]])
[3, 4]
```

Empty list:
```
Flame> Last([])
Null
```

Symbolic arguments:
```
Flame> Last(x)
Last(x)
```

## Notes
- Defined in `list.flame`.
- Uses negative indexing (`l[-1]`) internally.
- Does not mutate the original list.

## See Also
- [First](First.md) — get the first element
- [Drop](Drop.md) — remove elements from the front or back
- [At](../general/At.md) — access an element by index
