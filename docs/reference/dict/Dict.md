# Dict

Creates a dictionary from key-value entries.

## Syntax
```
{}
{"key": value, ...}
Dict("key": value, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `entries` | DictEntry... | Zero or more `key: value` pairs |

## Returns
A `Dict` expression containing the given key-value mappings.

## Examples

Empty dictionary:
```
Flame> {}
Dict()
Flame> Head({})
Dict
```

Dictionary literal with string keys:
```
Flame> {"name": "Alice", "age": 30}
Dict("name": "Alice", "age": 30)
```

Integer keys:
```
Flame> {1: "one", 2: "two"}
Dict(1: "one", 2: "two")
```

Using the `Dict` function:
```
Flame> Dict("x": 10, "y": 20)
Dict("x": 10, "y": 20)
```

Expression values are evaluated:
```
Flame> {"sum": 2 + 3}
Dict("sum": 5)
```

## Notes
- Keys must be hashable (atomic types: Integer, Real, Complex, String, Symbol, Boolean, Null).
- Lists, Dicts, Compounds, and Lambdas cannot be used as keys.
- Duplicate keys are allowed; the last value wins.

## Errors
- Throws `UnsupportedOperationException` if a non-hashable expression is used as a key

## See Also
- [Lookup](Lookup.md) — retrieve a value by key
- [Keys](Keys.md) — get all keys
- [Values](Values.md) — get all values
