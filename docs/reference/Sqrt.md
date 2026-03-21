# Sqrt

Computes the square root of an expression. Returns exact integers for perfect squares, symbolic `Pow(n, (1/2))` for non-perfect-square integers, and numeric results for reals.

## Syntax
```
Sqrt(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to take the square root of |

## Returns
- An `IntegerAtom` for perfect square integers (e.g., `Sqrt(4)` returns `2`)
- A symbolic `Pow(n, (1/2))` for non-perfect-square integers (e.g., `Sqrt(2)` returns `Pow(2, (1/2))`)
- A `RealAtom` for real arguments (e.g., `Sqrt(2.0)` returns `1.4142...`)
- An unevaluated `Sqrt(x)` for symbolic arguments

## Examples

Perfect squares:
```
Flame> Sqrt(4)
2
Flame> Sqrt(9)
3
Flame> Sqrt(100)
10
```

Non-perfect squares (symbolic):
```
Flame> Sqrt(2)
Pow(2, (1/2))
Flame> Sqrt(3)
Pow(3, (1/2))
```

Symbolic square roots cancel via power grouping:
```
Flame> Sqrt(2) * Sqrt(2)
2
Flame> Sqrt(3) * Sqrt(3)
3
```

Real arguments:
```
Flame> Sqrt(2.0)
1.4142135623730951
```

Symbolic (unevaluated):
```
Flame> Sqrt(x)
Sqrt(x)
```

## Notes
- Internally delegates to `Pow(x, (1/2))` for integer and rational arguments
- Perfect square detection uses `PowFunc` — `Pow(n, (1/2))` returns the integer root when `n` is a perfect square
- Use `Num(Sqrt(2))` to get the numeric approximation

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Pow](Pow.md) — exponentiation
- [Num](Num.md) — force numeric evaluation
