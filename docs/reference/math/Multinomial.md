# Multinomial

Computes the multinomial coefficient: the number of ways to partition `n + k1 + k2 + ...` items into groups of sizes `n, k1, k2, ...`.

## Syntax
```
Multinomial(n, k1)
Multinomial(n, k1, k2, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | Size of the first group |
| `k1, k2, ...` | Integer (variadic) | Sizes of the remaining groups |

## Definition

```
Multinomial(n, k1, k2, ...) = (n + k1 + k2 + ...)! / (n! * k1! * k2! * ...)
```

## Returns
- An integer: the multinomial coefficient
- If any argument is not an integer, returns unevaluated

## Examples

Three groups:
```
Flame> Multinomial(3, 2, 1)
60
Flame> Multinomial(2, 2, 2)
90
```

Two groups (equivalent to Binomial):
```
Flame> Multinomial(3, 2)
10
```

Four groups:
```
Flame> Multinomial(2, 2, 2, 1)
12600
```

Edge cases:
```
Flame> Multinomial(1, 1, 1)
6
Flame> Multinomial(5, 0, 0)
1
```

Symbolic (unevaluated):
```
Flame> Multinomial(x, 2, 1)
Multinomial(x, 2, 1)
```

## Notes
- Uses `Product(Range(...))` internally, avoiding full factorial computation.
- With two arguments, `Multinomial(a, b)` equals `Binomial(a + b, b)`.
- Uses variadic arguments (`...k`) to accept any number of group sizes.

## Errors
- Requires at least 2 arguments

## See Also
- [Binomial](Binomial.md) — two-group special case
- [Factorial](Factorial.md) — factorial function
- [Product](Product.md) — product of a list
