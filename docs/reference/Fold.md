# Fold

Reduces a list to a single value by applying a function cumulatively.

## Syntax
```
Fold(f, init, list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `f` | Function | A function taking two arguments: accumulator and current element |
| `init` | Any | The initial value of the accumulator |
| `list` | List | The list to reduce |

## Returns
The final accumulated value after processing all elements.

## Examples
```
Flame> Fold((acc, x) => acc + x, 0, [1, 2, 3, 4, 5])
15
Flame> Fold((acc, x) => acc * x, 1, [1, 2, 3, 4])
24
Flame> Fold((acc, x) => acc + 1, 0, [10, 20, 30])
3
```

## Notes
- Defined in `list.flame` using a `While` loop.
- Processes elements left to right.
- Returns `init` for an empty list.

## See Also
- [Map](Map.md) — apply a function to each element
- [Filter](Filter.md) — select elements by predicate
- [Sum](Sum.md) — sum all elements of a list
