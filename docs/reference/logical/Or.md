# Or

Logical OR with short-circuit evaluation.

## Syntax
```
a || b
Or(a, b)
Or(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to disjoin |

## Returns
- `True` immediately if any argument evaluates to `True` (short-circuit)
- `False` if all arguments evaluate to `False`
- A simplified expression if some arguments are symbolic

## Examples
```
Flame> False || True
True
Flame> False || False
False
Flame> False || x
x
Flame> True || x
True
```

## Notes
- Short-circuits: `Or(True, ...)` returns `True` without evaluating the rest
- Flat: `Or(Or(a, b), c)` becomes `Or(a, b, c)`
- Identity elements are dropped: `Or(False, x)` returns `x`

## Errors
None. Accepts any number of arguments.

## See Also
- [And](And.md) — logical AND
- [Not](Not.md) — logical NOT
