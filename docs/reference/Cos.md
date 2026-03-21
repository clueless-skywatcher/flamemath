# Cos

Computes the cosine of an expression. Returns exact symbolic values for well-known multiples of Pi, and numeric results for numeric arguments.

## Syntax
```
Cos(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The angle expression |

## Returns
- A `RealAtom` if the argument is a numeric integer or real
- An exact symbolic value for rational multiples of Pi (multiples of Pi/6 and Pi/4)
- An unevaluated `Cos(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Cos(0)
1.0
Flame> Cos(1)
0.5403023058681398
```

Exact values at well-known angles:
```
Flame> Cos(Pi / 6)
(1/2)*Sqrt(3)
Flame> Cos(Pi / 4)
(1/2)*Sqrt(2)
Flame> Cos(Pi / 3)
(1/2)
Flame> Cos(Pi / 2)
0
Flame> Cos(Pi)
-1
```

Quadrant reduction:
```
Flame> Cos(2 * Pi / 3)
(-1/2)
Flame> Cos(5 * Pi / 4)
-1*(1/2)*Sqrt(2)
Flame> Cos(5 * Pi / 3)
(1/2)
```

Periodicity and negative arguments:
```
Flame> Cos(2 * Pi)
1
Flame> Cos(-Pi / 3)
(1/2)
```

Symbolic (unevaluated):
```
Flame> Cos(x)
Cos(x)
```

## Notes
- Uses a 5-entry seed table for the range [0, Pi/2], with quadrant reduction handling all other angles
- `Cos(integer)` evaluates numerically via `Math.cos` (radians)
- The seed table covers Pi/6 and Pi/4 increments only

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Sin](Sin.md) — sine
- [Tan](Tan.md) — tangent
- [Num](Num.md) — force numeric evaluation
