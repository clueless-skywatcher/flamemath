# Compositions

Generates all compositions of n into exactly k positive integer parts.

## Syntax
```
Compositions(n, k)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The positive integer to compose |
| `k` | Integer | The number of parts |

## Returns
A `List` of lists — all ordered sequences of k positive integers summing to n, in lexicographic order. Returns an empty list if k > n.

## Examples
```
Flame> Compositions(4, 2)
[[1, 3], [2, 2], [3, 1]]
Flame> Compositions(5, 3)
[[1, 1, 3], [1, 2, 2], [1, 3, 1], [2, 1, 2], [2, 2, 1], [3, 1, 1]]
Flame> Compositions(3, 1)
[[3]]
Flame> Compositions(3, 3)
[[1, 1, 1]]
Flame> Compositions(3, 5)
[]
```

## Notes
- The number of compositions of n into k parts is $\binom{n-1}{k-1}$ (stars and bars).
- Unlike `IntegerPartitions`, order matters: `[1, 2]` and `[2, 1]` are distinct compositions.
- Returns unevaluated `Compositions(n, k)` for non-integer, zero, or negative arguments.

## See Also
- [IntegerPartitions](IntegerPartitions.md) — unordered partitions of n
- [Binomial](Binomial.md) — binomial coefficient
