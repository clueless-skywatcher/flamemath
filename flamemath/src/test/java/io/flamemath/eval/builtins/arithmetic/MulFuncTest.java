package io.flamemath.eval.builtins.arithmetic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.flamemath.FlameTestingUtils.assertExec;
import static io.flamemath.FlameTestingUtils.execute;
import static org.junit.jupiter.api.Assertions.*;

class MulFuncTest {

    private final MulFunc mul = new MulFunc();

    @Test
    void name() {
        assertEquals("Mul", mul.name());
    }

    @Test
    void zeroArgs() throws Exception {
        // Note: returns 0, but multiplicative identity should be 1
        assertEquals(execute("0"), mul.apply(List.of(), null));
    }

    // --- Single args (passthrough) ---

    @Test
    void singleInteger() throws Exception {
        assertExec("5", "Mul(5)");
    }

    @Test
    void singleReal() throws Exception {
        assertExec("3.14", "Mul(3.14)");
    }

    @Test
    void singleSymbol() throws Exception {
        assertExec("x", "Mul(x)");
    }

    // --- Two numeric args ---

    @Test
    void twoIntegers() throws Exception {
        assertExec("6", "2 * 3");
    }

    @Test
    void twoReals() throws Exception {
        assertExec("7.5", "2.5 * 3.0");
    }

    @Test
    void integerTimesReal() throws Exception {
        assertExec("7.0", "2 * 3.5");
    }

    @Test
    void realTimesInteger() throws Exception {
        assertExec("7.0", "3.5 * 2");
    }

    // --- Many numeric args ---

    @Test
    void multipleIntegers() throws Exception {
        assertExec("24", "1 * 2 * 3 * 4");
    }

    @Test
    void mixedNumerics() throws Exception {
        assertExec("7.5", "1 * 2.5 * 3");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void twoSymbols() throws Exception {
        assertExec("Mul(x, y)", "x * y");
    }

    // --- Mixed numeric + symbolic ---

    @Test
    void integerTimesSymbol() throws Exception {
        // 2 * x → Mul(2, x), coefficient preserved
        assertExec("Mul(2, x)", "2 * x");
    }

    @Test
    void symbolTimesInteger() throws Exception {
        assertExec("Mul(2, x)", "x * 2");
    }

    @Test
    void symbolTimesOne() throws Exception {
        assertExec("x", "x * 1");
    }

    @Test
    void oneTimesSymbol() throws Exception {
        // 1 * x → x, identity eliminated
        assertExec("x", "1 * x");
    }

    @Test
    void multipleNumericAndSymbolic() throws Exception {
        // 2 * x * 3 * y → Mul(6, x, y)
        assertExec("Mul(6, x, y)", "2 * x * 3 * y");
    }

    @Test
    void realAndSymbolic() throws Exception {
        // 1.5 * x * 2 → Mul(3.0, x)
        assertExec("Mul(3.0, x)", "1.5 * x * 2");
    }

    // --- Nested compound args ---

    @Test
    void compoundArgs() throws Exception {
        assertExec("Mul(Sin(x), Cos(y))", "Sin(x) * Cos(y)");
    }

    @Test
    void compoundTimesNumeric() throws Exception {
        // 2 * Sin(x) * 3 → Mul(6, Sin(x))
        assertExec("Mul(6, Sin(x))", "2 * Sin(x) * 3");
    }

    // --- Edge: all ones ---

    @Test
    void allOnes() throws Exception {
        assertExec("1", "1 * 1");
    }

    // --- Edge: multiply by zero ---

    @Test
    void timesZero() throws Exception {
        assertExec("0", "5 * 0");
    }

    @Test
    void zeroTimesZero() throws Exception {
        assertExec("0", "0 * 0");
    }

    @Test
    void zeroTimesSymbol() throws Exception {
        assertExec("0", "0 * x");
    }

    @Test
    void symbolTimesZero() throws Exception {
        assertExec("0", "x * 0");
    }

    // --- Negative numbers ---

    @Test
    void negativeIntegers() throws Exception {
        assertExec("-6", "Mul(2, -3)");
    }

    @Test
    void twoNegatives() throws Exception {
        assertExec("6", "Mul(-2, -3)");
    }

    @Test
    void negativeOneTimesSymbol() throws Exception {
        assertExec("-x", "(-1) * x");
    }

    @Test
    void nestedMulGetsFlattenedAndNumericsCollected() throws Exception {
        assertExec("Mul(24, x, y)", "Mul(2, Mul(3, x), Mul(4, y))");
    }

    @Test
    void addExpressionRemainsAsSymbolicFactor() throws Exception {
        assertExec("Mul(2, Add(x, y))", "2 * (x + y)");
    }

    @Test
    void duplicateSymbolsAreNotCombinedIntoPowers() throws Exception {
        assertExec("Mul(x, x)", "x * x");
    }

    // --- Large products ---

    @Test
    void largeProduct() throws Exception {
        assertExec("120", "1 * 2 * 3 * 4 * 5");
    }
}
