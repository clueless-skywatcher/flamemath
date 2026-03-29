# Prime

Returns the n-th prime number.

## Syntax
```
Prime(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The 1-based index of the desired prime |

## Returns
- The n-th prime number (e.g., `Prime(1)` → `2`, `Prime(4)` → `7`)
- An unevaluated `Prime(n)` for symbolic arguments

## Examples

First few primes:
```
Flame> Prime(1)
2
Flame> Prime(2)
3
Flame> Prime(3)
5
Flame> Prime(4)
7
```

Larger indices:
```
Flame> Prime(10)
29
Flame> Prime(25)
97
Flame> Prime(100)
541
```

Symbolic (unevaluated):
```
Flame> Prime(x)
Prime(x)
```

## Notes
- Uses a sequential search starting from 2, testing each candidate with `IsPrime`
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [IsPrime](IsPrime.md) — primality test
- [PrimesInRange](PrimesInRange.md) — all primes in a range
- [WieferichPrime](WieferichPrime.md) — smallest Wieferich prime up to n
