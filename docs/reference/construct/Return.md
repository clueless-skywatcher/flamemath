# Return

Exits the current function early, returning a value. Implemented as an exception that unwinds through loops, conditionals, and blocks until it hits the enclosing lambda boundary.

## Syntax
```
Return(value)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `value` | Any | The expression to evaluate and return |

## Returns
Does not return normally. Throws internally to unwind the call stack.

## Examples
```
Flame> F = (x) => {
    While(True, {
        If(x == 5, Return(x ^ 2));
        x = x + 1
    })
}
Flame> F(0)
25
```

## Notes
- Can only be used inside a function (lambda) body.
- Unwinds through `While`, `If`, and `Seq` blocks.

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [While](While.md) — loop construct
- [If](If.md) — conditional evaluation
