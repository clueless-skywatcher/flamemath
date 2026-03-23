# Tan

Computes the tangent of an expression. Delegates to `Sin(x) / Cos(x)` for symbolic evaluation.

## Syntax
```
Tan(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The angle expression |

## Returns
- A `RealAtom` if the argument is a numeric integer or real
- An exact value computed from `Sin(x) / Cos(x)` for rational multiples of Pi
- An unevaluated `Tan(x)` otherwise

## Examples

Numeric evaluation:
```
Flame> Tan(0)
0.0
Flame> Tan(1)
1.5574077246549023
```

Exact values at well-known angles:
```
Flame> Tan(Pi)
0
Flame> Tan(2 * Pi)
0
```

Undefined at odd multiples of Pi/2:
```
Flame> Tan(Pi / 2)
Error: Tan undefined: division by zero
```

Symbolic (unevaluated):
```
Flame> Tan(x)
Tan(x)
```

## Notes
- Computed as `Sin(x) / Cos(x)` — does not use its own seed table
- Results involving `Sqrt` may not fully simplify (e.g., `Tan(Pi/4)` may not reduce to `1`)
- Throws `ArithmeticException` when `Cos(x)` is zero

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws `ArithmeticException` at odd multiples of Pi/2

## See Also
- [Sin](Sin.md) — sine
- [Cos](Cos.md) — cosine
- [Num](Num.md) — force numeric evaluation
