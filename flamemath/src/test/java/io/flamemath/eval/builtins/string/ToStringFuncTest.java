package io.flamemath.eval.builtins.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class ToStrFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("ToStr", new ToStrFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ToStr()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ToStr(1, 2)"));
    }

    // --- Integer ---

    @Test
    void integerToStr() throws Exception {
        fm.assertExec("\"42\"", "ToStr(42)");
    }

    @Test
    void negativeIntegerToStr() throws Exception {
        fm.assertExec("\"-5\"", "ToStr(-5)");
    }

    @Test
    void zeroToStr() throws Exception {
        fm.assertExec("\"0\"", "ToStr(0)");
    }

    // --- Real ---

    @Test
    void realToStr() throws Exception {
        fm.assertExec("\"3.14\"", "ToStr(3.14)");
    }

    // --- Boolean ---

    @Test
    void trueToStr() throws Exception {
        fm.assertExec("\"True\"", "ToStr(True)");
    }

    @Test
    void falseToStr() throws Exception {
        fm.assertExec("\"False\"", "ToStr(False)");
    }

    // --- String ---

    @Test
    void stringToStr() throws Exception {
        fm.assertExec("ToStr(\"hello\")", "ToStr(\"hello\")");
    }

    // --- Symbol ---

    @Test
    void symbolToStr() throws Exception {
        fm.assertExec("\"x\"", "ToStr(x)");
    }

}
