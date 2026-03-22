# Outer

Applies a function to all combinations of elements from two lists, producing a matrix.

## Syntax
```
Outer(f, list1, list2)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `f` | Function | A two-argument function to apply |
| `list1` | List | The first list (determines rows) |
| `list2` | List | The second list (determines columns) |

## Returns
- A nested `List` (matrix) where entry `[i, j]` is `f(list1[i], list2[j])`.
- If either list argument is not a list, returns unevaluated.

## Examples

Multiplication table:
```
Flame> Outer((a, b) => a * b, [1, 2, 3], [1, 2, 3])
[[1, 2, 3], [2, 4, 6], [3, 6, 9]]
```

Addition:
```
Flame> Outer((a, b) => a + b, [1, 2], [10, 20])
[[11, 21], [12, 22]]
```

With a builtin function:
```
Flame> Outer(Add, [1, 2], [3, 4])
[[4, 5], [5, 6]]
```

Pairing elements:
```
Flame> Outer((a, b) => [a, b], ["x", "y"], [1, 2])
[[["x", 1], ["x", 2]], [["y", 1], ["y", 2]]]
```

Symbolic arguments:
```
Flame> Outer(Add, x, [1, 2])
Outer(Add, x, [1, 2])
```

## Notes
- Defined in `list.flame` using `GenList` and `Map`.
- The result is always `Len(list1)` x `Len(list2)` in size.
- Generalizes the outer product / Cartesian product pattern.

## See Also
- [Map](Map.md) — apply a function to each element
- [GenList](GenList.md) — generate lists with an expression
- [Zip](Zip.md) — combine lists element-wise (not all combinations)
