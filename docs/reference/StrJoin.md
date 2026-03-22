# StrJoin

Joins a list of strings into a single string using a delimiter.

## Syntax
```
StrJoin(list, delimiter)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `list` | List | A list of strings to join |
| `delimiter` | String | The separator placed between each element |

## Returns
A `String` formed by concatenating all elements of the list, separated by the delimiter.

## Examples
```
Flame> StrJoin(["a", "b", "c"], ",")
"a,b,c"
Flame> StrJoin(["hello", "world"], " ")
"hello world"
Flame> StrJoin(["a", "b", "c"], "")
"abc"
Flame> StrJoin([], ",")
""
```

## Errors
- Throws `FlameArityException` if not given exactly 2 arguments.
- Throws an error if the first argument is not a list.
- Throws an error if the list contains non-string elements.
- Throws an error if the delimiter is not a string.

## See Also
- [StrSplit](StrSplit.md) — split a string into a list
- [StrLength](StrLength.md) — get the length of a string
