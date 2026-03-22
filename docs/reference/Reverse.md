# Reverse

Returns a new list with elements in reverse order.

## Syntax
```
Reverse(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to reverse |

## Returns
- A new `List` with elements in reverse order.
- If the argument is not a list, returns unevaluated.

## Examples
```
Flame> Reverse([1, 2, 3])
[3, 2, 1]
Flame> Reverse(["a", "b", "c"])
["c", "b", "a"]
Flame> Reverse([])
[]
```

Symbolic argument:
```
Flame> Reverse(x)
Reverse(x)
```

## Notes
- Defined in `list.flame` using a `While` loop with `Prepend`.
- Does not mutate the original list. Returns a new list.
- `Reverse(Reverse(list))` returns the original list.

## See Also
- [Sort](Sort.md) — sort elements
- [Map](Map.md) — apply a function to each element
