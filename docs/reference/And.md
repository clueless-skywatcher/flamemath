# And

Logical AND with short-circuit evaluation.

## Syntax
```
a && b
And(a, b)
And(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to conjoin |

## Returns
- `False` immediately if any argument evaluates to `False` (short-circuit)
- `True` if all arguments evaluate to `True`
- A simplified expression if some arguments are symbolic

## Examples
```
Flame> True && True
True
Flame> True && False
False
Flame> True && x
x
Flame> False && x
False
```

## Notes
- Short-circuits: `And(False, ...)` returns `False` without evaluating the rest
- Flat: `And(And(a, b), c)` becomes `And(a, b, c)`
- Identity elements are dropped: `And(True, x)` returns `x`

## Errors
None. Accepts any number of arguments.

## See Also
- [Or](Or.md) — logical OR
- [Not](Not.md) — logical NOT
