# EuclideanDistance

Computes the Euclidean distance (L2 norm) between two equal-length lists of numbers or expressions.

$$d(\mathbf{u}, \mathbf{v}) = \sqrt{\sum_{i}(u_i - v_i)^2}$$

## Syntax
```
EuclideanDistance(u, v)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `u` | List | First vector |
| `v` | List | Second vector (must have same length as `u`) |

## Returns
- A numeric result when all elements are numeric (e.g., `EuclideanDistance([0, 0], [3, 4])` returns `5`)
- A symbolic expression when elements contain symbols (e.g., `EuclideanDistance([x], [y])` returns `Sqrt((x - y)^2)`)
- An unevaluated `EuclideanDistance(u, v)` when either argument is not a list or the lists have different lengths

## Examples

Numeric vectors:
```
Flame> EuclideanDistance([0, 0], [3, 4])
5
Flame> EuclideanDistance([1, 2], [3, 4])
√8
Flame> EuclideanDistance([1, 2, 3], [4, 5, 6])
√27
```

Identical vectors:
```
Flame> EuclideanDistance([1, 2, 3], [1, 2, 3])
0
```

Symbolic elements:
```
Flame> EuclideanDistance([x], [y])
√((x - y)^2)
Flame> EuclideanDistance([x, y], [a, b])
√((x - a)^2 + (y - b)^2)
```

Unevaluated (non-list arguments):
```
Flame> EuclideanDistance(x, y)
EuclideanDistance(x, y)
```

## Notes
- This is a stdlib function defined in `distance_similarity.flame`
- Both arguments must be lists of the same length; otherwise the call returns unevaluated

## See Also
- [ManhattanDistance](ManhattanDistance.md) — L1 distance
- [Sqrt](Sqrt.md) — square root
- [Abs](Abs.md) — absolute value
