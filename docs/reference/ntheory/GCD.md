# GCD

Computes the greatest common divisor of one or more integers. The result is always non-negative.

## Syntax
```
GCD(a)
GCD(a, b)
GCD(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Integer | First integer |
| `b, c, ...` | Integer (variadic) | Additional integers |

## Returns
- An `IntegerAtom`: the greatest common divisor of all arguments
- Always non-negative (absolute value)

## Examples

Two arguments:
```
Flame> GCD(12, 8)
4
Flame> GCD(7, 5)
1
Flame> GCD(0, 5)
5
```

Negative arguments (result is always positive):
```
Flame> GCD(-12, 8)
4
Flame> GCD(-6, -9)
3
```

Multiple arguments:
```
Flame> GCD(12, 8, 6)
2
Flame> GCD(30, 20, 10)
10
```

Single argument:
```
Flame> GCD(5)
5
```

## Notes
- `GCD(0, n)` returns `Abs(n)` for any integer `n`
- Variadic: accepts any number of arguments (at least 1)
- Uses the Euclidean algorithm internally

## Errors
- Throws `FlameArityException` if no arguments are provided
- Throws an exception if any argument is not an integer

## See Also
- [LCM](LCM.md) — least common multiple
- [Mod](../math/Mod.md) — modular remainder
