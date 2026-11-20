# IsPositiveInteger

Tests whether a value is an integer greater than or equal to zero.

## Syntax
```
IsPositiveInteger(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The value to test |

## Returns
- `True` if `x` is an `Integer` and `x >= 0`
- `False` otherwise

## Examples

Positive integers:
```
Flame> IsPositiveInteger(1)
True
Flame> IsPositiveInteger(42)
True
Flame> IsPositiveInteger(1000000)
True
```

Zero is accepted:
```
Flame> IsPositiveInteger(0)
True
```

Negative integers are rejected:
```
Flame> IsPositiveInteger(-1)
False
Flame> IsPositiveInteger(-1000000)
False
```

Non-integer numerics are rejected:
```
Flame> IsPositiveInteger(3.14)
False
Flame> IsPositiveInteger(-2.5)
False
```

Other types are rejected:
```
Flame> IsPositiveInteger("5")
False
Flame> IsPositiveInteger([1])
False
Flame> IsPositiveInteger(x)
False
```

## Notes
- Defined in `numeric.flame`.
- **Naming caveat:** despite the name, `0` is accepted — the implementation is `IsInteger(x) && x >= 0`, which is conventionally "non-negative integer." If you need strictly positive (`n >= 1`), compose it yourself:
  ```
  IsStrictlyPositiveInteger = (x) => IsInteger(x) && x > 0
  ```
- Paired with [`IsNegativeInteger`](IsNegativeInteger.md), which uses the strict check `x < 0`. Together they do not cover every integer: `0` is accepted by the positive predicate and rejected by the negative one.

## See Also
- [IsInteger](../../../flamemath/src/main/resources/stdlib/types.flame) — integer-head predicate
- [IsNegativeInteger](IsNegativeInteger.md) — integers strictly less than zero
- [IsNumeric](../../../flamemath/src/main/resources/stdlib/types.flame) — integers and reals
