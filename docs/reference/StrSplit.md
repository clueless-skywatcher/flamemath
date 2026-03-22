# StrSplit

Splits a string into a list of substrings using a delimiter.

## Syntax
```
StrSplit(str, delimiter)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `str` | String | The string to split |
| `delimiter` | String | The separator to split on |

## Returns
A `List` of strings produced by splitting the input string at each occurrence of the delimiter.

## Examples
```
Flame> StrSplit("a,b,c", ",")
["a", "b", "c"]
Flame> StrSplit("hello world", " ")
["hello", "world"]
Flame> StrSplit("hello", ",")
["hello"]
Flame> StrSplit("a,,b", ",")
["a", "", "b"]
```

## Errors
- Throws `FlameArityException` if not given exactly 2 arguments.
- Throws an error if the first argument is not a string.
- Throws an error if the delimiter is not a string.

## See Also
- [StrJoin](StrJoin.md) — join a list of strings into one string
- [SubStr](SubStr.md) — extract a substring
