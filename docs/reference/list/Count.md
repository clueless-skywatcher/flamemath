# Count

Counts the number of occurrences of a value in a list.

## Syntax
```
Count(list, value)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to search |
| `value` | Any | The value to count |

## Returns
- An `Integer` equal to the number of elements in `list` that equal `value`
- An unevaluated `Count(list, value)` if the first argument is not a list

## Examples

```
Flame> Count([1, 2, 1, 3], 1)
2
Flame> Count([1, 2, 3], 9)
0
Flame> Count(["a", "b", "a", "c"], "a")
2
Flame> Count([], 1)
0
```

Symbolic (unevaluated):
```
Flame> Count(x, 1)
Count(x, 1)
```

## Notes
- Defined in `list.flame`.
- Uses `==` for comparison, so elements must match exactly.

## See Also
- [Filter](Filter.md) — select elements matching a predicate
- [Len](Len.md) — total number of elements
