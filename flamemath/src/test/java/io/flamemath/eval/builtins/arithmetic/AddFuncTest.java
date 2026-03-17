package io.flamemath.eval.builtins.arithmetic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.flamemath.FlameTestingUtils.assertExec;
import static io.flamemath.FlameTestingUtils.execute;
import static org.junit.jupiter.api.Assertions.*;

class AddFuncTest {

    private final AddFunc add = new AddFunc();

    @Test
    void name() {
        assertEquals("Add", new AddFunc().name());
    }

    @Test
    void zeroArgs() throws Exception {
        assertEquals(execute("0"), add.apply(List.of(), null));
    }

    // --- Single args (passthrough) ---

    @Test
    void singleInteger() throws Exception {
        assertExec("5", "Add(5)");
    }

    @Test
    void singleReal() throws Exception {
        assertExec("3.14", "Add(3.14)");
    }

    @Test
    void singleSymbol() throws Exception {
        assertExec("x", "Add(x)");
    }

    // --- Two numeric args ---

    @Test
    void twoIntegers() throws Exception {
        assertExec("5", "2 + 3");
    }

    @Test
    void twoReals() throws Exception {
        assertExec("5.5", "2.5 + 3.0");
    }

    @Test
    void integerPlusReal() throws Exception {
        assertExec("5.5", "2 + 3.5");
    }

    @Test
    void realPlusInteger() throws Exception {
        assertExec("5.5", "3.5 + 2");
    }

    // --- Many numeric args ---

    @Test
    void multipleIntegers() throws Exception {
        assertExec("10", "1 + 2 + 3 + 4");
    }

    @Test
    void mixedNumerics() throws Exception {
        assertExec("6.5", "1 + 2.5 + 3");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void twoSymbols() throws Exception {
        assertExec("Add(x, y)", "x + y");
    }

    @Test
    void symbolPlusZero() throws Exception {
        assertExec("x", "x + 0");
    }

    @Test
    void zeroPlusSymbol() throws Exception {
        assertExec("x", "0 + x");
    }

    // --- Mixed numeric + symbolic ---

    @Test
    void integerPlusSymbol() throws Exception {
        assertExec("Add(3, x)", "3 + x");
    }

    @Test
    void symbolPlusInteger() throws Exception {
        // x + 3 → Add(3, x)  (numeric collected first)
        assertExec("Add(3, x)", "x + 3");
    }

    @Test
    void multipleNumericAndSymbolic() throws Exception {
        // 1 + x + 2 + y → Add(3, x, y)
        assertExec("Add(3, x, y)", "1 + x + 2 + y");
    }

    @Test
    void realAndSymbolic() throws Exception {
        // 1.5 + x + 2 → Add(3.5, x)
        assertExec("Add(3.5, x)", "1.5 + x + 2");
    }

    // --- Nested compound args ---

    @Test
    void compoundArgs() throws Exception {
        assertExec("Add(Sin(x), Cos(y))", "Sin(x) + Cos(y)");
    }

    @Test
    void compoundPlusNumeric() throws Exception {
        assertExec("Add(5, Sin(x))", "2 + Sin(x) + 3");
    }

    // --- Edge: all zeros ---

    @Test
    void allZeros() throws Exception {
        assertExec("0", "0 + 0");
    }

    // --- Negative numbers ---

    @Test
    void negativeIntegers() throws Exception {
        assertExec("-1", "Add(2, -3)");
    }

    @Test
    void negativeCancellation() throws Exception {
        assertExec("0", "Add(5, -5)");
    }

    @Test
    void negativeCancellationWithSymbol() throws Exception {
        // 5 + x + (-5) → numeric cancels to 0, just x remains
        assertExec("x", "Add(5, x, -5)");
    }

    @Test
    void addXCoeffsTogether() throws Exception {
        // 2x + 3x → 5x
        assertExec("5*x", "2*x + 3*x");
    }

    @Test
    void nestedAddGetsFlattenedAndNumericsCollected() throws Exception {
        assertExec("Add(6, x, y)", "Add(1, Add(2, x), Add(3, y))");
    }

    @Test
    void duplicateSymbolsBecomeCoefficientTimesSymbol() throws Exception {
        assertExec("2*x", "x + x");
    }

    @Test
    void symbolicTermsCanCancelCompletely() throws Exception {
        assertExec("0", "x + (-1)*x");
    }

    @Test
    void symbolicCancellationCanLeaveNumericConstant() throws Exception {
        assertExec("5", "x + 5 + (-1)*x");
    }

    @Test
    void mulTermsWithSameCoreHaveCoefficientsMerged() throws Exception {
        assertExec("3*x*y", "x*y + 2*x*y");
    }

    @Test
    void realAndIntegerCoefficientsMergeOnSameSymbolicCore() throws Exception {
        assertExec("3.5*x", "1.5*x + 2*x");
    }
}
