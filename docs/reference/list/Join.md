# Join

Concatenates two or more lists into a new list.

## Syntax
```
Join(list1, list2)
Join(list1, list2, list3, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list1`, `list2`, ... | List | Two or more lists to concatenate |

## Returns
- A new `List` containing all elements from the input lists in order
- If any argument is not a `List`, returns unevaluated

## Examples

Two lists:
```
Flame> Join([1, 2], [3, 4])
[1, 2, 3, 4]
```

Multiple lists:
```
Flame> Join([1, 2], [3, 4], [5, 6])
[1, 2, 3, 4, 5, 6]
```

With empty lists:
```
Flame> Join([], [1, 2])
[1, 2]
Flame> Join([1, 2], [], [3, 4])
[1, 2, 3, 4]
```

Nested lists:
```
Flame> Join([[1, 2]], [[3, 4]])
[[1, 2], [3, 4]]
```

Symbolic (unevaluated):
```
Flame> Join(x, [1, 2])
Join(x, [1, 2])
```

## Notes
- Does not mutate the original lists. Returns a new list.
- Unlike `Extend`, which mutates the first list and returns `Null`, `Join` is pure and returns the result.
- Useful with `Apply` and `Raw` for constructing unevaluated expressions with variadic arguments:
  `Apply(Raw, Join([Multinomial], args))`

## Errors
- Throws `FlameArityException` if fewer than 2 arguments are provided

## See Also
- [Extend](Extend.md) — mutating list concatenation
- [Append](Append.md) — append a single element
- [Apply](../general/Apply.md) — apply a function to a list of arguments
