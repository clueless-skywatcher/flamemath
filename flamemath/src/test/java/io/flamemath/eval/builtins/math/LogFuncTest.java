package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Log", new LogFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Log()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Log(1, 2)"));
    }

    // --- Log(1) = 0 ---

    @Test
    void logOne() throws Exception {
        fm.assertExec("0", "Log(1)");
    }

    @Test
    void logOneReal() throws Exception {
        fm.assertExec("0", "Log(1.0)");
    }

    // --- Log(E) = 1 ---

    @Test
    void logE() throws Exception {
        fm.assertExec("1", "Log(E)");
    }

    // --- Real → Math.log ---

    @Test
    void logRealTwo() throws Exception {
        var result = (RealAtom) fm.execute("Log(2.0)");
        assertEquals(Math.log(2.0), result.value(), 1e-10);
    }

    @Test
    void logRealTen() throws Exception {
        var result = (RealAtom) fm.execute("Log(10.0)");
        assertEquals(Math.log(10.0), result.value(), 1e-10);
    }

    @Test
    void logRealE() throws Exception {
        fm.assertExec("1", "Log(2.718281828459045)");
    }

    // --- Integer (non-1) → stays symbolic ---

    @Test
    void logTwo() throws Exception {
        fm.assertExec("Log(2)", "Log(2)");
    }

    @Test
    void logTen() throws Exception {
        fm.assertExec("Log(10)", "Log(10)");
    }

    @Test
    void logThree() throws Exception {
        fm.assertExec("Log(3)", "Log(3)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void logSymbol() throws Exception {
        fm.assertExec("Log(x)", "Log(x)");
    }

    @Test
    void logExpression() throws Exception {
        fm.assertExec("Log(Add(1, x))", "Log(1 + x)");
    }
}
