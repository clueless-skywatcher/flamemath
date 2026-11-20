package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class CoeffTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Simple variable ---

    @Test
    void coeffOfVariable() throws Exception {
        fm.assertExec("1", "Coeff(x, x)");
    }

    @Test
    void coeffOfDifferentVariable() throws Exception {
        fm.assertExec("0", "Coeff(y, x)");
    }

    // --- Constants ---

    @Test
    void coeffOfConstant() throws Exception {
        fm.assertExec("0", "Coeff(5, x)");
    }

    // --- Products with coefficient ---

    @Test
    void coeffOfScaledVariable() throws Exception {
        fm.assertExec("3", "Coeff(3*x, x)");
    }

    @Test
    void coeffOfScaledPower() throws Exception {
        fm.assertExec("3", "Coeff(3*x^2, x^2)");
    }

    // --- Sums ---

    @Test
    void coeffInPolynomial() throws Exception {
        fm.assertExec("2", "Coeff(x^2 + 2*x + 1, x)");
    }

    @Test
    void coeffOfSquaredTerm() throws Exception {
        fm.assertExec("1", "Coeff(x^2 + 2*x + 1, x^2)");
    }

    // --- Multivariate ---

    @Test
    void coeffReturnsExpression() throws Exception {
        fm.assertExec("1 + y", "Coeff(x*y + x + 1, x)");
    }

    @Test
    void coeffCollectsMultipleTerms() throws Exception {
        fm.assertExec("Add(3, y)", "Coeff(3*x^2 + x^2*y, x^2)");
    }

    // --- No match ---

    @Test
    void coeffOfAbsentTerm() throws Exception {
        fm.assertExec("0", "Coeff(x + 1, x^2)");
    }

    // --- Single term (non-Add) ---

    @Test
    void coeffOfSingleProduct() throws Exception {
        fm.assertExec("3", "Coeff(3*x, x)");
    }

    @Test
    void coeffOfSingleExactMatch() throws Exception {
        fm.assertExec("1", "Coeff(x^2, x^2)");
    }
}
