# Map

Applies a function to each element of a list, returning a new list of results.

## Syntax
```
Map(func, list)
Map(func, expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `func` | Symbol or Lambda | The function to apply |
| `list` | List or Any | The list to map over, or a single expression |

## Returns
- A new `List` with the function applied to each element
- If the second argument is not a list, applies the function to it directly

## Examples

With a lambda:
```
Flame> Map((x) => x + 1, [1, 2, 3])
[2, 3, 4]
Flame> Map((x) => x * 2, [1, 2, 3])
[2, 4, 6]
```

With a named function (symbol):
```
Flame> Map(Head, [1, "hello", x])
[Integer, String, Symbol]
```

Non-list second argument:
```
Flame> Map((x) => x + 1, 10)
11
```

Nested lists:
```
Flame> Map(Len, [[1, 2], [3, 4, 5]])
[2, 3]
```

## Notes
- Does not mutate the original list. Returns a new list.
- If `func` is neither a Symbol nor a Lambda, returns the expression unevaluated.

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [GenList](GenList.md) — generate lists with an expression
- [List](List.md) — list construction
