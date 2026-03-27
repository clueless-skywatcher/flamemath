# Keys

Returns a list of all keys in a dictionary.

## Syntax
```
Keys(dict)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The dictionary to extract keys from |

## Returns
A `List` containing all keys in the dictionary.

## Examples
```
Flame> Keys({"a": 1, "b": 2})
["a", "b"]
Flame> Keys({})
[]
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws an error if the argument is not a Dict

## See Also
- [Values](Values.md) — get all values
- [Dict](Dict.md) — dictionary construction
