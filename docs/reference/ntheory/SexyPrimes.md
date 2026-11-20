# SexyPrimes

Returns the first n pairs of sexy primes. Sexy primes are pairs of primes that differ by 6.

## Syntax
```
SexyPrimes(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number of sexy prime pairs to return |

## Returns
A `List` of lists — each inner list is a pair `[p, p + 6]` where both `p` and `p + 6` are prime.

## Examples
```
Flame> SexyPrimes(1)
[[5, 11]]
Flame> SexyPrimes(3)
[[5, 11], [7, 13], [11, 17]]
Flame> SexyPrimes(5)
[[5, 11], [7, 13], [11, 17], [13, 19], [17, 23]]
```

Symbolic (unevaluated):
```
Flame> SexyPrimes(x)
SexyPrimes(x)
```

## Notes
- The first few sexy prime pairs are: (5, 11), (7, 13), (11, 17), (13, 19), (17, 23), (23, 29), ...
- Uses `Prime`, `Intersection`, and `Take` internally to find pairs efficiently.
- Returns unevaluated `SexyPrimes(n)` for non-integer arguments.
- Defined in `ntheory.flame`.

## See Also
- [Prime](Prime.md) — nth prime number
- [IsPrime](IsPrime.md) — primality test
- [PrimesInRange](PrimesInRange.md) — primes in a given range
