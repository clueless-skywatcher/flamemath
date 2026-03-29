package io.flamemath;

import io.flamemath.expr.*;
import io.flamemath.expr.RationalAtom;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExprPrinterTest {

    private Compound c(String head, Expr... children) {
        return new Compound(head, List.of(children));
    }

    private String print(Expr expr) {
        return ExprPrinter.print(expr);
    }

    // --- Atoms ---

    @Test
    void integer() {
        assertEquals("42", print(new IntegerAtom(42)));
    }

    @Test
    void negativeInteger() {
        assertEquals("-3", print(new IntegerAtom(-3)));
    }

    @Test
    void real() {
        assertEquals("3.14", print(new RealAtom(3.14)));
    }

    @Test
    void string() {
        assertEquals("\"hello\"", print(new StringAtom("hello")));
    }

    @Test
    void symbol() {
        assertEquals("x", print(new Symbol("x")));
    }

    @Test
    void booleanTrue() {
        assertEquals("True", print(BooleanAtom.TRUE));
    }

    @Test
    void booleanFalse() {
        assertEquals("False", print(BooleanAtom.FALSE));
    }

    @Test
    void nullExpr() {
        assertEquals("Null", print(NullExpr.INSTANCE));
    }

    // --- Basic infix ---

    @Test
    void addition() {
        assertEquals("x + y", print(c("Add", new Symbol("x"), new Symbol("y"))));
    }

    @Test
    void multiplication() {
        assertEquals("x*y", print(c("Mul", new Symbol("x"), new Symbol("y"))));
    }

    @Test
    void exponentiation() {
        assertEquals("x^2", print(c("Pow", new Symbol("x"), new IntegerAtom(2))));
    }

    // --- Precedence and parentheses ---

    @Test
    void addInsideMulGetsParens() {
        // Mul(Add(x, y), z) → (x + y)*z
        assertEquals("(x + y)*z",
                print(c("Mul", c("Add", new Symbol("x"), new Symbol("y")), new Symbol("z"))));
    }

    @Test
    void mulInsideAddNoParens() {
        // Add(x, Mul(2, y)) → x + 2*y
        assertEquals("x + 2*y",
                print(c("Add", new Symbol("x"), c("Mul", new IntegerAtom(2), new Symbol("y")))));
    }

    @Test
    void powInsideMulNoParens() {
        assertEquals("x^2*y",
                print(c("Mul", c("Pow", new Symbol("x"), new IntegerAtom(2)), new Symbol("y"))));
    }

    // --- Subtraction (negative Mul inside Add) ---

    @Test
    void subtractionFromMinusOne() {
        // Add(x, Mul(-1, y)) → x - y
        assertEquals("x - y",
                print(c("Add", new Symbol("x"), c("Mul", IntegerAtom.MINUS_ONE, new Symbol("y")))));
    }

    @Test
    void subtractionWithCoefficient() {
        // Add(x, Mul(-2, y)) → x - 2*y
        assertEquals("x - 2*y",
                print(c("Add", new Symbol("x"), c("Mul", new IntegerAtom(-2), new Symbol("y")))));
    }

    @Test
    void subtractionWithMultipleFactors() {
        // Add(x, Mul(-1, y, z)) → x - y*z
        assertEquals("x - y*z",
                print(c("Add", new Symbol("x"), c("Mul", IntegerAtom.MINUS_ONE, new Symbol("y"), new Symbol("z")))));
    }

    @Test
    void subtractionWithCoefficientAndMultipleFactors() {
        // Add(x, Mul(-3, y, z)) → x - 3*y*z
        assertEquals("x - 3*y*z",
                print(c("Add", new Symbol("x"), c("Mul", new IntegerAtom(-3), new Symbol("y"), new Symbol("z")))));
    }

    @Test
    void subtractionWithRealCoefficient() {
        // Add(x, Mul(-2.5, y)) → x - 2.5*y
        assertEquals("x - 2.5*y",
                print(c("Add", new Symbol("x"), c("Mul", new RealAtom(-2.5), new Symbol("y")))));
    }

    @Test
    void multipleSubtractions() {
        // Add(x, Mul(-1, y), Mul(-1, z)) → x - y - z
        assertEquals("x - y - z",
                print(c("Add", new Symbol("x"),
                        c("Mul", IntegerAtom.MINUS_ONE, new Symbol("y")),
                        c("Mul", IntegerAtom.MINUS_ONE, new Symbol("z")))));
    }

    @Test
    void mixedAddAndSubtract() {
        // Add(x, y, Mul(-1, z)) → x + y - z
        assertEquals("x + y - z",
                print(c("Add", new Symbol("x"), new Symbol("y"),
                        c("Mul", IntegerAtom.MINUS_ONE, new Symbol("z")))));
    }

    // --- Division (Pow(x, -1) inside Mul) ---

    @Test
    void division() {
        // Mul(x, Pow(y, -1)) → x / y
        assertEquals("x / y",
                print(c("Mul", new Symbol("x"), c("Pow", new Symbol("y"), IntegerAtom.MINUS_ONE))));
    }

    // --- Function calls ---

    @Test
    void functionCall() {
        assertEquals("Sin(x)", print(c("Sin", new Symbol("x"))));
    }

    @Test
    void nestedFunctionCall() {
        assertEquals("Sin(Cos(x))", print(c("Sin", c("Cos", new Symbol("x")))));
    }

    @Test
    void functionWithExpressionArg() {
        assertEquals("Sin(x + 1)", print(c("Sin", c("Add", new Symbol("x"), IntegerAtom.ONE))));
    }

    // --- Prefix operators ---

    @Test
    void logicalNot() {
        assertEquals("!True", print(c("Not", BooleanAtom.TRUE)));
    }

    // --- Comparison and logical ---

    @Test
    void equality() {
        assertEquals("x == 0", print(c("Eq", new Symbol("x"), IntegerAtom.ZERO)));
    }

    @Test
    void logicalAnd() {
        assertEquals("a && b", print(c("And", new Symbol("a"), new Symbol("b"))));
    }

    @Test
    void logicalOr() {
        assertEquals("a || b", print(c("Or", new Symbol("a"), new Symbol("b"))));
    }

    // --- Assignment ---

    @Test
    void assignment() {
        assertEquals("x = 5", print(c("Set", new Symbol("x"), new IntegerAtom(5))));
    }

    // --- ListExpr ---

    @Test
    void emptyListExpr() {
        assertEquals("[]", print(new ListExpr(List.of())));
    }

    @Test
    void singleElementListExpr() {
        assertEquals("[1]", print(new ListExpr(List.of(IntegerAtom.ONE))));
    }

    @Test
    void multipleElementListExpr() {
        assertEquals("[1, 2, 3]",
                print(new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2), new IntegerAtom(3)))));
    }

    @Test
    void listExprWithMixedTypes() {
        assertEquals("[1, 3.14, \"hello\", True, x]",
                print(new ListExpr(List.of(
                        IntegerAtom.ONE, new RealAtom(3.14), new StringAtom("hello"),
                        BooleanAtom.TRUE, new Symbol("x")))));
    }

    @Test
    void listExprWithExpressionElements() {
        // List containing Add(x, y)
        assertEquals("[x + y, 2]",
                print(new ListExpr(List.of(
                        c("Add", new Symbol("x"), new Symbol("y")),
                        new IntegerAtom(2)))));
    }

    @Test
    void nestedListExpr() {
        assertEquals("[[1, 2], [3, 4]]",
                print(new ListExpr(List.of(
                        new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2))),
                        new ListExpr(List.of(new IntegerAtom(3), new IntegerAtom(4)))))));
    }

    @Test
    void listExprInsideFunctionCall() {
        // Sin([1, 2]) — list as argument to a function
        assertEquals("Sin([1, 2])",
                print(c("Sin", new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2))))));
    }

    @Test
    void listExprInsideInfix() {
        // Add([1, 2], [3, 4])
        assertEquals("[1, 2] + [3, 4]",
                print(c("Add",
                        new ListExpr(List.of(IntegerAtom.ONE, new IntegerAtom(2))),
                        new ListExpr(List.of(new IntegerAtom(3), new IntegerAtom(4))))));
    }

    // --- Square root (√) display ---

    @Test
    void sqrtOfInteger() {
        // Pow(2, (1/2)) → √2
        assertEquals("√2",
                print(c("Pow", new IntegerAtom(2),
                        new RationalAtom(IntegerAtom.ONE, new IntegerAtom(2)))));
    }

    @Test
    void sqrtOfSymbol() {
        // Sqrt(x) → √x
        assertEquals("√x", print(c("Sqrt", new Symbol("x"))));
    }

    @Test
    void sqrtOfCompoundExpr() {
        // Sqrt(Pow(x, 2)) → √(x^2)
        assertEquals("√(x^2)",
                print(c("Sqrt", c("Pow", new Symbol("x"), new IntegerAtom(2)))));
    }

    @Test
    void sqrtInProduct() {
        // Mul(2, Pow(3, (1/2))) → 2*√3
        assertEquals("2*√3",
                print(c("Mul", new IntegerAtom(2),
                        c("Pow", new IntegerAtom(3),
                                new RationalAtom(IntegerAtom.ONE, new IntegerAtom(2))))));
    }

    @Test
    void sqrtOfProductExpr() {
        // Sqrt(Mul(2, x)) → √(2*x)
        assertEquals("√(2*x)",
                print(c("Sqrt", c("Mul", new IntegerAtom(2), new Symbol("x")))));
    }

    // --- Complex combinations ---

    @Test
    void fullExpression() {
        // Add(Mul(3, x), Mul(-2, y)) → 3*x - 2*y
        assertEquals("3*x - 2*y",
                print(c("Add", c("Mul", new IntegerAtom(3), new Symbol("x")),
                        c("Mul", new IntegerAtom(-2), new Symbol("y")))));
    }

    @Test
    void nestedPrecedence() {
        // Add(1, Mul(2, Pow(x, 3))) → 1 + 2*x^3
        assertEquals("1 + 2*x^3",
                print(c("Add", IntegerAtom.ONE,
                        c("Mul", new IntegerAtom(2), c("Pow", new Symbol("x"), new IntegerAtom(3))))));
    }

    // --- RationalAtom ---

    @Test
    void rationalSimple() {
        // RationalAtom(1, 2) → (1/2)
        assertEquals("(1/2)",
                print(new RationalAtom(IntegerAtom.ONE, new IntegerAtom(2))));
    }

    @Test
    void rationalThreeOverFour() {
        assertEquals("(3/4)",
                print(new RationalAtom(new IntegerAtom(3), new IntegerAtom(4))));
    }

    @Test
    void rationalNegativeNumerator() {
        assertEquals("(-1/3)",
                print(new RationalAtom(IntegerAtom.MINUS_ONE, new IntegerAtom(3))));
    }

    @Test
    void rationalInAdd() {
        // Add(x, RationalAtom(1, 2)) → x + (1/2)
        assertEquals("x + (1/2)",
                print(c("Add", new Symbol("x"),
                        new RationalAtom(IntegerAtom.ONE, new IntegerAtom(2)))));
    }

    @Test
    void rationalInMul() {
        // Mul(RationalAtom(1, 2), x) → (1/2)*x
        assertEquals("(1/2)*x",
                print(c("Mul",
                        new RationalAtom(IntegerAtom.ONE, new IntegerAtom(2)),
                        new Symbol("x"))));
    }
}
