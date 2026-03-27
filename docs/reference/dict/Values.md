# Values

Returns a list of all values in a dictionary.

## Syntax
```
Values(dict)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `dict` | Dict | The dictionary to extract values from |

## Returns
A `List` containing all values in the dictionary.

## Examples
```
Flame> Values({"a": 1, "b": 2})
[1, 2]
Flame> Values({})
[]
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws an error if the argument is not a Dict

## See Also
- [Keys](Keys.md) — get all keys
- [Dict](Dict.md) — dictionary construction
