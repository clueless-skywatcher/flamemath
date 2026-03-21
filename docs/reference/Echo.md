# Echo

Prints the evaluated result of an expression to standard output and returns it.

## Syntax
```
Echo(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to evaluate, print, and return |

## Returns
The evaluated result of `expr`.

## Examples
```
Flame> Echo(1 + 2)
3
3
Flame> Set(x, Echo(5 * 4))
20
Flame> x
20
```

## Notes
- Requires at least one argument.
- Prints the result and also returns it, making it useful for inline debugging.

## See Also
- [PrintLn](PrintLn.md) — print expressions without returning them
