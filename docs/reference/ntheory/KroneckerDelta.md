# KroneckerDelta

Returns $1$ if $i = j$, and $0$ otherwise.

## Syntax
```
KroneckerDelta(i, j)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `i` | Integer | First value |
| `j` | Integer | Second value |

## Returns
- $1$ if $i = j$, $0$ otherwise
- An unevaluated `KroneckerDelta(i, j)` for symbolic arguments

## Examples

```
Flame> KroneckerDelta(3, 3)
1
Flame> KroneckerDelta(3, 5)
0
Flame> KroneckerDelta(0, 0)
1
```

Symbolic (unevaluated):
```
Flame> KroneckerDelta(x, y)
KroneckerDelta(x, y)
```

## Notes
- Defined in `ntheory.flame`

## Errors
- Requires exactly 2 arguments

## See Also
- [MoebiusMu](MoebiusMu.md) — Möbius function (uses KroneckerDelta internally)
