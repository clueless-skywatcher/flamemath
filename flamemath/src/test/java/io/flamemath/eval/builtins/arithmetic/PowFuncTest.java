package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Pow", new PowFunc().name());
    }

    // --- Both numeric ---

    @Test
    void integerToInteger() throws Exception {
        fm.assertExec("8", "2^3");
    }

    @Test
    void integerToZero() throws Exception {
        fm.assertExec("1", "5^0");
    }

    @Test
    void integerToOne() throws Exception {
        fm.assertExec("5", "5^1");
    }

    @Test
    void zeroToPositive() throws Exception {
        fm.assertExec("0", "0^5");
    }

    @Test
    void zeroToZero() throws Exception {
        // Convention: 0^0 = 1
        fm.assertExec("1", "0^0");
    }

    @Test
    void oneToAnything() throws Exception {
        fm.assertExec("1", "1^100");
    }

    @Test
    void negativeExponentOnInteger() throws Exception {
        fm.assertExec("(1/2)", "2^(-1)");
    }

    @Test
    void negativeExponentSquaredOnInteger() throws Exception {
        fm.assertExec("(1/16)", "4^(-2)");
    }

    @Test
    void negativeExponentOnThree() throws Exception {
        fm.assertExec("(1/3)", "3^(-1)");
    }

    @Test
    void negativeExponentOnRealStaysReal() throws Exception {
        fm.assertExec("0.5", "2.0^(-1)");
    }

    @Test
    void negativeExponentOnOneReducesToInteger() throws Exception {
        // 1^(-1) → RationalAtom(1, 1) → reduced to 1
        fm.assertExec("1", "1^(-1)");
    }

    @Test
    void realBase() throws Exception {
        fm.assertExec("6.25", "2.5^2");
    }

    @Test
    void realExponent() throws Exception {
        fm.assertExec("Pow(2.0, 2)", "2.0^2");
    }

    @Test
    void realBaseAndExponent() throws Exception {
        fm.assertExec("Pow(2.0, 0.5)", "2.0^0.5");
    }

    // --- Symbolic exponent ---

    @Test
    void symbolToZero() throws Exception {
        fm.assertExec("1", "x^0");
    }

    @Test
    void symbolToOne() throws Exception {
        fm.assertExec("x", "x^1");
    }

    @Test
    void symbolToInteger() throws Exception {
        fm.assertExec("Pow(x, 2)", "x^2");
    }

    @Test
    void symbolToSymbol() throws Exception {
        fm.assertExec("Pow(x, y)", "x^y");
    }

    // --- Symbolic base with numeric identities ---

    @Test
    void oneBaseSymbolicExp() throws Exception {
        fm.assertExec("1", "1^n");
    }

    // --- Power of a power ---

    @Test
    void powerOfPowerNumeric() throws Exception {
        // (x^2)^3 → x^6
        fm.assertExec("Pow(x, 6)", "(x^2)^3");
    }

    @Test
    void powerOfPowerSymbolic() throws Exception {
        // (x^a)^b → x^(a*b)
        fm.assertExec("Pow(x, Mul(a, b))", "(x^a)^b");
    }

    @Test
    void powerOfPowerCollapseToOne() throws Exception {
        // (x^2)^0 → 1
        fm.assertExec("1", "(x^2)^0");
    }

    // --- Power of a product ---

    @Test
    void powerOfProduct() throws Exception {
        // (x*y)^2 → x^2 * y^2
        fm.assertExec("Mul(Pow(x, 2), Pow(y, 2))", "(x*y)^2");
    }

    @Test
    void powerOfProductWithCoefficient() throws Exception {
        // (2*x)^2 → 4 * x^2
        fm.assertExec("Mul(4, Pow(x, 2))", "(2*x)^2");
    }

    @Test
    void powerOfProductToOne() throws Exception {
        // (x*y)^1 → x*y
        fm.assertExec("Mul(x, y)", "(x*y)^1");
    }

    @Test
    void powerOfProductToZero() throws Exception {
        // (x*y)^0 → 1
        fm.assertExec("1", "(x*y)^0");
    }

    // --- Does not distribute for non-integer exponent ---

    @Test
    void powerOfProductSymbolicExp() throws Exception {
        // (x*y)^a stays as Pow(x*y, a)
        fm.assertExec("Pow(Mul(x, y), a)", "(x*y)^a");
    }

    // --- Pow interacts with Mul base grouping ---

    @Test
    void powResultGetsCombinedInMul() throws Exception {
        // x * x^2 → x^3 (Mul groups, then Pow evaluates)
        fm.assertExec("Pow(x, 3)", "x * x^2");
    }

    @Test
    void powOfPowerTriple() throws Exception {
        // ((x^2)^3)^4 → x^24
        fm.assertExec("Pow(x, 24)", "((x^2)^3)^4");
    }

    @Test
    void powerOfPowerCollapsesToBase() throws Exception {
        // (x^a)^1 → x^a
        fm.assertExec("Pow(x, a)", "(x^a)^1");
    }

    // --- Negative base ---

    @Test
    void negativeBaseEvenExp() throws Exception {
        fm.assertExec("4", "(-2)^2");
    }

    @Test
    void negativeBaseOddExp() throws Exception {
        fm.assertExec("-8", "(-2)^3");
    }

    // --- Power of Add (stays unevaluated) ---

    @Test
    void powerOfAddStaysUnevaluated() throws Exception {
        // (x + y)^2 → Pow(Add(x, y), 2), not distributed
        fm.assertExec("Pow(Add(x, y), 2)", "(x + y)^2");
    }

    // --- Power of a product with more terms ---

    @Test
    void powerOfTripleProduct() throws Exception {
        // (x*y*z)^2 → x^2 * y^2 * z^2
        fm.assertExec("Mul(Pow(x, 2), Pow(y, 2), Pow(z, 2))", "(x*y*z)^2");
    }

    @Test
    void powerOfProductWithNegativeCoeff() throws Exception {
        // (-1 * x)^2 → x^2
        fm.assertExec("Pow(x, 2)", "(-x)^2");
    }

    // --- Division produces Pow(-1) which interacts ---

    @Test
    void divisionCreatesPowMinusOne() throws Exception {
        // x / y → Mul(x, Pow(y, -1)) → evaluates Pow → Mul(x, Pow(y, -1))
        fm.assertExec("Mul(x, Pow(y, -1))", "x / y");
    }

    @Test
    void divisionBySameBaseCancels() throws Exception {
        // x / x → x * x^(-1) → x^0 → 1
        fm.assertExec("1", "x / x");
    }

    @Test
    void squaredOverBase() throws Exception {
        // x^2 / x → x^2 * x^(-1) → x^1 → x
        fm.assertExec("x", "x^2 / x");
    }

    // --- Division by zero ---

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("1 / 0"));
    }

    @Test
    void zeroToNegativeExponentThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("0^(-1)"));
    }

    @Test
    void zeroToNegativeTwoThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("0^(-2)"));
    }

    // --- Big values (overflow long) ---

    @Test
    void bigPowIdentity() throws Exception {
        // 2^100 = 2^50 * 2^50
        fm.assertExec("2^50 * 2^50", "2^100");
    }

    @Test
    void bigPowChain() throws Exception {
        // (2^10)^10 = 2^100
        fm.assertExec("2^100", "(2^10)^10");
    }

    @Test
    void bigNegativeExponent() throws Exception {
        // 2^(-50) * 2^50 = 1
        fm.assertExec("1", "2^(-50) * 2^50");
    }

    // --- Power of Sqrt ---

    @Test
    void sqrtSquaredSimplifies() throws Exception {
        // Sqrt(x)^2 → x
        fm.assertExec("x", "Sqrt(x)^2");
    }

    @Test
    void sqrtToFourth() throws Exception {
        // Sqrt(x)^4 → x^2
        fm.assertExec("Pow(x, 2)", "Sqrt(x)^4");
    }

    @Test
    void sqrtCubed() throws Exception {
        // Sqrt(x)^3 → x^(3/2)
        fm.assertExec("Pow(x, (3/2))", "Sqrt(x)^3");
    }

    @Test
    void sqrtToZero() throws Exception {
        // Sqrt(x)^0 → 1
        fm.assertExec("1", "Sqrt(x)^0");
    }

    @Test
    void sqrtToOne() throws Exception {
        // Sqrt(x)^1 → Sqrt(x)
        fm.assertExec("Sqrt(x)", "Sqrt(x)^1");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Pow()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Pow(2)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Pow(2, 3, 4)"));
    }
}
