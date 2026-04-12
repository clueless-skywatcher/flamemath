# Pow

Raises a base to an exponent. Performs symbolic simplification when possible.

## Syntax
```
a ^ b
Pow(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `base` | Any | The base expression |
| `exponent` | Any | The exponent expression |

## Returns
- A numeric result if both arguments are numeric
- A simplified symbolic expression otherwise

## Examples

Numeric exponentiation:
```
Flame> 2 ^ 3
8
Flame> 2 ^ -1
(1/2)
Flame> 4 ^ -2
(1/16)
```

Rational exponents and square roots:
```
Flame> Sqrt(4)
2
Flame> Sqrt(2)
Pow(2, (1/2))
Flame> Sqrt(2) * Sqrt(2)
2
```

Symbolic simplification:
```
Flame> x ^ 0
1
Flame> x ^ 1
x
Flame> 0 ^ 5
0
Flame> 1 ^ x
1
```

Nested powers are flattened:
```
Flame> (x ^ 2) ^ 3
x^6
```

Powers of `Sqrt` simplify by halving the exponent:
```
Flame> Sqrt(x) ^ 2
x
Flame> Sqrt(x) ^ 4
x^2
Flame> Sqrt(x) ^ 3
x^(3/2)
```

Power of a product distributes:
```
Flame> (a * b) ^ 2
a^2*b^2
```

## Notes
- `^` is right-associative: `2^3^4` is `2^(3^4)`
- Negative integer exponents on integer bases produce `RationalAtom` (e.g., `2^-1` returns `(1/2)`)
- Negative integer exponents on real bases produce `RealAtom` (e.g., `2.0^-1` returns `0.5`)
- `Pow(n, (1/2))` returns the integer root for perfect squares, stays symbolic otherwise
- `Pow(Sqrt(b), e)` simplifies to `Pow(b, e * (1/2))` — e.g., `Sqrt(x)^2` evaluates to `x`
- `0^0` evaluates to `1` (by convention)

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Add](Add.md) — addition
- [Mul](Mul.md) — multiplication
