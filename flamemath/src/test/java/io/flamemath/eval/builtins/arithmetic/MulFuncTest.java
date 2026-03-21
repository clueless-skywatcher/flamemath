package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MulFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();
    private final MulFunc mul = new MulFunc();

    @Test
    void name() {
        assertEquals("Mul", mul.name());
    }

    @Test
    void zeroArgs() throws Exception {
        // Note: returns 0, but multiplicative identity should be 1
        assertEquals(fm.execute("0"), mul.apply(List.of(), null));
    }

    // --- Single args (passthrough) ---

    @Test
    void singleInteger() throws Exception {
        fm.assertExec("5", "Mul(5)");
    }

    @Test
    void singleReal() throws Exception {
        fm.assertExec("3.14", "Mul(3.14)");
    }

    @Test
    void singleSymbol() throws Exception {
        fm.assertExec("x", "Mul(x)");
    }

    // --- Two numeric args ---

    @Test
    void twoIntegers() throws Exception {
        fm.assertExec("6", "2 * 3");
    }

    @Test
    void twoReals() throws Exception {
        fm.assertExec("7.5", "2.5 * 3.0");
    }

    @Test
    void integerTimesReal() throws Exception {
        fm.assertExec("7.0", "2 * 3.5");
    }

    @Test
    void realTimesInteger() throws Exception {
        fm.assertExec("7.0", "3.5 * 2");
    }

    // --- Many numeric args ---

    @Test
    void multipleIntegers() throws Exception {
        fm.assertExec("24", "1 * 2 * 3 * 4");
    }

    @Test
    void mixedNumerics() throws Exception {
        fm.assertExec("7.5", "1 * 2.5 * 3");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void twoSymbols() throws Exception {
        fm.assertExec("Mul(x, y)", "x * y");
    }

    // --- Mixed numeric + symbolic ---

    @Test
    void integerTimesSymbol() throws Exception {
        // 2 * x → Mul(2, x), coefficient preserved
        fm.assertExec("Mul(2, x)", "2 * x");
    }

    @Test
    void symbolTimesInteger() throws Exception {
        fm.assertExec("Mul(2, x)", "x * 2");
    }

    @Test
    void symbolTimesOne() throws Exception {
        fm.assertExec("x", "x * 1");
    }

    @Test
    void oneTimesSymbol() throws Exception {
        // 1 * x → x, identity eliminated
        fm.assertExec("x", "1 * x");
    }

    @Test
    void multipleNumericAndSymbolic() throws Exception {
        // 2 * x * 3 * y → Mul(6, x, y)
        fm.assertExec("Mul(6, x, y)", "2 * x * 3 * y");
    }

    @Test
    void realAndSymbolic() throws Exception {
        // 1.5 * x * 2 → Mul(3.0, x)
        fm.assertExec("Mul(3.0, x)", "1.5 * x * 2");
    }

    // --- Nested compound args ---

    @Test
    void compoundArgs() throws Exception {
        fm.assertExec("Mul(Sin(x), Cos(y))", "Sin(x) * Cos(y)");
    }

    @Test
    void compoundTimesNumeric() throws Exception {
        // 2 * Sin(x) * 3 → Mul(6, Sin(x))
        fm.assertExec("Mul(6, Sin(x))", "2 * Sin(x) * 3");
    }

    // --- Edge: all ones ---

    @Test
    void allOnes() throws Exception {
        fm.assertExec("1", "1 * 1");
    }

    // --- Edge: multiply by zero ---

    @Test
    void timesZero() throws Exception {
        fm.assertExec("0", "5 * 0");
    }

    @Test
    void zeroTimesZero() throws Exception {
        fm.assertExec("0", "0 * 0");
    }

    @Test
    void zeroTimesSymbol() throws Exception {
        fm.assertExec("0", "0 * x");
    }

    @Test
    void symbolTimesZero() throws Exception {
        fm.assertExec("0", "x * 0");
    }

    // --- Negative numbers ---

    @Test
    void negativeIntegers() throws Exception {
        fm.assertExec("-6", "Mul(2, -3)");
    }

    @Test
    void twoNegatives() throws Exception {
        fm.assertExec("6", "Mul(-2, -3)");
    }

    @Test
    void negativeOneTimesSymbol() throws Exception {
        fm.assertExec("-x", "(-1) * x");
    }

    @Test
    void nestedMulGetsFlattenedAndNumericsCollected() throws Exception {
        fm.assertExec("Mul(24, x, y)", "Mul(2, Mul(3, x), Mul(4, y))");
    }

    @Test
    void addExpressionRemainsAsSymbolicFactor() throws Exception {
        fm.assertExec("Mul(2, Add(x, y))", "2 * (x + y)");
    }

    // --- Base grouping (decomposeMultiplicative) ---

    @Test
    void duplicateSymbolsCombineIntoPow() throws Exception {
        // x * x → x^2
        fm.assertExec("Pow(x, 2)", "x * x");
    }

    @Test
    void tripleSymbolCombinesIntoPow() throws Exception {
        // x * x * x → x^3
        fm.assertExec("Pow(x, 3)", "x * x * x");
    }

    @Test
    void duplicateSymbolWithCoefficient() throws Exception {
        // 2 * x * x → 2 * x^2
        fm.assertExec("Mul(2, Pow(x, 2))", "2 * x * x");
    }

    @Test
    void powTimesSameBase() throws Exception {
        // x^2 * x → x^3
        fm.assertExec("Pow(x, 3)", "x^2 * x");
    }

    @Test
    void powTimesPow() throws Exception {
        // x^2 * x^3 → x^5
        fm.assertExec("Pow(x, 5)", "x^2 * x^3");
    }

    @Test
    void powTimesInversePow() throws Exception {
        // x^2 * x^(-1) → x
        fm.assertExec("Pow(x, 1)", "x^2 * x^(-1)");
    }

    @Test
    void differentBasesNotGrouped() throws Exception {
        // x * y stays as Mul(x, y)
        fm.assertExec("Mul(x, y)", "x * y");
    }

    @Test
    void mixedBasesGrouped() throws Exception {
        // x * y * x → Mul(x^2, y)
        fm.assertExec("Mul(Pow(x, 2), y)", "x * y * x");
    }

    @Test
    void symbolicExponentGrouping() throws Exception {
        // x^a * x^b → x^(a + b)
        fm.assertExec("Pow(x, Add(a, b))", "x^a * x^b");
    }

    @Test
    void coefficientAndBaseGrouping() throws Exception {
        // 3 * x^2 * 2 * x^3 → 6 * x^5
        fm.assertExec("Mul(6, Pow(x, 5))", "3 * x^2 * 2 * x^3");
    }

    @Test
    void baseGroupingWithMultipleBases() throws Exception {
        // x * y * x * y → x^2 * y^2
        fm.assertExec("Mul(Pow(x, 2), Pow(y, 2))", "x * y * x * y");
    }

    @Test
    void cancellingExponents() throws Exception {
        // x^2 * x^(-2) → exponents sum to 0, dropped → 1
        fm.assertExec("1", "x^2 * x^(-2)");
    }

    // --- Rationalization ---

    @Test
    void integerTimesRational() throws Exception {
        fm.assertExec("(3/2)", "3 * 2^(-1)");
    }

    @Test
    void oneOverTwo() throws Exception {
        fm.assertExec("(1/2)", "1 * 2^(-1)");
    }

    @Test
    void integerTimesInverseSquare() throws Exception {
        fm.assertExec("(3/4)", "3 * 2^(-2)");
    }

    @Test
    void multipleDenominators() throws Exception {
        fm.assertExec("(3/10)", "3 * 2^(-1) * 5^(-1)");
    }

    @Test
    void numeratorProduct() throws Exception {
        fm.assertExec("(6/5)", "2 * 3 * 5^(-1)");
    }

    @Test
    void symbolicBaseDoesNotRationalize() throws Exception {
        fm.assertExec("Mul(3, Pow(x, -1))", "3 * x^(-1)");
    }

    @Test
    void symbolicExponentDoesNotRationalize() throws Exception {
        fm.assertExec("Mul(3, Pow(2, Mul(-1, x)))", "3 * 2^(-x)");
    }

    @Test
    void mixedSymbolicAndIntegerDoesNotRationalize() throws Exception {
        fm.assertExec("Mul((1/2), x)", "x * 2^(-1)");
    }

    @Test
    void positiveExponentDoesNotRationalize() throws Exception {
        fm.assertExec("12", "3 * 2^2");
    }

    // --- Rational reduction ---

    @Test
    void rationalReducesToSimplestForm() throws Exception {
        // 6 * 4^(-1) → (6/4) → reduced to (3/2)
        fm.assertExec("(3/2)", "6 * 4^(-1)");
    }

    @Test
    void rationalReducesToInteger() throws Exception {
        // 6 * 3^(-1) → (6/3) → reduced to 2
        fm.assertExec("2", "6 * 3^(-1)");
    }

    @Test
    void rationalReducesLargeNums() throws Exception {
        // 12 * 8^(-1) → (12/8) → reduced to (3/2)
        fm.assertExec("(3/2)", "12 * 8^(-1)");
    }

    @Test
    void rationalAlreadyReduced() throws Exception {
        // 7 * 3^(-1) → (7/3) — already in simplest form
        fm.assertExec("(7/3)", "7 * 3^(-1)");
    }

    @Test
    void rationalReducesWithMultipleDenominators() throws Exception {
        // 12 * 2^(-1) * 3^(-1) → (12/6) → reduced to 2
        fm.assertExec("2", "12 * 2^(-1) * 3^(-1)");
    }

    @Test
    void rationalReducesNegativeNumerator() throws Exception {
        // -6 * 4^(-1) → (-6/4) → reduced to (-3/2)
        fm.assertExec("(-3/2)", "(-6) * 4^(-1)");
    }

    // --- Slash (division) producing rationals ---

    @Test
    void slashSimple() throws Exception {
        fm.assertExec("(1/2)", "1 / 2");
    }

    @Test
    void slashProducesReducedRational() throws Exception {
        fm.assertExec("(1/2)", "3 / 6");
    }

    @Test
    void slashReducesToInteger() throws Exception {
        fm.assertExec("2", "6 / 3");
    }

    @Test
    void slashAlreadyReduced() throws Exception {
        fm.assertExec("(7/3)", "7 / 3");
    }

    @Test
    void slashLargeReduction() throws Exception {
        fm.assertExec("(3/4)", "12 / 16");
    }

    @Test
    void slashNegativeNumerator() throws Exception {
        fm.assertExec("(-1/2)", "(-1) / 2");
    }

    @Test
    void slashNegativeDenominator() throws Exception {
        fm.assertExec("(-1/2)", "1 / (-2)");
    }

    @Test
    void slashBothNegative() throws Exception {
        fm.assertExec("(1/2)", "(-1) / (-2)");
    }

    @Test
    void slashOneOverOne() throws Exception {
        fm.assertExec("1", "1 / 1");
    }

    @Test
    void slashSymbolicStaysSymbolic() throws Exception {
        fm.assertExec("Mul(x, Pow(y, -1))", "x / y");
    }

    @Test
    void slashIntegerOverSymbol() throws Exception {
        fm.assertExec("Mul(3, Pow(x, -1))", "3 / x");
    }

    // --- Large products ---

    @Test
    void largeProduct() throws Exception {
        fm.assertExec("120", "1 * 2 * 3 * 4 * 5");
    }
}
