# Merge

Combines two dictionaries into one. Keys from the second dictionary take precedence.

## Syntax
```
Merge(dict1, dict2)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict1` | Dict | The base dictionary |
| `dict2` | Dict | The dictionary to merge in (wins on conflicts) |

## Returns
A new `Dict` containing all key-value pairs from both dictionaries. If a key exists in both, the value from `dict2` is used.

## Examples
```
Flame> Merge({"a": 1}, {"b": 2})
Dict("a": 1, "b": 2)
Flame> Merge({"a": 1}, {"a": 99})
Dict("a": 99)
Flame> Merge({"a": 1, "b": 2}, {})
Dict("a": 1, "b": 2)
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if either argument is not a Dict

## See Also
- [DictSet](DictSet.md) — add a single key
- [Dict](Dict.md) — dictionary construction
