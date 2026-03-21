# Exp

Computes the exponential function e^x. Delegates to `Pow(E, x)`.

## Syntax
```
Exp(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The exponent expression |

## Returns
- The result of `Pow(E, x)`, which applies standard power rules:
  - `Exp(0)` returns `1`
  - `Exp(1)` returns `E`
  - `Exp(n)` for other integers returns `Pow(E, n)` (symbolic)
- A `RealAtom` when forced via `Num()`

## Examples

Identity cases:
```
Flame> Exp(0)
1
Flame> Exp(1)
E
```

Symbolic:
```
Flame> Exp(2)
Pow(E, 2)
Flame> Exp(x)
Pow(E, x)
```

Use `Num` for numeric evaluation:
```
Flame> Num(Exp(1))
2.718281828459045
Flame> Num(Exp(2))
7.38905609893065
```

## Notes
- `Exp(x)` is syntactic sugar for `Pow(E, x)`
- All standard `Pow` rules apply (e.g., `Exp(0)` = `E^0` = `1`)
- `E` is a protected constant symbol

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Log](Log.md) — natural logarithm (inverse of Exp)
- [Pow](Pow.md) — exponentiation
- [Num](Num.md) — force numeric evaluation
