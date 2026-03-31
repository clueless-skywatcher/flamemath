# NextPrime

Returns the smallest prime strictly greater than `n`.

## Syntax
```
NextPrime(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to search from |

## Returns
- The smallest prime `p > n`
- An unevaluated `NextPrime(n)` for symbolic arguments

## Examples

```
Flame> NextPrime(10)
11
Flame> NextPrime(11)
13
Flame> NextPrime(100)
101
```

Edge cases:
```
Flame> NextPrime(0)
2
Flame> NextPrime(1)
2
```

Symbolic (unevaluated):
```
Flame> NextPrime(x)
NextPrime(x)
```

## Notes
- Searches sequentially from `n + 1`, testing each candidate with `IsPrime`
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [IsPrime](IsPrime.md) — primality test
- [Prime](Prime.md) — n-th prime number
- [PrimesInRange](PrimesInRange.md) — all primes in a range
