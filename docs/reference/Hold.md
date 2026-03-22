# Hold

Returns its argument without evaluating it.

## Syntax
```
Hold(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to hold unevaluated |

## Returns
- The expression exactly as written, without evaluation

## Examples

Prevent variable resolution:
```
Flame> { x = 42; Hold(x) }
x
Flame> Hold(1 + 2)
1 + 2
```

Prevent function evaluation:
```
Flame> Hold(Sin(0))
Sin(0)
```

Atomic values pass through unchanged:
```
Flame> Hold(42)
42
```

Useful with `Apply` and `Raw` for constructing unevaluated expressions:
```
Flame> Apply(Raw, [Hold(Sin), 0])
Sin(0)
```

This pattern is used in stdlib for variadic Raw guards:
```
Multinomial = (n, ...k) => {
    raw = Apply(Raw, Join([Hold(Multinomial), n], k));
    ...
}
```

## Notes
- All arguments are held (`holdAll`), so nothing inside `Hold` is evaluated.
- `Hold` is the identity function on the unevaluated AST.

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Raw](Raw.md) — construct an unevaluated function call
- [Apply](Apply.md) — apply a function to a list of arguments
