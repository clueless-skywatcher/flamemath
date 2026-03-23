# Log

Computes the natural logarithm (base e) of an expression.

## Syntax
```
Log(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to take the logarithm of |

## Returns
- `0` if the argument is `1`
- `1` if the argument is the constant `E`
- A `RealAtom` for real arguments via `Math.log`
- An unevaluated `Log(x)` for integers (other than 1) and symbolic arguments

## Examples

Special values:
```
Flame> Log(1)
0
Flame> Log(E)
1
```

Real arguments:
```
Flame> Log(2.0)
0.6931471805599453
Flame> Log(10.0)
2.302585092994046
```

Integer arguments stay symbolic:
```
Flame> Log(2)
Log(2)
Flame> Log(10)
Log(10)
```

Use `Num` for numeric evaluation:
```
Flame> Num(Log(2))
0.6931471805599453
```

Symbolic (unevaluated):
```
Flame> Log(x)
Log(x)
```

## Notes
- This is the natural logarithm (base e), not base 10
- Integer arguments remain symbolic to preserve exactness; use `Num()` for a float

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Exp](Exp.md) — exponential function
- [Num](Num.md) — force numeric evaluation
