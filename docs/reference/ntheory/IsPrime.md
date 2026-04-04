# IsPrime

Tests whether an integer is prime using the deterministic Miller-Rabin primality test.

## Syntax
```
IsPrime(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to test for primality |

## Returns
- `True` if `n` is prime
- `False` if `n` is not prime (including values less than 2)

## Examples

Small primes:
```
Flame> IsPrime(2)
True
Flame> IsPrime(7)
True
Flame> IsPrime(37)
True
```

Composites:
```
Flame> IsPrime(4)
False
Flame> IsPrime(15)
False
Flame> IsPrime(25)
False
```

Edge cases:
```
Flame> IsPrime(0)
False
Flame> IsPrime(1)
False
Flame> IsPrime(-7)
False
```

Large primes:
```
Flame> IsPrime(999999937)
True
Flame> IsPrime(104729)
True
```

Carmichael numbers (composites that fool simpler tests):
```
Flame> IsPrime(561)
False
Flame> IsPrime(1729)
False
```

## Notes
- Uses the deterministic Miller-Rabin test with 12 fixed bases: `{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}`
- This is proven correct for all values up to $3.317 \times 10^{24}$, which covers the full `long` range
- Negative numbers and values less than 2 always return `False`
- Correctly rejects Carmichael numbers (e.g., 561, 1105, 1729), which would fool a Fermat-only test

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided
- Throws an exception if the argument is not an integer

## See Also
- [GCD](GCD.md) — greatest common divisor
- [LCM](LCM.md) — least common multiple
- [Mod](../math/Mod.md) — modular remainder
