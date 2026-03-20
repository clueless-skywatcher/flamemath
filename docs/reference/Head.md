# Head

Returns the type (head) of an expression as a symbol.

## Syntax
```
Head(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to inspect |

## Returns
A `Symbol` representing the type of the expression.

## Examples
```
Flame> Head(42)
Integer
Flame> Head(3.14)
Real
Flame> Head("hello")
String
Flame> Head(True)
Boolean
Flame> Head(x)
Symbol
Flame> Head([1, 2, 3])
List
Flame> Head(Null)
Null
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [List](List.md) — list construction
