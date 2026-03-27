# Fibonacci

Computes the nth Fibonacci number.

## Syntax
```
Fibonacci(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer |

## Returns
An `Integer` — the nth Fibonacci number.

## Examples
```
Flame> Fibonacci(0)
0
Flame> Fibonacci(1)
1
Flame> Fibonacci(6)
8
Flame> Fibonacci(10)
55
```

## Notes
- Defined in `math.flame` using recursion.
- `Fibonacci(0)` returns `0`, `Fibonacci(1)` returns `1`.
- Uses naive recursion — may be slow for large values of `n`.

## See Also
- [Factorial](Factorial.md) — factorial of n
