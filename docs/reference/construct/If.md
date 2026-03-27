# If

Conditional evaluation. Evaluates and returns one of two branches based on a condition.

## Syntax
```
If(condition, then)
If(condition, then, else)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `condition` | Any | Expression that evaluates to `True` or `False` |
| `then` | Any | Expression to evaluate if condition is `True` |
| `else` | Any (optional) | Expression to evaluate if condition is `False` |

## Returns
- The evaluated `then` expression if condition is `True`
- The evaluated `else` expression if condition is `False` and an else branch is provided
- `Null` if condition is `False` and no else branch is provided

## Examples
```
Flame> If(3 > 2, "yes", "no")
yes
Flame> If(False, "yes")
Null
Flame> x = If(True, 42, 0)
42
```

## Notes
- Arguments are held (not pre-evaluated). Only the chosen branch is evaluated.
- `If` does not support else-if chains. Nest `If` calls instead.

## Errors
- Throws `FlameArityException` if fewer than 2 or more than 3 arguments are provided

## See Also
- [While](While.md) — loop construct
