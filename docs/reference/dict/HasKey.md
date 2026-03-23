# HasKey

Checks whether a dictionary contains a given key.

## Syntax
```
HasKey(dict, key)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The dictionary to check |
| `key` | Any (hashable) | The key to look for |

## Returns
`True` if the key exists in the dictionary, `False` otherwise.

## Examples
```
Flame> HasKey({"a": 1, "b": 2}, "a")
True
Flame> HasKey({"a": 1, "b": 2}, "c")
False
Flame> HasKey({}, "x")
False
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a Dict

## See Also
- [Lookup](Lookup.md) — retrieve a value by key
- [Dict](Dict.md) — dictionary construction
