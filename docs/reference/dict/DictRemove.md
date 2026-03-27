# DictRemove

Returns a new dictionary with a key removed.

## Syntax
```
DictRemove(dict, key)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The original dictionary |
| `key` | Any (hashable) | The key to remove |

## Returns
A new `Dict` with the key removed. If the key does not exist, returns a copy of the original. The original dictionary is not modified.

## Examples
```
Flame> DictRemove({"a": 1, "b": 2}, "a")
Dict("b": 2)
Flame> DictRemove({"a": 1}, "z")
Dict("a": 1)
Flame> d = {"x": 1, "y": 2}
Flame> DictRemove(d, "x")
Dict("y": 2)
Flame> d
Dict("x": 1, "y": 2)
```

## Notes
- Does not mutate the original dictionary. Returns a new one.
- Removing a non-existent key is not an error.

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the first argument is not a Dict

## See Also
- [DictSet](DictSet.md) — add or update a key
- [Dict](Dict.md) — dictionary construction
