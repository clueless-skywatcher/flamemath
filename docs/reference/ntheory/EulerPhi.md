# EulerPhi

Returns Euler's totient function $\varphi(n)$ — the count of integers from 1 to $n$ that are coprime to $n$.

## Syntax
```
EulerPhi(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to compute the totient of |

## Returns
- The number of integers in $[1, n]$ coprime to $n$
- An unevaluated `EulerPhi(n)` for symbolic arguments

## Examples

Primes ($\varphi(p) = p - 1$):
```
Flame> EulerPhi(7)
6
Flame> EulerPhi(13)
12
```

Prime powers ($\varphi(p^k) = p^k - p^{k-1}$):
```
Flame> EulerPhi(8)
4
Flame> EulerPhi(9)
6
```

Composites:
```
Flame> EulerPhi(12)
4
Flame> EulerPhi(360)
96
```

Edge case:
```
Flame> EulerPhi(1)
1
```

Symbolic (unevaluated):
```
Flame> EulerPhi(x)
EulerPhi(x)
```

## Notes
- Computed via the product formula: $\varphi(n) = n \prod_{p \mid n} \left(1 - \frac{1}{p}\right)$ over distinct prime factors $p$ of $n$
- Uses `PrimeFactors` to obtain the factorization
- Defined in `ntheory.flame`

## Errors
- Requires exactly 1 argument

## See Also
- [PrimeFactors](PrimeFactors.md) — prime factorization
- [Divisors](Divisors.md) — all divisors of n
- [GCD](GCD.md) — greatest common divisor
