# IsIntPartition

Tests whether a list is an integer partition of a given number — that is, a non-increasing sequence of non-negative integers summing to `n`.

## Syntax
```
IsIntPartition(l, n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `l` | List | Candidate partition (a flat list of integers) |
| `n` | Integer | The target sum |

## Returns
- `True` if `l` is a non-increasing list of non-negative integers whose entries sum to `n`
- `False` otherwise

## Examples

Valid partitions:
```
Flame> IsIntPartition([5], 5)
True
Flame> IsIntPartition([3, 2, 1], 6)
True
Flame> IsIntPartition([3, 3, 2, 2], 10)
True
Flame> IsIntPartition([4, 3, 2, 2, 1], 12)
True
```

Wrong sum:
```
Flame> IsIntPartition([3, 2, 1], 7)
False
Flame> IsIntPartition([1, 1], 0)
False
```

Wrong ordering (must be non-increasing):
```
Flame> IsIntPartition([1, 2, 3], 6)
False
Flame> IsIntPartition([2, 1, 3], 6)
False
```

Bad argument shapes:
```
Flame> IsIntPartition([3, 2, 1], 6.0)
False
Flame> IsIntPartition(6, 6)
False
Flame> IsIntPartition([[3, 2, 1]], 6)
False
```

Used alongside `IntegerPartitions` (reversed because `IntegerPartitions` returns non-decreasing order):
```
Flame> ps = IntegerPartitions(5); For(p, ps, PrintLn(IsIntPartition(Reverse(p), 5)))
True
True
True
True
True
True
True
```

## Notes
- Defined in `ntheory.flame` as:
  ```
  IsIntPartition = (l, n) => IsInteger(n)
      && IsVector(l)
      && Apply(And, Map(IsPositiveInteger, Deltas(l)))
      && Sum(l) == n
  ```
- **Ordering convention:** expects a *non-increasing* list (e.g. `[3, 2, 1]`). `IntegerPartitions(n)` returns partitions in *non-decreasing* order, so call `Reverse` on each partition before checking.
- **Zeros are allowed** — `IsPositiveInteger` accepts `0`, so `[3, 0]` validates as a partition of `3`. Strip zeros if you want canonical partitions.
- Returns `False` (not an unevaluated `Raw(...)`) for malformed inputs.

## See Also
- [IntegerPartitions](IntegerPartitions.md) — enumerate all partitions of `n`
- [IsVector](../types/IsVector.md) — flat-list predicate used in the check
- [IsPositiveInteger](../types/IsPositiveInteger.md) — non-negative integer predicate
- [Deltas](../list/Deltas.md) — backward differences used to verify ordering
- [Sum](../math/Sum.md) — list sum used to verify total
