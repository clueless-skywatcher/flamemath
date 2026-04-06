package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FreeOfTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Free of variable ---

    @Test
    void freeOfAbsentVariable() throws Exception {
        fm.assertExec("True", "FreeOf(y + 1, x)");
    }

    @Test
    void freeOfConstant() throws Exception {
        fm.assertExec("True", "FreeOf(5, x)");
    }

    // --- Not free of variable ---

    @Test
    void notFreeOfPresentVariable() throws Exception {
        fm.assertExec("False", "FreeOf(x^2 + y, x)");
    }

    @Test
    void notFreeOfSingleSymbol() throws Exception {
        fm.assertExec("False", "FreeOf(x, x)");
    }

    // --- Subtraction / Negation ---

    @Test
    void freeOfInSubtraction() throws Exception {
        fm.assertExec("True", "FreeOf(a - b, x)");
    }

    @Test
    void notFreeOfInSubtraction() throws Exception {
        fm.assertExec("False", "FreeOf(x - y, x)");
    }

    // --- Division ---

    @Test
    void notFreeOfInDenominator() throws Exception {
        fm.assertExec("False", "FreeOf(1 / x, x)");
    }

    @Test
    void freeOfInDivision() throws Exception {
        fm.assertExec("True", "FreeOf(a / b, x)");
    }

    // --- Trig / Math functions ---

    @Test
    void notFreeOfInsideSin() throws Exception {
        fm.assertExec("False", "FreeOf(Sin(x), x)");
    }

    @Test
    void freeOfInsideCos() throws Exception {
        fm.assertExec("True", "FreeOf(Cos(y), x)");
    }

    @Test
    void notFreeOfInsideNestedFunction() throws Exception {
        fm.assertExec("False", "FreeOf(Log(Sin(x)), x)");
    }

    // --- Multiple variables ---

    @Test
    void freeOfOneButNotAnother() throws Exception {
        fm.assertExec("True", "FreeOf(x^2 + 1, y)");
    }

    @Test
    void multiVarExpressionFreeOfThird() throws Exception {
        fm.assertExec("True", "FreeOf(x*y + x^2, z)");
    }

    // --- Complex expressions ---

    @Test
    void notFreeOfInPolynomial() throws Exception {
        fm.assertExec("False", "FreeOf(3*x^2 + 2*x + 1, x)");
    }

    @Test
    void freeOfInPureNumericExpression() throws Exception {
        fm.assertExec("True", "FreeOf(3 + 2*5, x)");
    }

    @Test
    void notFreeOfInProduct() throws Exception {
        fm.assertExec("False", "FreeOf(x * y * z, y)");
    }

    @Test
    void notFreeOfInPower() throws Exception {
        fm.assertExec("False", "FreeOf(x^3, x)");
    }
}
