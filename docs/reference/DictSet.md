# DictSet

Returns a new dictionary with a key added or updated.

## Syntax
```
DictSet(dict, key, value)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The original dictionary |
| `key` | Any (hashable) | The key to set |
| `value` | Any | The value to associate with the key |

## Returns
A new `Dict` with the key-value pair added or updated. The original dictionary is not modified.

## Examples
```
Flame> DictSet({}, "a", 10)
Dict("a": 10)
Flame> DictSet({"a": 1}, "a", 99)
Dict("a": 99)
Flame> d = {"x": 1}
Flame> DictSet(d, "y", 2)
Dict("x": 1, "y": 2)
Flame> d
Dict("x": 1)
```

## Notes
- Does not mutate the original dictionary. Returns a new one.
- The key must be hashable (atomic types only).

## Errors
- Throws `FlameArityException` if not exactly 3 arguments are provided
- Throws an error if the first argument is not a Dict
- Throws `UnsupportedOperationException` if the key is not hashable

## See Also
- [DictRemove](DictRemove.md) — remove a key
- [Dict](Dict.md) — dictionary construction
