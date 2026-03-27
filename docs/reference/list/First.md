# First

Returns the first element of a list, or `Null` if the list is empty.

## Syntax
```
First(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to get the first element from |

## Returns
- The first element of the list
- `Null` if the list is empty
- If the argument is not a list, returns unevaluated

## Examples

```
Flame> First([1, 2, 3])
1
Flame> First(["a", "b", "c"])
"a"
Flame> First([[1, 2], [3, 4]])
[1, 2]
```

Empty list:
```
Flame> First([])
Null
```

Symbolic arguments:
```
Flame> First(x)
First(x)
```

## Notes
- Defined in `list.flame`.
- Does not mutate the original list.

## See Also
- [Last](Last.md) — get the last element
- [Take](Take.md) — get the first n elements
- [At](../general/At.md) — access an element by index
