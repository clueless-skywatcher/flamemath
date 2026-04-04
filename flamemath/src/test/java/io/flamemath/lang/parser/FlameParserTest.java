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
        assertEquals(BooleanAtom.TRUE, parse("True"));
    }

    @Test
    void booleanFalse() throws Exception {
        assertEquals(BooleanAtom.FALSE, parse("False"));
    }

    // --- Arithmetic ---

    @Test
    void addition() throws Exception {
        // 1 + 2 → Add(1, 2)
        assertEquals(
                c("Add", IntegerAtom.ONE, new IntegerAtom(2)),
                parse("1 + 2"));
    }

    @Test
    void subtraction() throws Exception {
        // 3 - 1 → Add(3, Mul(-1, 1))
        assertEquals(
                c("Add", new IntegerAtom(3), c("Mul", IntegerAtom.MINUS_ONE, IntegerAtom.ONE)),
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
                c("Mul", new IntegerAtom(6), c("Pow", new IntegerAtom(3), IntegerAtom.MINUS_ONE)),
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
                c("Add", IntegerAtom.ONE, c("Mul", new IntegerAtom(2), new IntegerAtom(3))),
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
                c("Add", IntegerAtom.ONE, c("Mul", new IntegerAtom(2), c("Pow", new IntegerAtom(3), new IntegerAtom(4)))),
                parse("1 + 2 * 3 ^ 4"));
    }

    // --- Associativity ---

    @Test
    void addIsLeftAssociative() throws Exception {
        // 1 + 2 + 3 → Add(Add(1, 2), 3)
        assertEquals(
                c("Add", c("Add", IntegerAtom.ONE, new IntegerAtom(2)), new IntegerAtom(3)),
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
                c("Set", new Symbol("a"), c("Set", new Symbol("b"), IntegerAtom.ONE)),
                parse("a = b = 1"));
    }

    // --- Unary operators ---

    @Test
    void unaryMinus() throws Exception {
        // -x → Mul(-1, x)
        assertEquals(
                c("Mul", IntegerAtom.MINUS_ONE, new Symbol("x")),
                parse("-x"));
    }

    @Test
    void unaryMinusPrecedence() throws Exception {
        // -x ^ 2 → Mul(-1, Pow(x, 2))  because ^ binds tighter
        assertEquals(
                c("Mul", IntegerAtom.MINUS_ONE, c("Pow", new Symbol("x"), new IntegerAtom(2))),
                parse("-x ^ 2"));
    }

    @Test
    void logicalNot() throws Exception {
        assertEquals(
                c("Not", BooleanAtom.TRUE),
                parse("!True"));
    }

    // --- Grouping ---

    @Test
    void parenthesesOverridePrecedence() throws Exception {
        // (1 + 2) * 3 → Mul(Add(1, 2), 3)
        assertEquals(
                c("Mul", c("Add", IntegerAtom.ONE, new IntegerAtom(2)), new IntegerAtom(3)),
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
                c("Eq", new Symbol("x"), IntegerAtom.ZERO),
                parse("x == 0"));
    }

    @Test
    void inequality() throws Exception {
        assertEquals(
                c("NotEq", new Symbol("x"), IntegerAtom.ZERO),
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
                c("GreaterEq", new Symbol("x"), IntegerAtom.ZERO),
                parse("x >= 0"));
    }

    @Test
    void logicalAnd() throws Exception {
        assertEquals(
                c("And", BooleanAtom.TRUE, BooleanAtom.FALSE),
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
        assertEquals(new ListExpr(List.of()), parse("[]"));
    }

    @Test
    void singletonList() throws Exception {
        assertEquals(
                new ListExpr(List.of(IntegerAtom.ONE)),
                parse("[1]"));
    }

    @Test
    void multiElementList() throws Exception {
        assertEquals(
                new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2), new IntegerAtom(3))),
                parse("[1, 2, 3]"));
    }

    @Test
    void listWithExpressions() throws Exception {
        // [x + 1, y * 2]
        assertEquals(
                new ListExpr(List.of(
                        c("Add", new Symbol("x"), IntegerAtom.ONE),
                        c("Mul", new Symbol("y"), new IntegerAtom(2)))),
                parse("[x + 1, y * 2]"));
    }

    @Test
    void nestedList() throws Exception {
        assertEquals(
                new ListExpr(List.of(
                        new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2))),
                        new ListExpr(List.of(new IntegerAtom(3), new IntegerAtom(4))))),
                parse("[[1, 2], [3, 4]]"));
    }

    // --- Indexing ---

    @Test
    void indexSymbol() throws Exception {
        // x[0] → At(x, 0)
        assertEquals(
                c("At", new Symbol("x"), IntegerAtom.ZERO),
                parse("x[0]"));
    }

    @Test
    void indexListLiteral() throws Exception {
        // [1, 2, 3][0] → At(ListExpr(1, 2, 3), 0)
        assertEquals(
                c("At", new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2), new IntegerAtom(3))), IntegerAtom.ZERO),
                parse("[1, 2, 3][0]"));
    }

    @Test
    void indexWithExpression() throws Exception {
        // x[i + 1] → At(x, Add(i, 1))
        assertEquals(
                c("At", new Symbol("x"), c("Add", new Symbol("i"), IntegerAtom.ONE)),
                parse("x[i + 1]"));
    }

    @Test
    void chainedIndexing() throws Exception {
        // x[0][1] → At(At(x, 0), 1)
        assertEquals(
                c("At", c("At", new Symbol("x"), IntegerAtom.ZERO), IntegerAtom.ONE),
                parse("x[0][1]"));
    }

    // --- Compound expression (semicolon) ---

    @Test
    void semicolonCompound() throws Exception {
        // a; b → Seq(a, b)
        assertEquals(
                c("Seq", new Symbol("a"), new Symbol("b")),
                parse("a; b"));
    }

    @Test
    void semicolonChain() throws Exception {
        // a; b; c → Seq(Seq(a, b), c)
        assertEquals(
                c("Seq", c("Seq", new Symbol("a"), new Symbol("b")), new Symbol("c")),
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
                c("Set", new Symbol("x"), c("Add", IntegerAtom.ONE, new IntegerAtom(2))),
                parse("x = 1 + 2"));
    }

    // --- Complex combinations ---

    @Test
    void fullExpression() throws Exception {
        // Sin(x + 2) * 3 + 1 → Add(Mul(Sin(Add(x, 2)), 3), 1)
        assertEquals(
                c("Add", c("Mul", c("Sin", c("Add", new Symbol("x"), new IntegerAtom(2))), new IntegerAtom(3)), IntegerAtom.ONE),
                parse("Sin(x + 2) * 3 + 1"));
    }

    @Test
    void negativeInExpression() throws Exception {
        // 1 + -2 → Add(1, Mul(-1, 2))
        assertEquals(
                c("Add", IntegerAtom.ONE, c("Mul", IntegerAtom.MINUS_ONE, new IntegerAtom(2))),
                parse("1 + -2"));
    }

    @Test
    void indexedAssignment() throws Exception {
        // a[0] = 5 → SetAt(a, 0, 5)
        assertEquals(
                c("SetAt", new Symbol("a"), IntegerAtom.ZERO, new IntegerAtom(5)),
                parse("a[0] = 5"));
    }

    @Test
    void indexedAssignmentWithExpression() throws Exception {
        // a[i + 1] = x * 2 → SetAt(a, Add(i, 1), Mul(x, 2))
        assertEquals(
                c("SetAt", new Symbol("a"), c("Add", new Symbol("i"), IntegerAtom.ONE),
                        c("Mul", new Symbol("x"), new IntegerAtom(2))),
                parse("a[i + 1] = x * 2"));
    }

    @Test
    void indexedAssignmentNegativeIndex() throws Exception {
        // a[-1] = 10 → SetAt(a, Mul(-1, 1), 10)
        assertEquals(
                c("SetAt", new Symbol("a"), c("Mul", IntegerAtom.MINUS_ONE, IntegerAtom.ONE), new IntegerAtom(10)),
                parse("a[-1] = 10"));
    }

    @Test
    void comparisonInAssignment() throws Exception {
        // result = x > 0 && x < 10 → Set(result, And(Greater(x, 0), Less(x, 10)))
        assertEquals(
                c("Set", new Symbol("result"),
                        c("And",
                                c("Greater", new Symbol("x"), IntegerAtom.ZERO),
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

    // --- Comments ---

    @Test
    void commentAfterExpression() throws Exception {
        assertEquals(
                c("Add", IntegerAtom.ONE, new IntegerAtom(2)),
                parse("1 + 2 // this is ignored"));
    }

    @Test
    void commentOnlyIsEmpty() {
        assertThrows(Exception.class, () -> parse("// just a comment"));
    }

    @Test
    void commentPreservesCodeBefore() throws Exception {
        assertEquals(new Symbol("x"), parse("x // the rest is gone"));
    }

    @Test
    void commentInMiddleOfSeq() throws Exception {
        // a; // comment\n b → Seq(a, b)
        assertEquals(
                c("Seq", new Symbol("a"), new Symbol("b")),
                parse("a; // comment\nb"));
    }

    @Test
    void commentAfterAssignment() throws Exception {
        assertEquals(
                c("Set", new Symbol("x"), new IntegerAtom(5)),
                parse("x = 5 // assign x"));
    }

    @Test
    void commentAfterFunctionCall() throws Exception {
        assertEquals(
                c("Sin", new Symbol("x")),
                parse("Sin(x) // compute sine"));
    }

    @Test
    void multipleCommentedLines() throws Exception {
        assertEquals(
                c("Seq", c("Set", new Symbol("x"), IntegerAtom.ONE), new Symbol("x")),
                parse("x = 1; // set x\nx // return x"));
    }

    // --- Comments that break expressions ---

    @Test
    void commentBreaksInfixOperator() {
        // 1 + // 2  →  the "2" is commented out, so "1 +" is a dangling operator
        assertThrows(Exception.class, () -> parse("1 + // 2"));
    }

    @Test
    void commentBreaksFunctionArgs() {
        // Add(1, // 2)  →  the "2)" is commented out, so parens are unmatched
        assertThrows(Exception.class, () -> parse("Add(1, // 2)"));
    }

    @Test
    void commentBreaksListLiteral() {
        // [1, // 2, 3]  →  the rest of the line is gone, bracket unmatched
        assertThrows(Exception.class, () -> parse("[1, // 2, 3]"));
    }

    @Test
    void commentBreaksAssignmentRhs() {
        // x = // 5  →  nothing after =
        assertThrows(Exception.class, () -> parse("x = // 5"));
    }
}
