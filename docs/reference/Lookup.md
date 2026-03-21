# Lookup

Retrieves the value associated with a key in a dictionary.

## Syntax
```
Lookup(dict, key)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The dictionary to search |
| `key` | Any (hashable) | The key to look up |

## Returns
The value associated with the key, or `Null` if the key is not found.

## Examples
```
Flame> d = {"a": 1, "b": 2}
Flame> Lookup(d, "a")
1
Flame> Lookup(d, "b")
2
Flame> Lookup(d, "c")
Null
```

With integer keys:
```
Flame> Lookup({1: "one"}, 1)
"one"
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a Dict

## See Also
- [Dict](Dict.md) — dictionary construction
- [HasKey](HasKey.md) — check if a key exists
