# Apply

Applies a function to a list of arguments, unpacking the list as individual arguments.

## Syntax
```
Apply(func, list)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `func` | Symbol, Lambda, or overloaded Seq | The function to apply |
| `list` | List | The arguments to unpack |

## Returns
- The result of calling `func` with the elements of `list` as individual arguments
- If `list` is not a `List`, returns unevaluated
- If `func` is not a callable (Symbol, Lambda, or Seq), returns unevaluated

## Examples

With a builtin:
```
Flame> Apply(Add, [1, 2, 3])
6
Flame> Apply(Mul, [2, 3, 4])
24
```

With a lambda:
```
Flame> Apply((x, y) => x + y, [3, 4])
7
Flame> Apply((x) => x * x, [5])
25
```

With a variable:
```
Flame> { f = (a, b) => a + b; Apply(f, [10, 20]) }
30
Flame> { l = [1, 2, 3]; Apply(Add, l) }
6
```

With overloaded functions:
```
Flame> {
    f = {
        (x) => x * 2;
        (x, y) => x + y
    };
    Apply(f, [5])
}
10
```

With variadic lambdas:
```
Flame> Apply((x, ...rest) => rest, [1, 2, 3])
[2, 3]
```

Symbolic (unevaluated):
```
Flame> Apply(Add, x)
Apply(Add, x)
Flame> Apply(42, [1, 2])
Apply(42, [1, 2])
```

## Notes
- The first argument is held (not evaluated), so `Apply(Add, ...)` passes the symbol `Add` directly rather than trying to call it.
- The second argument is evaluated, so list variables and expressions work as expected.
- For overloaded functions, exact arity matches are preferred over variadic fallbacks.

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Map](Map.md) — apply a function to each element of a list
- [Fold](Fold.md) — reduce a list with a function
