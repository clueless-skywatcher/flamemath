package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.eval.FlameArityException;
import org.junit.jupiter.api.Test;

import static io.flamemath.FlameTestingUtils.assertExec;
import static io.flamemath.FlameTestingUtils.execute;
import static org.junit.jupiter.api.Assertions.*;

class PowFuncTest {

    @Test
    void name() {
        assertEquals("Pow", new PowFunc().name());
    }

    // --- Both numeric ---

    @Test
    void integerToInteger() throws Exception {
        assertExec("8", "2^3");
    }

    @Test
    void integerToZero() throws Exception {
        assertExec("1", "5^0");
    }

    @Test
    void integerToOne() throws Exception {
        assertExec("5", "5^1");
    }

    @Test
    void zeroToPositive() throws Exception {
        assertExec("0", "0^5");
    }

    @Test
    void zeroToZero() throws Exception {
        // Convention: 0^0 = 1
        assertExec("1", "0^0");
    }

    @Test
    void oneToAnything() throws Exception {
        assertExec("1", "1^100");
    }

    @Test
    void negativeExponentPromotesToReal() throws Exception {
        assertExec("0.5", "2^(-1)");
    }

    @Test
    void negativeExponentOnFour() throws Exception {
        assertExec("0.0625", "4^(-2)");
    }

    @Test
    void realBase() throws Exception {
        assertExec("6.25", "2.5^2");
    }

    @Test
    void realExponent() throws Exception {
        assertExec("Pow(2.0, 2)", "2.0^2");
    }

    @Test
    void realBaseAndExponent() throws Exception {
        assertExec("Pow(2.0, 0.5)", "2.0^0.5");
    }

    // --- Symbolic exponent ---

    @Test
    void symbolToZero() throws Exception {
        assertExec("1", "x^0");
    }

    @Test
    void symbolToOne() throws Exception {
        assertExec("x", "x^1");
    }

    @Test
    void symbolToInteger() throws Exception {
        assertExec("Pow(x, 2)", "x^2");
    }

    @Test
    void symbolToSymbol() throws Exception {
        assertExec("Pow(x, y)", "x^y");
    }

    // --- Symbolic base with numeric identities ---

    @Test
    void oneBaseSymbolicExp() throws Exception {
        assertExec("1", "1^n");
    }

    // --- Power of a power ---

    @Test
    void powerOfPowerNumeric() throws Exception {
        // (x^2)^3 → x^6
        assertExec("Pow(x, 6)", "(x^2)^3");
    }

    @Test
    void powerOfPowerSymbolic() throws Exception {
        // (x^a)^b → x^(a*b)
        assertExec("Pow(x, Mul(a, b))", "(x^a)^b");
    }

    @Test
    void powerOfPowerCollapseToOne() throws Exception {
        // (x^2)^0 → 1
        assertExec("1", "(x^2)^0");
    }

    // --- Power of a product ---

    @Test
    void powerOfProduct() throws Exception {
        // (x*y)^2 → x^2 * y^2
        assertExec("Mul(Pow(x, 2), Pow(y, 2))", "(x*y)^2");
    }

    @Test
    void powerOfProductWithCoefficient() throws Exception {
        // (2*x)^2 → 4 * x^2
        assertExec("Mul(4, Pow(x, 2))", "(2*x)^2");
    }

    @Test
    void powerOfProductToOne() throws Exception {
        // (x*y)^1 → x*y
        assertExec("Mul(x, y)", "(x*y)^1");
    }

    @Test
    void powerOfProductToZero() throws Exception {
        // (x*y)^0 → 1
        assertExec("1", "(x*y)^0");
    }

    // --- Does not distribute for non-integer exponent ---

    @Test
    void powerOfProductSymbolicExp() throws Exception {
        // (x*y)^a stays as Pow(x*y, a)
        assertExec("Pow(Mul(x, y), a)", "(x*y)^a");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> execute("Pow()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> execute("Pow(2)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> execute("Pow(2, 3, 4)"));
    }
}
