# IntegerPartitions

Generates all integer partitions of n.

## Syntax
```
IntegerPartitions(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer |

## Returns
A `List` of lists — all partitions of n with parts in non-decreasing order, in lexicographic order.

## Examples
```
Flame> IntegerPartitions(4)
[[1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2], [4]]
Flame> IntegerPartitions(5)
[[1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 4], [2, 3], [5]]
Flame> IntegerPartitions(1)
[[1]]
Flame> IntegerPartitions(0)
[[0]]
```

## Notes
- Uses the Kelleher-O'Sullivan algorithm with amortized O(1) cost per partition.
- The number of partitions of n is the partition function p(n).
- Returns unevaluated `IntegerPartitions(x)` for non-integer arguments.

## See Also
- [Compositions](Compositions.md) — ordered compositions of n into k parts
- [CatalanNumber](Catalan.md) — Catalan numbers
