# ManhattanDistance

Computes the Manhattan distance (L1 norm) between two equal-length lists of numbers or expressions.

$$d(\mathbf{u}, \mathbf{v}) = \sum_{i}|u_i - v_i|$$

## Syntax
```
ManhattanDistance(u, v)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `u` | List | First vector |
| `v` | List | Second vector (must have same length as `u`) |

## Returns
- A numeric result when all elements are numeric (e.g., `ManhattanDistance([0, 0], [3, 4])` returns `7`)
- A symbolic expression when elements contain symbols (e.g., `ManhattanDistance([x], [y])` returns `Abs(x - y)`)
- An unevaluated `ManhattanDistance(u, v)` when either argument is not a list or the lists have different lengths

## Examples

Numeric vectors:
```
Flame> ManhattanDistance([0, 0], [3, 4])
7
Flame> ManhattanDistance([1, 2], [3, 4])
4
Flame> ManhattanDistance([1, 2, 3], [4, 5, 6])
9
```

Identical vectors:
```
Flame> ManhattanDistance([1, 2, 3], [1, 2, 3])
0
```

Symbolic elements:
```
Flame> ManhattanDistance([x], [y])
Abs(x - y)
Flame> ManhattanDistance([x, y], [a, b])
Abs(x - a) + Abs(y - b)
```

Unevaluated (non-list arguments):
```
Flame> ManhattanDistance(x, y)
ManhattanDistance(x, y)
```

## Notes
- This is a stdlib function defined in `distance_similarity.flame`
- Both arguments must be lists of the same length; otherwise the call returns unevaluated

## See Also
- [EuclideanDistance](EuclideanDistance.md) — L2 distance
- [Abs](Abs.md) — absolute value
