# Exit

Exits the REPL process.

## Syntax
```
Exit()
Exit(code)
```

## Parameters
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `code` | Integer | `0` | Exit code |

## Returns
Does not return. Terminates the process.

## Examples
```
Flame> Exit()
```

```
Flame> Exit(1)
```

## Notes
- With no arguments, exits with code `0`.
- If the argument is not an integer, returns the expression unevaluated.

## See Also
- [PrintLn](PrintLn.md) — print output
