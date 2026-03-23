# Not

Logical negation.

## Syntax
```
!a
Not(a)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Any | The expression to negate |

## Returns
- `False` if the argument is `True`
- `True` if the argument is `False`
- The unevaluated expression `Not(a)` if the argument is symbolic

## Examples
```
Flame> !True
False
Flame> !False
True
Flame> !x
Not(x)
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [And](And.md) — logical AND
- [Or](Or.md) — logical OR
