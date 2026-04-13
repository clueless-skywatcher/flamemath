# Deltas

Computes successive differences of a list, using the convention `l[i] - l[i+1]` (current minus next).

## Syntax
```
Deltas(l)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `l` | List | The list to compute differences for |

## Returns
- A `List` of length `Len(l) - 1` whose `i`th element is `l[i] - l[i+1]`
- An empty list `[]` if `l` has one element
- For a list of length `n`, the output has length `n - 1`

## Examples

Monotonic sequences:
```
Flame> Deltas([1, 2, 3, 4])
[-1, -1, -1]
Flame> Deltas([4, 3, 2, 1])
[1, 1, 1]
```

Non-increasing with repeats yields zeros:
```
Flame> Deltas([3, 3, 2, 2])
[0, 1, 0]
```

Irregular input:
```
Flame> Deltas([1, 3, 6, 10])
[-2, -3, -4]
Flame> Deltas([-5, -2, -7])
[-3, 5]
```

Trivial inputs:
```
Flame> Deltas([5])
[]
Flame> Deltas([7, 10])
[-3]
```

Real values:
```
Flame> Deltas([1.0, 1.5, 2.0])
[-0.5, -0.5]
```

## Notes
- Defined in `list.flame` as:
  ```
  Deltas = (l) => GenList(l[i] - l[i + 1], [i, Len(l) - 1])
  ```
- **Sign convention:** Deltas computes *backward* differences (`l[i] - l[i+1]`), not the forward-difference convention used by NumPy's `np.diff` or R's `diff`. A strictly increasing input produces all-negative deltas; a strictly decreasing input produces all-positive deltas.
- This sign convention is load-bearing for [`IsIntPartition`](../ntheory/IsIntPartition.md): a non-increasing list has non-negative deltas, which is what `IsIntPartition` checks via `IsPositiveInteger`.

## See Also
- [IsIntPartition](../ntheory/IsIntPartition.md) — uses `Deltas` to verify non-increasing order
- [GenList](GenList.md) — the underlying generator
- [Fold](Fold.md), [FoldScan](FoldScan.md) — general reductions over a list
