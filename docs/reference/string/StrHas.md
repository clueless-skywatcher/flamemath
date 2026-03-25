# StrHas

Tests whether a string contains a given substring.

## Syntax
```
StrHas(str, substring)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `str` | String | The string to search in |
| `substring` | String | The substring to search for |

## Returns
- `True` if `str` contains `substring`
- `False` otherwise
- An unevaluated `StrHas(str, substring)` if either argument is not a string

## Examples
```
Flame> StrHas("hello world", "world")
True
Flame> StrHas("hello world", "hello")
True
Flame> StrHas("hello", "xyz")
False
Flame> StrHas("Hello", "hello")
False
Flame> StrHas("hello", "")
True
```

Symbolic (unevaluated):
```
Flame> StrHas(x, "hello")
StrHas(x, "hello")
```

## Notes
- The search is case-sensitive.
- An empty substring is considered to be contained in any string.

## Errors
- Throws `FlameArityException` if not given exactly 2 arguments.

## See Also
- [StrReplace](StrReplace.md) — replace occurrences of a substring
- [SubStr](SubStr.md) — extract a substring by position
