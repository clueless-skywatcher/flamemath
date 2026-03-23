# Set

Assigns a value to a variable.

## Syntax
```
x = value
Set(x, value)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `symbol` | Symbol | The variable name (held, not evaluated) |
| `value` | Any | The value to assign (evaluated before assignment) |

## Returns
The assigned value.

## Examples
```
Flame> x = 5
5
Flame> x
5
Flame> x = x + 1
6
```

## Notes
- The left-hand side is held: `Set(x, 5)` does not evaluate `x` before assigning.
- The right-hand side is evaluated before assignment.

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an error if the left-hand side is not a symbol

## See Also
- [Seq](Seq.md) — evaluate a sequence of statements
