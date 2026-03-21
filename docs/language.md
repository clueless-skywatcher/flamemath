# FlameLang

## Introduction
FlameLang is a Wolfram-inspired programming language + Computer algebra system written from scratch in Java. This markdown documents the basics of the language. Function references will be stored in a separate directory.

## Hello World!
For an introduction to any programming language, a "Hello World!" program is absolutely necessary
```
Flame> PrintLn("Hello World!")
Hello World!
```
## Variables
By default, every variable is a `Symbol` that just evaluates to itself.
```
Flame> x
x
Flame> Head(x)
Symbol
```
You can assign values to a variable by
- The easy way: `x = 5`
- The more tedious way `Set(x, 5)`. Internally `x = 5` becomes `Set(x, 5)`
```
Flame> Set(x, 5)
5
Flame> x
5
Flame> x = 4
4
Flame> x
4
```
## Programming Constructs
There is no concept of a "keyword" in FlameLang. Every programming construct like if-statements and while loops are used as functions.
### If statements
The `If` function takes either of the following:
- A condition and an if-expression: If the condition evaluates to `True`, then evaluate and return the if-expression, else return `Null`.
- A condition, if-expression and an else-expression: If the condition evaluates to `True`, then evaluate and return the if-expression, else evaluate and return the else-expression
```
Flame> x = -1
-1
Flame> If(x < 0, x = x + 2) // Will evaluate the expression
1
Flame> If(x > 0, x = x + 3) // Will evaluate the expression again
4
Flame> If(x > 3, x = x * 10, x = x - 10) // Will take the second expression
40
Flame> If(x < 10, x = x + 10, x = x - 10) // Will take the else-expression
30
Flame> If(x == 20, x = x + 10) // Returns Null since it does not take the if-expression
```
`If` does not support else-if like constructs like in other languages. Support for this will be added later on in a different function.
### While loops
The `While` function takes only 2 arguments: a condition and an expression. The expression is repeatedly evaluated while the condition evaluates to `True`. Once it becomes `False`, the evaluation stops. Returns `Null`
```
Flame> i = 0
0
Flame> While(i < 5, {PrintLn(i); i = i + 1})
0
1
2
3
4
```
### Seq blocks
You can enclose a group of statements in a `Seq`, by enclosing them in braces like this and putting semicolons between them
```
{a; b; c}
```
This evaluates all the statements, but only returns the last one
```
Flame> {a; b; c}
c
Flame> {1 + 2; 3 + 1; 4 + 5}
9
Flame> {PrintLn("Statement 1"); PrintLn("Statement 2"); 5}
Statement 1
Statement 2
5
```
NOTE: You are not seeing it wrong. The last statement should NOT have a trailing semicolon.
### Return statement
`Return` is also a function! It takes only one argument and prematurely returns that argument after evaluation in a statement. You can only use `Return` within a function definition. More on this in the functions section.

## Types
Everything in FlameLang is an expression. Every expression has a `Head` that tells you what kind of thing it is.
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
Flame> Head(Null)
Null
```
Here are the atomic types:
- `Integer` — whole numbers like `0`, `42`, `-7`
- `Real` — floating point numbers like `3.14`, `0.5`, `1e10`
- `String` — text enclosed in double quotes: `"hello"`
- `Boolean` — `True` or `False`
- `Symbol` — unassigned names like `x`, `y`, `foo`
- `Null` — the absence of a value. `Null` is a singleton; there is only one of it

Non-atomic types include `Compound` expressions (function calls, operators), `Lambda` (function definitions), `List`, and `Dict`.

## Dictionaries
Dictionaries are key-value mappings created with braces and the `:` operator.
```
Flame> d = {"name": "Alice", "age": 30}
Flame> Lookup(d, "name")
"Alice"
Flame> HasKey(d, "age")
True
```
Keys must be hashable (any atomic type: Integer, Real, String, Symbol, Boolean, Null). Lists, Dicts, and Lambdas cannot be used as keys.

Empty braces `{}` create an empty dictionary.
```
Flame> {}
Dict()
```

Dictionaries are immutable. Functions like `DictSet` and `DictRemove` return new dictionaries without modifying the original.
```
Flame> d = {"a": 1}
Flame> d2 = DictSet(d, "b", 2)
Flame> Lookup(d2, "b")
2
Flame> HasKey(d, "b")
False
```

See the reference pages for [Dict](reference/Dict.md), [Lookup](reference/Lookup.md), [Keys](reference/Keys.md), [Values](reference/Values.md), [HasKey](reference/HasKey.md), [DictSet](reference/DictSet.md), [DictRemove](reference/DictRemove.md), and [Merge](reference/Merge.md).

## Operators
FlameLang uses infix operators that desugar into function calls internally. There are no "special" operators, they are all syntactic sugar.

### Arithmetic
| Syntax | Desugars to | Notes |
|--------|------------|-------|
| `a + b` | `Add(a, b)` | |
| `a - b` | `Add(a, Mul(-1, b))` | Subtraction is addition by the negative |
| `a * b` | `Mul(a, b)` | |
| `a / b` | `Mul(a, Pow(b, -1))` | Division is multiplication by the reciprocal |
| `a ^ b` | `Pow(a, b)` | Right-associative: `2^3^4` is `2^(3^4)` |
| `-a` | `Mul(-1, a)` | Unary minus |

### Comparison
| Syntax | Desugars to |
|--------|------------|
| `a == b` | `Eq(a, b)` |
| `a != b` | `NotEq(a, b)` |
| `a < b` | `Less(a, b)` |
| `a <= b` | `LessEq(a, b)` |
| `a > b` | `Greater(a, b)` |
| `a >= b` | `GreaterEq(a, b)` |

These return `True` or `False` when both arguments are numeric. If arguments are symbolic, the expression is returned unevaluated.

### Logical
| Syntax | Desugars to |
|--------|------------|
| `a && b` | `And(a, b)` |
| `a \|\| b` | `Or(a, b)` |
| `!a` | `Not(a)` |

`And` and `Or` are short-circuiting. `And(False, ...)` returns `False` without evaluating the rest. `Or(True, ...)` returns `True` without evaluating the rest. Both are also flattening: `And(And(a, b), c)` becomes `And(a, b, c)`.

When given symbolic arguments, identity elements are dropped: `And(True, x)` returns `x`, `Or(False, x)` returns `x`.

## Functions
There are no keywords for defining functions. You just assign a lambda to a variable.
### Lambdas
```
Flame> F = (x) => x + 1
Flame> F(5)
6
```
The syntax is `(params) => body`. Multiple parameters work as expected.
```
Flame> Add3 = (a, b, c) => a + b + c
Flame> Add3(1, 2, 3)
6
```
Zero-parameter lambdas work too.
```
Flame> AnswerToLife = () => 42
Flame> AnswerToLife()
42
```
### Block bodies
If your function needs multiple statements, wrap the body in braces. Remember: no trailing semicolon.
```
Flame> F = (x) => {
    y = x * 2;
    y + x
}
Flame> F(5)
15
```
### Scoping
Lambdas create their own scope. Parameters do not leak into the outer scope, and local variables defined inside the body do not either.
```
Flame> x = 10
10
Flame> F = (x) => x + 1
Flame> F(2)
3
Flame> x
10
```
Lambdas do see the outer scope though. They are closures.
```
Flame> a = 10
10
Flame> F = (x) => x + a
Flame> F(5)
15
```
### Overloading by arity
You can define multiple clauses for the same function by grouping lambdas in braces.
```
Flame> F = {
    (a, b) => a + b;
    (a) => a * 2
}
Flame> F(2, 3)
5
Flame> F(5)
10
```
The correct clause is selected based on the number of arguments. If no clause matches, you get an arity error.
### Nested closures
Since lambdas are closures, you can return a lambda from a lambda and it remembers its enclosing scope.
```
Flame> Adder = (x) => (y) => x + y
Flame> Add5 = Adder(5)
Flame> Add5(7)
12
Flame> Add3 = Adder(3)
Flame> Add3(10)
13
```
### Return
`Return` breaks out of the nearest enclosing function. It does not just return from a block. It unwinds through `While` loops, `If` branches, and `Seq` blocks until it hits the lambda boundary.
```
Flame> F = (x) => {
    While(True, {
        If(x == 5, Return(x ^ 2));
        x = x + 1
    })
}
Flame> F(0)
25
```
Without `Return`, the `While(True, ...)` would loop forever. `Return` breaks out of the entire function, not just the loop.

## Everything is an expression
There are no statements in FlameLang. Everything returns a value. `If` returns the chosen branch. `While` returns `Null`. `Set` returns the assigned value. Even `Seq` returns its last element.
```
Flame> result = If(True, 42, 0)
42
Flame> result
42
Flame> 1 + If(True, 2, 3)
3
```
This means you can nest things freely. There is no distinction between "expressions" and "statements", it's expressions all the way down.

## Coding Conventions
FlameLang follows Mathematica's naming conventions. These are not enforced by the language, but you should follow them anyway.

- **Function names are PascalCase.** `Add`, `PrintLn`, `AnswerToLife`. Not `add`, `printLn`, `answer_to_life`.
- **Variable names are lowercase.** `x`, `result`, `counter`. This makes it visually obvious what is a function and what is a variable.
- **Constants are PascalCase too.** `True`, `False`, `Null`, `Pi`, `E`. They look like functions because in FlameLang, the line between the two is thin.
- **No trailing semicolons in blocks.** `{ a; b; c }`, not `{ a; b; c; }`. The last expression is what gets returned, and a dangling semicolon will cause a parse error.
- **Braces for multi-statement bodies.** If your lambda body has more than one expression, wrap it in braces. Single expressions do not need them.