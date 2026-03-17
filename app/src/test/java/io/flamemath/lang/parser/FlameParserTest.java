package io.flamemath.lang.parser;

import io.flamemath.expr.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlameParserTest {

    private Expr parse(String input) throws Exception {
        return new FlameParser(input).parse();
    }

    // Helper to build Compound nodes concisely
    private Compound c(String head, Expr... children) {
        return new Compound(head, List.of(children));
    }

    // --- Atoms ---

    @Test
    void integer() throws Exception {
        assertEquals(new IntegerAtom(42), parse("42"));
    }

    @Test
    void real() throws Exception {
        assertEquals(new RealAtom(3.14), parse("3.14"));
    }

    @Test
    void string() throws Exception {
        assertEquals(new StringAtom("hello"), parse("\"hello\""));
    }

    @Test
    void symbol() throws Exception {
        assertEquals(new Symbol("x"), parse("x"));
    }

    @Test
    void booleanTrue() throws Exception {
        assertEquals(new BooleanAtom(true), parse("True"));
    }

    @Test
    void booleanFalse() throws Exception {
        assertEquals(new BooleanAtom(false), parse("False"));
    }

    // --- Arithmetic ---

    @Test
    void addition() throws Exception {
        // 1 + 2 → Add(1, 2)
        assertEquals(
                c("Add", new IntegerAtom(1), new IntegerAtom(2)),
                parse("1 + 2"));
    }

    @Test
    void subtraction() throws Exception {
        // 3 - 1 → Add(3, Mul(-1, 1))
        assertEquals(
                c("Add", new IntegerAtom(3), c("Mul", new IntegerAtom(-1), new IntegerAtom(1))),
                parse("3 - 1"));
    }

    @Test
    void multiplication() throws Exception {
        assertEquals(
                c("Mul", new IntegerAtom(2), new IntegerAtom(3)),
                parse("2 * 3"));
    }

    @Test
    void division() throws Exception {
        // 6 / 3 → Mul(6, Pow(3, -1))
        assertEquals(
                c("Mul", new IntegerAtom(6), c("Pow", new IntegerAtom(3), new IntegerAtom(-1))),
                parse("6 / 3"));
    }

    @Test
    void exponentiation() throws Exception {
        assertEquals(
                c("Pow", new Symbol("x"), new IntegerAtom(2)),
                parse("x ^ 2"));
    }

    // --- Precedence ---

    @Test
    void mulBeforeAdd() throws Exception {
        // 1 + 2 * 3 → Add(1, Mul(2, 3))
        assertEquals(
                c("Add", new IntegerAtom(1), c("Mul", new IntegerAtom(2), new IntegerAtom(3))),
                parse("1 + 2 * 3"));
    }

    @Test
    void powBeforeMul() throws Exception {
        // 2 * 3 ^ 4 → Mul(2, Pow(3, 4))
        assertEquals(
                c("Mul", new IntegerAtom(2), c("Pow", new IntegerAtom(3), new IntegerAtom(4))),
                parse("2 * 3 ^ 4"));
    }

    @Test
    void fullPrecedenceChain() throws Exception {
        // 1 + 2 * 3 ^ 4 → Add(1, Mul(2, Pow(3, 4)))
        assertEquals(
                c("Add", new IntegerAtom(1), c("Mul", new IntegerAtom(2), c("Pow", new IntegerAtom(3), new IntegerAtom(4)))),
                parse("1 + 2 * 3 ^ 4"));
    }

    // --- Associativity ---

    @Test
    void addIsLeftAssociative() throws Exception {
        // 1 + 2 + 3 → Add(Add(1, 2), 3)
        assertEquals(
                c("Add", c("Add", new IntegerAtom(1), new IntegerAtom(2)), new IntegerAtom(3)),
                parse("1 + 2 + 3"));
    }

    @Test
    void powIsRightAssociative() throws Exception {
        // 2 ^ 3 ^ 4 → Pow(2, Pow(3, 4))
        assertEquals(
                c("Pow", new IntegerAtom(2), c("Pow", new IntegerAtom(3), new IntegerAtom(4))),
                parse("2 ^ 3 ^ 4"));
    }

    @Test
    void assignmentIsRightAssociative() throws Exception {
        // a = b = 1 → Set(a, Set(b, 1))
        assertEquals(
                c("Set", new Symbol("a"), c("Set", new Symbol("b"), new IntegerAtom(1))),
                parse("a = b = 1"));
    }

    // --- Unary operators ---

    @Test
    void unaryMinus() throws Exception {
        // -x → Mul(-1, x)
        assertEquals(
                c("Mul", new IntegerAtom(-1), new Symbol("x")),
                parse("-x"));
    }

    @Test
    void unaryMinusPrecedence() throws Exception {
        // -x ^ 2 → Mul(-1, Pow(x, 2))  because ^ binds tighter
        assertEquals(
                c("Mul", new IntegerAtom(-1), c("Pow", new Symbol("x"), new IntegerAtom(2))),
                parse("-x ^ 2"));
    }

    @Test
    void logicalNot() throws Exception {
        assertEquals(
                c("Not", new BooleanAtom(true)),
                parse("!True"));
    }

    // --- Grouping ---

    @Test
    void parenthesesOverridePrecedence() throws Exception {
        // (1 + 2) * 3 → Mul(Add(1, 2), 3)
        assertEquals(
                c("Mul", c("Add", new IntegerAtom(1), new IntegerAtom(2)), new IntegerAtom(3)),
                parse("(1 + 2) * 3"));
    }

    @Test
    void nestedParentheses() throws Exception {
        // ((x)) → x
        assertEquals(new Symbol("x"), parse("((x))"));
    }

    // --- Comparison & logical ---

    @Test
    void equality() throws Exception {
        assertEquals(
                c("Eq", new Symbol("x"), new IntegerAtom(0)),
                parse("x == 0"));
    }

    @Test
    void inequality() throws Exception {
        assertEquals(
                c("NotEq", new Symbol("x"), new IntegerAtom(0)),
                parse("x != 0"));
    }

    @Test
    void lessThan() throws Exception {
        assertEquals(
                c("Less", new Symbol("x"), new IntegerAtom(5)),
                parse("x < 5"));
    }

    @Test
    void greaterEqual() throws Exception {
        assertEquals(
                c("GreaterEq", new Symbol("x"), new IntegerAtom(0)),
                parse("x >= 0"));
    }

    @Test
    void logicalAnd() throws Exception {
        assertEquals(
                c("And", new BooleanAtom(true), new BooleanAtom(false)),
                parse("True && False"));
    }

    @Test
    void logicalOr() throws Exception {
        assertEquals(
                c("Or", new Symbol("a"), new Symbol("b")),
                parse("a || b"));
    }

    @Test
    void logicalPrecedence() throws Exception {
        // a || b && c → Or(a, And(b, c))  because && binds tighter than ||
        assertEquals(
                c("Or", new Symbol("a"), c("And", new Symbol("b"), new Symbol("c"))),
                parse("a || b && c"));
    }

    // --- Function calls ---

    @Test
    void noArgCall() throws Exception {
        assertEquals(
                c("F"),
                parse("F()"));
    }

    @Test
    void singleArgCall() throws Exception {
        assertEquals(
                c("Sin", new Symbol("x")),
                parse("Sin(x)"));
    }

    @Test
    void multiArgCall() throws Exception {
        assertEquals(
                c("Add", new Symbol("x"), new Symbol("y")),
                parse("Add(x, y)"));
    }

    @Test
    void nestedCalls() throws Exception {
        // Sin(Cos(x))
        assertEquals(
                c("Sin", c("Cos", new Symbol("x"))),
                parse("Sin(Cos(x))"));
    }

    @Test
    void callWithExpression() throws Exception {
        // Sin(x + 2) → Sin(Add(x, 2))
        assertEquals(
                c("Sin", c("Add", new Symbol("x"), new IntegerAtom(2))),
                parse("Sin(x + 2)"));
    }

    @Test
    void callTimesLiteral() throws Exception {
        // Sin(x) * 3 → Mul(Sin(x), 3)
        assertEquals(
                c("Mul", c("Sin", new Symbol("x")), new IntegerAtom(3)),
                parse("Sin(x) * 3"));
    }

    // --- Lists ---

    @Test
    void emptyList() throws Exception {
        assertEquals(c("List"), parse("[]"));
    }

    @Test
    void singletonList() throws Exception {
        assertEquals(
                c("List", new IntegerAtom(1)),
                parse("[1]"));
    }

    @Test
    void multiElementList() throws Exception {
        assertEquals(
                c("List", new IntegerAtom(1), new IntegerAtom(2), new IntegerAtom(3)),
                parse("[1, 2, 3]"));
    }

    @Test
    void listWithExpressions() throws Exception {
        // [x + 1, y * 2]
        assertEquals(
                c("List",
                        c("Add", new Symbol("x"), new IntegerAtom(1)),
                        c("Mul", new Symbol("y"), new IntegerAtom(2))),
                parse("[x + 1, y * 2]"));
    }

    @Test
    void nestedList() throws Exception {
        assertEquals(
                c("List", c("List", new IntegerAtom(1), new IntegerAtom(2)), c("List", new IntegerAtom(3), new IntegerAtom(4))),
                parse("[[1, 2], [3, 4]]"));
    }

    // --- Indexing ---

    @Test
    void indexSymbol() throws Exception {
        // x[0] → At(x, 0)
        assertEquals(
                c("At", new Symbol("x"), new IntegerAtom(0)),
                parse("x[0]"));
    }

    @Test
    void indexListLiteral() throws Exception {
        // [1, 2, 3][0] → At(List(1, 2, 3), 0)
        assertEquals(
                c("At", c("List", new IntegerAtom(1), new IntegerAtom(2), new IntegerAtom(3)), new IntegerAtom(0)),
                parse("[1, 2, 3][0]"));
    }

    @Test
    void indexWithExpression() throws Exception {
        // x[i + 1] → At(x, Add(i, 1))
        assertEquals(
                c("At", new Symbol("x"), c("Add", new Symbol("i"), new IntegerAtom(1))),
                parse("x[i + 1]"));
    }

    @Test
    void chainedIndexing() throws Exception {
        // x[0][1] → At(At(x, 0), 1)
        assertEquals(
                c("At", c("At", new Symbol("x"), new IntegerAtom(0)), new IntegerAtom(1)),
                parse("x[0][1]"));
    }

    // --- Compound expression (semicolon) ---

    @Test
    void semicolonCompound() throws Exception {
        // a; b → Multi(a, b)
        assertEquals(
                c("Multi", new Symbol("a"), new Symbol("b")),
                parse("a; b"));
    }

    @Test
    void semicolonChain() throws Exception {
        // a; b; c → Multi(Multi(a, b), c)
        assertEquals(
                c("Multi", c("Multi", new Symbol("a"), new Symbol("b")), new Symbol("c")),
                parse("a; b; c"));
    }

    // --- Assignment ---

    @Test
    void simpleAssignment() throws Exception {
        // x = 5 → Set(x, 5)
        assertEquals(
                c("Set", new Symbol("x"), new IntegerAtom(5)),
                parse("x = 5"));
    }

    @Test
    void assignmentWithExpression() throws Exception {
        // x = 1 + 2 → Set(x, Add(1, 2))
        assertEquals(
                c("Set", new Symbol("x"), c("Add", new IntegerAtom(1), new IntegerAtom(2))),
                parse("x = 1 + 2"));
    }

    // --- Complex combinations ---

    @Test
    void fullExpression() throws Exception {
        // Sin(x + 2) * 3 + 1 → Add(Mul(Sin(Add(x, 2)), 3), 1)
        assertEquals(
                c("Add", c("Mul", c("Sin", c("Add", new Symbol("x"), new IntegerAtom(2))), new IntegerAtom(3)), new IntegerAtom(1)),
                parse("Sin(x + 2) * 3 + 1"));
    }

    @Test
    void negativeInExpression() throws Exception {
        // 1 + -2 → Add(1, Mul(-1, 2))
        assertEquals(
                c("Add", new IntegerAtom(1), c("Mul", new IntegerAtom(-1), new IntegerAtom(2))),
                parse("1 + -2"));
    }

    @Test
    void comparisonInAssignment() throws Exception {
        // result = x > 0 && x < 10 → Set(result, And(Greater(x, 0), Less(x, 10)))
        assertEquals(
                c("Set", new Symbol("result"),
                        c("And",
                                c("Greater", new Symbol("x"), new IntegerAtom(0)),
                                c("Less", new Symbol("x"), new IntegerAtom(10)))),
                parse("result = x > 0 && x < 10"));
    }

    // --- Error cases ---

    @Test
    void unmatchedParenThrows() {
        assertThrows(Exception.class, () -> parse("(1 + 2"));
    }

    @Test
    void unmatchedBracketThrows() {
        assertThrows(Exception.class, () -> parse("[1, 2"));
    }

    @Test
    void emptyInputThrows() {
        assertThrows(Exception.class, () -> parse(""));
    }

    @Test
    void danglingOperatorThrows() {
        assertThrows(Exception.class, () -> parse("1 +"));
    }
}
