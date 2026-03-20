# PrintLn

Prints expressions to standard output followed by a newline.

## Syntax
```
PrintLn(args...)
PrintLn()
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to print, separated by spaces |

## Returns
`Null`

## Examples
```
Flame> PrintLn("Hello World!")
Hello World!
Flame> PrintLn(1 + 2)
3
Flame> PrintLn("x is", 42)
x is 42
Flame> PrintLn()

```

## Notes
- Accepts any number of arguments, including zero (prints an empty line).
- Multiple arguments are space-separated.
- String quotes are stripped in the output.

## See Also
- [Exit](Exit.md) — exit the REPL
