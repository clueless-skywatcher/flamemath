# Expand

Expands an algebraic expression by distributing products over sums and applying the multinomial theorem to powers of sums.

## Syntax
```
Expand(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to expand |

## Returns
The fully expanded form of the expression as a sum of monomials. Returns the expression unchanged when expansion is not applicable (symbolic exponents, negative exponents, non-algebraic functions).

## Examples

### Distribution
```
Flame> Expand(x * (y + z))
x*y + x*z
Flame> Expand((a + b) * (c + d))
a*c + a*d + b*c + b*d
Flame> Expand(2 * (x + 1))
2 + 2*x
```

### Binomial expansion
```
Flame> Expand((x + 1)^2)
x^2 + 2*x + 1
Flame> Expand((x + 1)^3)
x^3 + 3*x^2 + 3*x + 1
Flame> Expand((a + b)^4)
a^4 + 4*a^3*b + 6*a^2*b^2 + 4*a*b^3 + b^4
Flame> Expand((x - 1)^3)
x^3 - 3*x^2 + 3*x - 1
Flame> Expand((2*x + 3)^2)
4*x^2 + 12*x + 9
```

### Multinomial expansion
```
Flame> Expand((x + y + z)^2)
x^2 + 2*x*y + 2*x*z + y^2 + 2*y*z + z^2
Flame> Expand((a + b + c + d)^2)
a^2 + 2*a*b + 2*a*c + 2*a*d + b^2 + 2*b*c + 2*b*d + c^2 + 2*c*d + d^2
```

### Mixed (distribution + multinomial)
```
Flame> Expand((a + b)^2 * (c + d))
a^2*c + a^2*d + 2*a*b*c + 2*a*b*d + b^2*c + b^2*d
Flame> Expand((x + 1)^2 * (x + 2))
x^3 + 4*x^2 + 5*x + 2
```

### Passthrough cases
```
Flame> Expand(5)
5
Flame> Expand(x)
x
Flame> Expand(x + 1)
x + 1
Flame> Expand(x^2 + 2*x + 1)
x^2 + 2*x + 1
```

### Unevaluated cases
Symbolic or negative exponents cannot be expanded and are returned as-is:
```
Flame> Expand((x + 1)^n)
(x + 1)^n
Flame> Expand((x + 1)^(-2))
(x + 1)^(-2)
```

Non-algebraic functions are not expanded into:
```
Flame> Expand(Sin(x + y))
Sin(x + y)
```

## Algorithm

Expand walks the expression tree bottom-up and applies rules based on node type:

1. **Atomic** expressions pass through unchanged.
2. **Add** nodes: expand each child independently, recombine as a sum.
3. **Pow(base, n)** where base is a sum and n is a positive integer: apply the multinomial theorem, enumerating all compositions of n and computing multinomial coefficients.
4. **Mul** nodes: expand each factor, then distribute via the Cartesian product of additive terms.
5. **Other compounds** (Sin, Cos, etc.): return as-is.

After expansion, results are passed through the evaluator for automatic like-term combining and canonical ordering.

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Coeff](Coeff.md) — extract the coefficient of a term
- [Degree](Degree.md) — degree of a polynomial in a given variable
- [Vars](Vars.md) — extract free variables from an expression
- [FreeOf](FreeOf.md) — test whether an expression is free of a variable
