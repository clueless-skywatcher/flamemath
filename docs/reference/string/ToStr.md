# ToStr

Converts an expression to its string representation.

## Syntax
```
ToStr(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to convert to a string |

## Returns
A `String` containing the string representation of `expr`.

## Examples
```
Flame> ToStr(42)
"42"
Flame> ToStr(3.14)
"3.14"
Flame> ToStr(True)
"True"
Flame> ToStr(x)
"x"
```

## Notes
- Takes exactly one argument.
- Converts the unevaluated argument using its `toString()` representation.

## See Also
- [StrLength](StrLength.md) — get the length of a string
