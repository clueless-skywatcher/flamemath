# List

Constructs a list from its arguments. Arguments are evaluated before being added.

## Syntax
```
[a, b, c]
List(a, b, c)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Elements of the list |

## Returns
A `List` containing the evaluated arguments.

## Examples
```
Flame> [1, 2, 3]
[1, 2, 3]
Flame> List(1 + 1, 2 + 2, 3 + 3)
[2, 4, 6]
Flame> []
[]
Flame> [[1, 2], [3, 4]]
[[1, 2], [3, 4]]
```

## Notes
- Accepts any number of arguments, including zero.
- `[...]` bracket syntax is syntactic sugar for `List(...)`.

## See Also
- [At](../general/At.md) — access elements by index
- [Len](Len.md) — get list length
- [Append](Append.md), [Prepend](Prepend.md), [Extend](Extend.md) — modify lists
