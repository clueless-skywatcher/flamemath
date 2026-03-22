package io.flamemath.eval.builtins.construct;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.NullExpr;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ForFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void captureStdout() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void name() {
        assertEquals("For", new ForFunc().name());
    }

    // --- Returns Null ---

    @Test
    void returnsNull() throws Exception {
        assertSame(NullExpr.INSTANCE, fm.execute("For(i, [1, 2, 3], i)"));
    }

    // --- Basic iteration ---

    @Test
    void iteratesOverList() throws Exception {
        fm.execute("For(i, [1, 2, 3], PrintLn(i))");
        assertEquals("1\n2\n3\n", capturedOut.toString());
    }

    @Test
    void iteratesOverStrings() throws Exception {
        fm.execute("For(s, [\"a\", \"b\", \"c\"], PrintLn(s))");
        assertEquals("a\nb\nc\n", capturedOut.toString());
    }

    @Test
    void emptyListDoesNothing() throws Exception {
        fm.execute("For(i, [], PrintLn(i))");
        assertEquals("", capturedOut.toString());
    }

    @Test
    void singleElementList() throws Exception {
        fm.execute("For(i, [42], PrintLn(i))");
        assertEquals("42\n", capturedOut.toString());
    }

    // --- Accumulation ---

    @Test
    void sumElements() throws Exception {
        fm.assertExec("10", "{ s = 0; For(i, [1, 2, 3, 4], s = s + i); s }");
    }

    @Test
    void productElements() throws Exception {
        fm.assertExec("24", "{ p = 1; For(i, [1, 2, 3, 4], p = p * i); p }");
    }

    // --- Block body ---

    @Test
    void blockBody() throws Exception {
        fm.execute("""
        {
            s = 0;
            For(i, [1, 2, 3], {
                s = s + i;
                PrintLn(s)
            })
        }
        """);
        assertEquals("1\n3\n6\n", capturedOut.toString());
    }

    // --- Scoping ---

    @Test
    void loopVarDoesNotLeakOuterScope() throws Exception {
        fm.assertExec("99", """
        {
            i = 99;
            For(i, [1, 2, 3], i);
            i
        }
        """);
    }

    @Test
    void bodySeesOuterScope() throws Exception {
        fm.assertExec("30", """
        {
            factor = 10;
            s = 0;
            For(i, [1, 2], s = s + i * factor);
            s
        }
        """);
    }

    // --- List from variable ---

    @Test
    void listFromVariable() throws Exception {
        fm.execute("{ l = [10, 20, 30]; For(i, l, PrintLn(i)) }");
        assertEquals("10\n20\n30\n", capturedOut.toString());
    }

    // --- Nested lists ---

    @Test
    void iteratesOverNestedLists() throws Exception {
        fm.execute("For(pair, [[1, 2], [3, 4]], PrintLn(pair))");
        assertEquals("[1, 2]\n[3, 4]\n", capturedOut.toString());
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("For()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("For(i)"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("For(i, [1])"));
    }

    @Test
    void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("For(i, [1], i, i)"));
    }
}
