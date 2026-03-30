# Divisors

Returns a sorted list of all positive divisors of `n`.

## Syntax
```
Divisors(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to find divisors of |

## Returns
- A sorted list of all positive divisors of `n`
- An unevaluated `Divisors(n)` for symbolic arguments

## Examples

Prime (only 1 and itself):
```
Flame> Divisors(7)
[1, 7]
```

Composite:
```
Flame> Divisors(12)
[1, 2, 3, 4, 6, 12]
Flame> Divisors(60)
[1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30, 60]
```

Edge case:
```
Flame> Divisors(1)
[1]
```

Symbolic (unevaluated):
```
Flame> Divisors(x)
Divisors(x)
```

## Notes
- Generates divisors from the prime factorization obtained via `PrimeFactors`, then sorts the result
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [PrimeFactors](PrimeFactors.md) — prime factorization
- [DivisorSigma](DivisorSigma.md) — sum of powers of divisors
- [GCD](GCD.md) — greatest common divisor
