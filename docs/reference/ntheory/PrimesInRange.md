# PrimesInRange

Returns a list of all prime numbers within a given range.

## Syntax
```
PrimesInRange(from, to)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `from` | Integer | The lower bound of the range (inclusive) |
| `to` | Integer | The upper bound of the range (inclusive) |

## Returns
- A list of all prime numbers `p` where `from ≤ p ≤ to`
- An empty list `[]` if no primes exist in the range

## Examples

Basic usage:
```
Flame> PrimesInRange(1, 10)
[2, 3, 5, 7]
Flame> PrimesInRange(10, 30)
[11, 13, 17, 19, 23, 29]
```

Exact boundaries (inclusive):
```
Flame> PrimesInRange(7, 13)
[7, 11, 13]
Flame> PrimesInRange(7, 7)
[7]
```

No primes in range:
```
Flame> PrimesInRange(24, 28)
[]
```

Edge cases:
```
Flame> PrimesInRange(0, 5)
[2, 3, 5]
Flame> PrimesInRange(-10, 5)
[2, 3, 5]
```

## Notes
- Uses a dynamic prime sieve for numbers within the sieve limit, and falls back to the deterministic Miller-Rabin test for larger values
- Both `from` and `to` are inclusive — if either bound is itself prime, it will appear in the result
- Values below 2 are ignored since there are no primes less than 2

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided
- Throws an exception if either argument is not an integer

## See Also
- [IsPrime](IsPrime.md) — test whether a single number is prime
- [GCD](GCD.md) — greatest common divisor
- [LCM](LCM.md) — least common multiple
