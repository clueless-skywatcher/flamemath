# StrLength

Returns the length of a string.

## Syntax
```
StrLength(str)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `str` | String | The string to measure |

## Returns
An `Integer` representing the number of characters in the string.

## Examples
```
Flame> StrLength("hello")
5
Flame> StrLength("")
0
Flame> StrLength("hello world")
11
```

## Notes
- Takes exactly one argument.
- The argument must be a string; passing a non-string will throw an error.

## See Also
- [ToStr](ToStr.md) — convert an expression to a string
