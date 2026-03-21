# Raw

Constructs an unevaluated compound expression from a function name and arguments.

## Syntax
```
Raw(name, args...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `name` | Symbol | The function name to use as the head |
| `args` | Any (variadic) | Arguments to include in the expression |

## Returns
A `Compound` expression with the given head and arguments, without evaluating it.

## Examples
```
Flame> Raw(Factorial, x)
Factorial(x)
Flame> Raw(Add, 1, 2)
1 + 2
Flame> Raw(Foo)
Foo()
```

## Notes
- Requires at least one argument (the function name).
- The first argument must be a symbol.
- Arguments are not evaluated — they are included as-is.
- Useful in stdlib functions to return the unevaluated form when arguments are symbolic:
  ```
  Factorial = (n) => If(!IsInteger(n), Raw(Factorial, n), If(n <= 1, 1, n * Factorial(n - 1)))
  ```

## See Also
- [Head](Head.md) — get the head of an expression
