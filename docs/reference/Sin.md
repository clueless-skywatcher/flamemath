# Sin

Computes the sine of an expression. Returns exact symbolic values for well-known multiples of Pi, and numeric results for numeric arguments.

## Syntax
```
Sin(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The angle expression |

## Returns
- A `RealAtom` if the argument is a numeric integer or real
- An exact symbolic value for rational multiples of Pi (multiples of Pi/6 and Pi/4)
- An unevaluated `Sin(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Sin(0)
0.0
Flame> Sin(1)
0.8414709848078965
Flame> Sin(1.5)
0.9974949866315225
```

Exact values at well-known angles:
```
Flame> Sin(Pi / 6)
(1/2)
Flame> Sin(Pi / 4)
(1/2)*Sqrt(2)
Flame> Sin(Pi / 3)
(1/2)*Sqrt(3)
Flame> Sin(Pi / 2)
1
Flame> Sin(Pi)
0
```

Quadrant reduction:
```
Flame> Sin(2 * Pi / 3)
(1/2)*Sqrt(3)
Flame> Sin(7 * Pi / 6)
(-1/2)
Flame> Sin(5 * Pi / 3)
-1*(1/2)*Sqrt(3)
```

Periodicity and negative arguments:
```
Flame> Sin(2 * Pi)
0
Flame> Sin(-Pi / 2)
-1
```

Symbolic (unevaluated):
```
Flame> Sin(x)
Sin(x)
```

## Notes
- Uses a seed table with quadrant reduction covering all multiples of Pi/6 and Pi/4
- `Sin(integer)` evaluates numerically via `Math.sin` (radians)
- Symbolic expressions that are not rational multiples of Pi remain unevaluated

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Cos](Cos.md) — cosine
- [Tan](Tan.md) — tangent
- [Num](Num.md) — force numeric evaluation
