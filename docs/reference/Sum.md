# Sum

Computes the sum of all elements in a list.

## Syntax
```
Sum(list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | A list of numeric values |

## Returns
The sum of all elements in the list. Returns `0` for an empty list.

## Examples
```
Flame> Sum([1, 2, 3])
6
Flame> Sum([10, 20, 30])
60
Flame> Sum(Range(1, 5))
10
Flame> Sum([])
0
```

## Notes
- Defined in `math.flame` using a `While` loop.
- Iterates through the list, accumulating the total.

## See Also
- [Fold](Fold.md) — generalized list reduction
- [Triangular](Triangular.md) — sum of integers from 1 to n
