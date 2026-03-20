# While

Repeatedly evaluates an expression while a condition is true.

## Syntax
```
While(condition, body)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `condition` | Any | Expression re-evaluated each iteration; loop continues while `True` |
| `body` | Any | Expression to evaluate each iteration |

## Returns
`Null`

## Examples
```
Flame> i = 0
0
Flame> While(i < 5, {PrintLn(i); i = i + 1})
0
1
2
3
4
```

## Notes
- Arguments are held (not pre-evaluated). Both condition and body are re-evaluated each iteration.
- Use `Return` inside a function to break out of a `While` loop early.

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [If](If.md) — conditional evaluation
- [Return](Return.md) — early exit from a function
