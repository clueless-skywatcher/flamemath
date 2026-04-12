# Sqrt

Computes the square root of an expression. Returns exact integers for perfect squares, extracts perfect-square factors from non-perfect-square integers and products, and returns numeric results for reals. All results display using the `√` symbol.

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
- A simplified radical for non-perfect-square integers with a perfect-square factor (e.g., `Sqrt(12)` returns `2*√3`)
- A symbolic `√n` for square-free integers (e.g., `Sqrt(2)` returns `√2`)
- A simplified product for products with numeric perfect-square factors (e.g., `Sqrt(4*x^2)` returns `2*√(x^2)`)
- A `RealAtom` for real arguments (e.g., `Sqrt(2.0)` returns `1.4142...`)
- An unevaluated `√x` for symbolic arguments

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

Non-perfect squares with perfect-square factors:
```
Flame> Sqrt(8)
2*√2
Flame> Sqrt(12)
2*√3
Flame> Sqrt(18)
3*√2
Flame> Sqrt(72)
6*√2
```

Square-free integers (symbolic):
```
Flame> Sqrt(2)
√2
Flame> Sqrt(3)
√3
```

Extracting numeric factors from products:
```
Flame> Sqrt(4 * x^2)
2*√(x^2)
Flame> Sqrt(9 * x)
3*√x
Flame> Sqrt(8 * x)
2*√(2*x)
```

Symbolic square roots cancel via power grouping:
```
Flame> Sqrt(2) * Sqrt(2)
2
Flame> Sqrt(3) * Sqrt(3)
3
```

Powers of `Sqrt` simplify via `Pow`:
```
Flame> Sqrt(x) ^ 2
x
Flame> Sqrt(x) ^ 4
x^2
Flame> Sqrt(x) ^ 3
x^(3/2)
```

Real arguments:
```
Flame> Sqrt(2.0)
1.4142135623730951
```

Symbolic (unevaluated):
```
Flame> Sqrt(x)
√x
```

## Notes
- Perfect-square factor extraction uses trial division to find the largest $k$ such that $k^2$ divides the integer argument
- For products (`Mul`), only numeric integer factors are extracted; symbolic factors like `x^2` remain under the radical
- This matches the conservative approach used by Mathematica — `Sqrt(x^2)` does **not** simplify to `x` because that would be incorrect for negative $x$ (since $\sqrt{x^2} = |x|$)
- Use `Num(Sqrt(2))` to get the numeric approximation

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Pow](../arithmetic/Pow.md) — exponentiation
- [Num](Num.md) — force numeric evaluation
