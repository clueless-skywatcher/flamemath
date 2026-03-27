# StrReplace

Replaces all occurrences of a substring with a replacement string.

## Syntax
```
StrReplace(str, target, replacement)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `str` | String | The original string |
| `target` | String | The substring to find |
| `replacement` | String | The string to replace each occurrence with |

## Returns
- A new `String` with all occurrences of `target` replaced by `replacement`
- An unevaluated `StrReplace(str, target, replacement)` if any argument is not a string

## Examples
```
Flame> StrReplace("hello world", "world", "flame")
"hello flame"
Flame> StrReplace("a-a-a", "a", "x")
"x-x-x"
Flame> StrReplace("hello", "xyz", "abc")
"hello"
Flame> StrReplace("hello", "e", "")
"hllo"
```

Symbolic (unevaluated):
```
Flame> StrReplace(x, "a", "b")
StrReplace(x, "a", "b")
```

## Notes
- Replaces **all** occurrences, not just the first.
- The search is case-sensitive.
- If `target` is not found, the original string is returned unchanged.

## Errors
- Throws `FlameArityException` if not given exactly 3 arguments.

## See Also
- [StrHas](StrHas.md) — test whether a string contains a substring
- [StrSplit](StrSplit.md) — split a string by a delimiter
