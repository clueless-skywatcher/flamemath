# IsVector

Tests whether a value is a flat list — that is, a list whose elements are themselves not lists.

## Syntax
```
IsVector(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The value to test |

## Returns
- `True` if `x` is a `List` and none of its elements are themselves lists
- `False` otherwise

## Examples

Flat lists:
```
Flame> IsVector([1, 2, 3])
True
Flame> IsVector([1.5, 2.25, 3.75])
True
Flame> IsVector([1, 2.5, 3])
True
Flame> IsVector([7])
True
```

An empty list counts as a vector:
```
Flame> IsVector([])
True
```

Nested lists are rejected:
```
Flame> IsVector([[1, 2], [3, 4]])
False
Flame> IsVector([1, [2, 3], 4])
False
```

Non-list arguments:
```
Flame> IsVector(5)
False
Flame> IsVector("hello")
False
Flame> IsVector(x)
False
```

## Notes
- Defined in `types.flame`.
- The check is *structural*, not numeric: only direct nesting is rejected. String and other non-numeric atoms inside the list are accepted — e.g. `IsVector(["a", "b"])` returns `True`.
- If you need a vector of numbers specifically, compose with `IsNumeric`:
  ```
  IsNumVec = (x) => IsVector(x) && Apply(And, Map(IsNumeric, x))
  ```

## See Also
- [IsList](../list/List.md) — list-head predicate (builtin `Head(x) == List`)
- [IsIntPartition](../ntheory/IsIntPartition.md) — stricter shape predicate built on `IsVector`
