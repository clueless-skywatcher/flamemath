# Sort

Sorts the elements of a list in ascending order. An optional comparator function can be provided for custom ordering.

## Syntax
```
Sort(list)
Sort(list, comparator)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | The list to sort |
| `comparator` | Function (optional) | A two-argument function `(a, b) => ...` that returns `True` if `a` should come before `b` |

## Returns
A new `List` with elements sorted.

## Examples

Basic sorting:
```
Flame> Sort([3, 1, 2])
[1, 2, 3]
Flame> Sort([3.3, 1.1, 2.2])
[1.1, 2.2, 3.3]
Flame> Sort(["cherry", "apple", "banana"])
["apple", "banana", "cherry"]
```

With a custom comparator (descending order):
```
Flame> Sort([1, 3, 2], (a, b) => Greater(a, b))
[3, 2, 1]
```

Sort by absolute value:
```
Flame> Sort([-4, 3, 1, -2], (a, b) => Less(Abs(a), Abs(b)))
[1, -2, 3, -4]
```

## Notes
- Does not mutate the original list. Returns a new list.
- Without a comparator, elements are sorted using the canonical ordering (numbers < strings < symbols).
- The comparator function must accept exactly 2 arguments. If an overloaded function is passed, the arity-2 clause is used.

## Errors
- Throws `FlameArityException` if fewer than 1 or more than 2 arguments are provided
- Throws an error if the first argument is not a list
- Throws an error if the second argument is not a function

## See Also
- [Filter](Filter.md) — select elements by predicate
- [Map](Map.md) — apply a function to each element
