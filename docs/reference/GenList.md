# GenList

Generates a list by evaluating an expression over one or more iterator ranges. Multiple iterators produce nested lists.

## Syntax
```
GenList(expr, [var, start, end])
GenList(expr, [var, start, end, step])
GenList(expr, [var1, start1, end1], [var2, start2, end2], ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to evaluate for each iteration |
| `[var, start, end]` | List | Iterator spec: variable, start (inclusive), end (exclusive) |
| `[var, start, end, step]` | List | Iterator spec with custom step |

Each iterator spec is a list where:
- The first element is a `Symbol` (the loop variable)
- The remaining elements are numeric bounds and an optional step (default `1`)

## Returns
A `List`. With multiple iterators, a nested `List`.

## Examples

Basic generation:
```
Flame> GenList(i^2, [i, 1, 5])
[1, 4, 9, 16]
```

With a step:
```
Flame> GenList(i, [i, 0, 10, 2])
[0, 2, 4, 6, 8]
```

Two iterators produce a nested list (outer iterates rows, inner iterates columns):
```
Flame> GenList(i * j, [i, 1, 4], [j, 1, 4])
[[1, 2, 3], [2, 4, 6], [3, 6, 9]]
```

Symbolic expressions:
```
Flame> GenList(x + i, [i, 1, 4])
[x + 1, x + 2, x + 3]
```

Three iterators produce a doubly nested list:
```
Flame> GenList(i + j + k, [i, 1, 3], [j, 1, 3], [k, 1, 3])
[[[3, 4], [4, 5]], [[4, 5], [5, 6]]]
```

Iterator variables do not leak into the outer scope:
```
Flame> GenList(i, [i, 1, 5])
[1, 2, 3, 4]
Flame> i
i
```

## Errors
- Throws `FlameArityException` if fewer than 2 arguments are provided
- Throws an error if any iterator spec is not a list
- Throws an error if an iterator spec has fewer than 2 or more than 4 elements
- Throws an error if the first element of an iterator spec is not a symbol
- Throws an error if the bounds in an iterator spec are not numeric

## See Also
- [Range](Range.md) — generate a simple list of numbers
- [Map](Map.md) — apply a function to each element of a list
