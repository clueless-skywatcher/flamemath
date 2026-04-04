# PrimeFactors

Returns the prime factorization of `n` as a dictionary mapping each prime factor to its exponent.

## Syntax
```
PrimeFactors(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to factorize |

## Returns
- A dictionary `{prime: exponent, ...}` of the prime factorization
- An unevaluated `PrimeFactors(n)` for symbolic arguments

## Examples

```
Flame> PrimeFactors(12)
{2: 2, 3: 1}
Flame> PrimeFactors(360)
{2: 3, 3: 2, 5: 1}
Flame> PrimeFactors(7)
{7: 1}
```

Symbolic (unevaluated):
```
Flame> PrimeFactors(x)
PrimeFactors(x)
```

## Notes
- Uses trial division for small factors and Pollard's rho for large factors, with Miller-Rabin primality testing
- Implemented as a Java builtin (`PrimeFactorsFunc`) for performance
- Supports arbitrary-precision integers

## Errors
- Requires exactly 1 argument

## See Also
- [Divisors](Divisors.md) — all divisors of n
- [IsPrime](IsPrime.md) — primality test
- [GCD](GCD.md) — greatest common divisor
