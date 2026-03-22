# For

Iterates over a list, binding each element to a variable and evaluating the body.

## Syntax
```
For(var, list, body)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `var` | Symbol | The loop variable name (not evaluated) |
| `list` | List | The list to iterate over (evaluated) |
| `body` | Expr | The expression to evaluate on each iteration (held) |

## Returns
`Null`. Use `For` for side effects; use `Map` if you need a transformed list.

## Examples

Basic iteration:
```
Flame> For(i, [1, 2, 3], PrintLn(i))
1
2
3
```

Accumulation:
```
Flame> s = 0
0
Flame> For(i, [1, 2, 3, 4], s = s + i)
Flame> s
10
```

With a block body:
```
Flame> s = 0
0
Flame> For(i, [1, 2, 3], {
    s = s + i;
    PrintLn(s)
})
1
3
6
```

With a variable as the list:
```
Flame> l = [10, 20, 30]
Flame> For(i, l, PrintLn(i))
10
20
30
```

## Notes
- The loop variable is scoped to the `For` body and does not leak into the outer scope.
- The body can access and modify variables from the outer scope.
- The list argument is evaluated before iteration; the variable and body are held.
- An empty list results in no iterations.

## Errors
- Throws `FlameArityException` if the number of arguments is not exactly 3
- Throws an error if the first argument is not a symbol
- Throws an error if the second argument is not a list

## See Also
- [While](While.md) — loop with a condition
- [Map](Map.md) — apply a function to each element and collect results
