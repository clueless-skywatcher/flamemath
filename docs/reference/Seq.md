# Seq

Evaluates a sequence of expressions and returns the last result. This is the underlying function for `{ ... }` blocks.

## Syntax
```
{ a; b; c }
Seq(a, b, c)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to evaluate in order |

## Returns
The result of the last expression.

## Examples
```
Flame> {1 + 2; 3 + 1; 4 + 5}
9
Flame> {x = 5; y = 10; x + y}
15
```

When all expressions are lambdas, returns a `Seq` of lambdas (used for arity overloading):
```
Flame> F = {
    (a, b) => a + b;
    (a) => a * 2
}
Flame> F(2, 3)
5
Flame> F(5)
10
```

## Notes
- `Seq` is flat: `Seq(Seq(a, b), c)` becomes `Seq(a, b, c)`
- Arguments are held (not pre-evaluated). Each is evaluated in order.
- No trailing semicolon on the last expression.

## See Also
- [Set](Set.md) — variable assignment
- [Return](Return.md) — early exit from a function
