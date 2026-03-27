# Num

Forces numeric (floating-point) evaluation of an expression. Converts all exact values to `RealAtom` approximations.

## Syntax
```
Num(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to evaluate numerically |

## Returns
- A `RealAtom` for integers, rationals, and well-known constants
- A `RealAtom` for expressions that can be fully numerified
- The original symbolic expression for unresolvable symbols

## Examples

Constants:
```
Flame> Num(Pi)
3.141592653589793
Flame> Num(E)
2.718281828459045
```

Integers and rationals become reals:
```
Flame> Num(42)
42.0
Flame> Num(1/3)
0.3333333333333333
```

Symbolic math functions:
```
Flame> Num(Sqrt(2))
1.4142135623730951
Flame> Num(Log(2))
0.6931471805599453
Flame> Num(Sin(Pi / 6))
0.49999999999999994
```

Composed expressions:
```
Flame> Num(Pi + E)
5.859874482048838
Flame> Num(Sqrt(Pi + E))
2.4206427630468597
Flame> Num(Exp(Sin(Pi / 2)))
2.718281828459045
```

Unknown symbols stay symbolic:
```
Flame> Num(x)
x
Flame> Num(x + 1)
1.0 + x
```

## Notes
- Recursively numerifies all children of compound expressions, then re-evaluates
- If re-evaluation still produces a symbolic compound, calls the function's `numerify` method as a fallback
- Well-known constants: `Pi` and `E`
- All numeric types (integer, rational) are converted to `RealAtom`

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Sin](Sin.md), [Cos](Cos.md), [Tan](Tan.md) — trigonometric functions
- [Sqrt](Sqrt.md) — square root
- [Log](Log.md) — natural logarithm
- [Exp](Exp.md) — exponential function
