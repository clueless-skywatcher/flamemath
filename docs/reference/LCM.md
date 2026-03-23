# LCM

Computes the least common multiple of one or more integers. The result is always non-negative.

## Syntax
```
LCM(a)
LCM(a, b)
LCM(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Integer | First integer |
| `b, c, ...` | Integer (variadic) | Additional integers |

## Returns
- An `IntegerAtom`: the least common multiple of all arguments
- Always non-negative

## Examples

Two arguments:
```
Flame> LCM(4, 6)
12
Flame> LCM(3, 5)
15
Flame> LCM(7, 7)
7
```

Negative arguments (result is always positive):
```
Flame> LCM(-4, 6)
12
Flame> LCM(-3, -5)
15
```

Multiple arguments:
```
Flame> LCM(4, 6, 10)
60
Flame> LCM(2, 3, 5)
30
```

Zero arguments (LCM with zero is always zero):
```
Flame> LCM(0, 5)
0
Flame> LCM(3, 0, 7)
0
```

Single argument:
```
Flame> LCM(5)
5
```

## Notes
- `LCM(a, b)` is computed as `Abs(a) / GCD(a, b) * Abs(b)`, dividing before multiplying to reduce overflow risk
- If any argument is zero, the result is zero
- Variadic: accepts any number of arguments (at least 1)

## Errors
- Throws `FlameArityException` if no arguments are provided
- Throws an exception if any argument is not an integer

## See Also
- [GCD](GCD.md) — greatest common divisor
- [Mod](Mod.md) — modular remainder
