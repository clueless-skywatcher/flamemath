# Triangular

Computes the nth triangular number — the sum of integers from 1 to n.

## Syntax
```
Triangular(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A positive integer |

## Returns
An `Integer` equal to `1 + 2 + ... + n`.

## Examples
```
Flame> Triangular(1)
1
Flame> Triangular(5)
15
Flame> Triangular(10)
55
Flame> Triangular(100)
5050
```

## Notes
- Defined in `math.flame` as `Sum(Range(1, n + 1))`.
- Equivalent to `n * (n + 1) / 2`.

## See Also
- [Sum](Sum.md) — sum all elements of a list
- [Factorial](Factorial.md) — factorial of n
