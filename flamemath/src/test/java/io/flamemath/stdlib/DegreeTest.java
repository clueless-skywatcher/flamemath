package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DegreeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Simple variables ---

    @Test
    void degreeOfVariable() throws Exception {
        fm.assertExec("1", "Degree(x, x)");
    }

    @Test
    void degreeOfDifferentVariable() throws Exception {
        fm.assertExec("0", "Degree(y, x)");
    }

    // --- Constants ---

    @Test
    void degreeOfConstant() throws Exception {
        fm.assertExec("0", "Degree(5, x)");
    }

    // --- Powers ---

    @Test
    void degreeOfPower() throws Exception {
        fm.assertExec("3", "Degree(x^3, x)");
    }

    @Test
    void degreeOfPowerDifferentVariable() throws Exception {
        fm.assertExec("0", "Degree(y^4, x)");
    }

    // --- Products ---

    @Test
    void degreeOfProduct() throws Exception {
        fm.assertExec("3", "Degree(x^2 * x, x)");
    }

    @Test
    void degreeOfProductWithCoefficient() throws Exception {
        fm.assertExec("2", "Degree(3 * x^2, x)");
    }

    @Test
    void degreeOfProductMultipleVars() throws Exception {
        fm.assertExec("2", "Degree(x^2 * y^3, x)");
    }

    // --- Sums (polynomials) ---

    @Test
    void degreeOfPolynomial() throws Exception {
        fm.assertExec("3", "Degree(x^3 + 2*x + 1, x)");
    }

    @Test
    void degreeOfQuadratic() throws Exception {
        fm.assertExec("2", "Degree(x^2 + x + 1, x)");
    }

    @Test
    void degreeOfLinear() throws Exception {
        fm.assertExec("1", "Degree(x + 5, x)");
    }

    @Test
    void degreeOfConstantPolynomial() throws Exception {
        fm.assertExec("0", "Degree(3 + 2, x)");
    }

    // --- Multivariate ---

    @Test
    void degreeInFirstVariable() throws Exception {
        fm.assertExec("2", "Degree(x^2 * y + x * y^3, x)");
    }

    @Test
    void degreeInSecondVariable() throws Exception {
        fm.assertExec("3", "Degree(x^2 * y + x * y^3, y)");
    }

    // --- Unevaluated on non-Symbol second argument ---

    @Test
    void unevaluatedOnNonSymbol() throws Exception {
        fm.assertExec("Degree(x^2, 3)", "Degree(x^2, 3)");
    }

    // --- Non-polynomial expressions ---

    @Test
    void degreeOfFunctionCall() throws Exception {
        fm.assertExec("0", "Degree(Sin(x), x)");
    }
}
