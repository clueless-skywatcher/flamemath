# Range

Generates a list of numbers in a given range.

## Syntax
```
Range(end)
Range(start, end)
Range(start, end, step)
```

## Parameters
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `start` | Numeric | `0` (1-arg form) | Start of the range (inclusive) |
| `end` | Numeric | — | End of the range (exclusive) |
| `step` | Numeric | `1` | Step between elements |

## Returns
A `List` of numbers.

## Examples

Single argument — generates `[0, 1, ..., end-1]`:
```
Flame> Range(5)
[0, 1, 2, 3, 4]
```

Two arguments — generates `[start, start+1, ..., end-1]`:
```
Flame> Range(2, 7)
[2, 3, 4, 5, 6]
```

Three arguments — generates with a custom step:
```
Flame> Range(0, 10, 2)
[0, 2, 4, 6, 8]
```

Descending ranges with a negative step:
```
Flame> Range(10, 0, -2)
[10, 8, 6, 4, 2]
```

Negative ranges:
```
Flame> Range(-5, 2)
[-5, -4, -3, -2, -1, 0, 1]
```

Returns an empty list when the range is invalid (e.g., start >= end with positive step):
```
Flame> Range(5, 2)
[]
Flame> Range(0)
[]
```

## Errors
- Throws `FlameArityException` if 0 or more than 3 arguments are provided
- Throws an error if any argument is not numeric
- Throws an error if step is `0`

## See Also
- [GenList](GenList.md) — generate a list by evaluating an expression over iterator ranges
