package io.flamemath.eval.builtins.poly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class VarsFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Vars", new VarsFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Vars()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Vars(x, y)"));
    }

    // --- Single symbol ---

    @Test
    void singleSymbol() throws Exception {
        fm.assertExec("[x]", "Vars(x)");
    }

    // --- Addition ---

    @Test
    void additionTwoVars() throws Exception {
        fm.assertExec("[x, y]", "Vars(x + y)");
    }

    @Test
    void additionDuplicateVars() throws Exception {
        fm.assertExec("[x]", "Vars(x + x)");
    }

    // --- Multiplication ---

    @Test
    void multiplicationTwoVars() throws Exception {
        fm.assertExec("[x, y]", "Vars(x * y)");
    }

    // --- Power ---

    @Test
    void powerWithSymbol() throws Exception {
        fm.assertExec("[x]", "Vars(x^2)");
    }

    // --- Mixed expression ---

    @Test
    void polynomialMultipleVars() throws Exception {
        fm.assertExec("[x, y]", "Vars(x^2 + 3*x*y + y^2)");
    }

    // --- Subtraction / Negation ---

    @Test
    void subtraction() throws Exception {
        fm.assertExec("[x, y]", "Vars(x - y)");
    }

    @Test
    void negation() throws Exception {
        fm.assertExec("[x]", "Vars(-x)");
    }

    // --- Division ---

    @Test
    void division() throws Exception {
        fm.assertExec("[x, y]", "Vars(x / y)");
    }

    @Test
    void rationalCoefficient() throws Exception {
        fm.assertExec("[x]", "Vars((1/2) * x)");
    }

    // --- Trig / Math functions ---

    @Test
    void sinOfSymbol() throws Exception {
        fm.assertExec("[x]", "Vars(Sin(x))");
    }

    @Test
    void cosOfSymbol() throws Exception {
        fm.assertExec("[x]", "Vars(Cos(x))");
    }

    @Test
    void sinTimesCos() throws Exception {
        fm.assertExec("[y, x]", "Vars(Sin(x) * Cos(y))");
    }

    @Test
    void sqrtOfSymbol() throws Exception {
        fm.assertExec("[x]", "Vars(Sqrt(x))");
    }

    @Test
    void logOfExpression() throws Exception {
        fm.assertExec("[x, y]", "Vars(Log(x + y))");
    }

    @Test
    void nestedMathFunctions() throws Exception {
        fm.assertExec("[x]", "Vars(Sin(Cos(x)))");
    }

    // --- No variables ---

    @Test
    void integerReturnsEmptyList() throws Exception {
        fm.assertExec("[]", "Vars(5)");
    }

    @Test
    void realReturnsEmptyList() throws Exception {
        fm.assertExec("[]", "Vars(3.14)");
    }

    @Test
    void rationalReturnsEmptyList() throws Exception {
        fm.assertExec("[]", "Vars(1/2)");
    }

    @Test
    void stringReturnsEmptyList() throws Exception {
        fm.assertExec("[]", "Vars(\"hello\")");
    }

    @Test
    void booleanReturnsEmptyList() throws Exception {
        fm.assertExec("[]", "Vars(True)");
    }

    // --- Nested / complex expressions ---

    @Test
    void nestedExpression() throws Exception {
        fm.assertExec("[x, y, z]", "Vars(x * (y + z))");
    }

    @Test
    void deeplyNestedExpression() throws Exception {
        fm.assertExec("[a, b, c, d]", "Vars((a + b) * (c - d))");
    }

    @Test
    void manyDuplicates() throws Exception {
        fm.assertExec("[x, y]", "Vars(x^2 + 2*x*y + y^2 + x + y)");
    }

    @Test
    void polynomialThreeVars() throws Exception {
        fm.assertExec("[x, y, z]", "Vars(x^3 + y^2*z + x*y*z)");
    }

    @Test
    void mixedNumericAndSymbolic() throws Exception {
        fm.assertExec("[x]", "Vars(3*x^2 + 2*x + 1)");
    }

}
