# SubStr

Extracts a substring from a string.

## Syntax
```
SubStr(str, count)
SubStr(str, start, end)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `str` | String | The source string |
| `count` | Integer | Number of characters to take from the start (2-arg form) |
| `start` | Integer | Start index, exclusive (3-arg form, 1-based) |
| `end` | Integer | End index, exclusive (3-arg form, 1-based) |

## Returns
A `String` containing the extracted substring.

## Examples
```
Flame> SubStr("hello", 2)
"he"
Flame> SubStr("hello", 3)
"hel"
Flame> SubStr("hello", 1, 4)
"ell"
Flame> SubStr("hello", 1, 2)
"e"
Flame> SubStr("hello", 1, 5)
"ello"
```

## Notes
- The 2-argument form takes the first `count` characters.
- The 3-argument form returns characters between `start` and `end`, exclusive of both endpoints (1-based indexing).
- Throws an error on an empty string.

## Errors
- Throws `FlameArityException` if given fewer than 2 or more than 3 arguments.
- Throws an error if the first argument is not a string.
- Throws an error if the index arguments are not integers.

## See Also
- [StrSplit](StrSplit.md) — split a string by delimiter
- [StrLength](StrLength.md) — get the length of a string
