# OrderMod

Computes the multiplicative order of $a$ modulo $n$ — the smallest positive integer $k$ such that $a^k \equiv 1 \pmod{n}$.

## Syntax
```
OrderMod(a, n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Integer | The base (must be coprime to `n`) |
| `n` | Integer | The modulus (must be greater than 1) |

## Returns
- A positive integer $k$ such that $a^k \equiv 1 \pmod{n}$ and no smaller positive integer has this property
- Returns unevaluated if $\gcd(a, n) \neq 1$, $n \leq 1$, or arguments are non-integer

## Examples

Basic usage:
```
Flame> OrderMod(2, 7)
3
Flame> OrderMod(3, 7)
6
Flame> OrderMod(2, 5)
4
```

Order 1 (when $a \equiv 1 \pmod{n}$):
```
Flame> OrderMod(1, 5)
1
Flame> OrderMod(8, 7)
1
```

Negative or large input (reduced mod $n$):
```
Flame> OrderMod(-1, 7)
2
Flame> OrderMod(10, 7)
6
```

Not coprime (unevaluated):
```
Flame> OrderMod(2, 4)
OrderMod(2, 4)
Flame> OrderMod(6, 9)
OrderMod(6, 9)
```

## Notes
- The multiplicative order divides $\varphi(n)$ (Euler's totient) by Lagrange's theorem
- When the order equals $\varphi(n)$, $a$ is a primitive root modulo $n$
- The input $a$ is reduced modulo $n$ before computation
- Defined in `ntheory.flame`

## See Also
- [EulerPhi](EulerPhi.md) — Euler's totient function
- [PowerMod](PowerMod.md) — modular exponentiation
- [ModInverse](ModInverse.md) — modular multiplicative inverse
- [Coprime](Coprime.md) — pairwise coprimality test
