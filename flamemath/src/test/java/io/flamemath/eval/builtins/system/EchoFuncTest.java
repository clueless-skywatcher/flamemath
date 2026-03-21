package io.flamemath.eval.builtins.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

public class EchoFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();
    // --- Name ---

    @BeforeEach
    void setStream() {
        fm.setStream();
    }

    @AfterEach
    void unsetStream() {
        fm.unsetStream();
    }

    @Test
    void name() {
        assertEquals("Echo", new EchoFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Echo()"));
    }

    // --- Return value ---

    @Test
    void returnsEvaluatedInteger() throws Exception {
        fm.assertExec("2", "Echo(1 + 1)");
    }

    @Test
    void returnsString() throws Exception {
        fm.assertExec("\"hello\"", "Echo(\"hello\")");
    }

    @Test
    void returnsSymbol() throws Exception {
        fm.assertExec("x", "Echo(x)");
    }

    @Test
    void returnsNestedExpression() throws Exception {
        fm.assertExec("6", "Echo(2 * 3)");
    }

    @Test
    void returnsList() throws Exception {
        fm.assertExec("List(1, 2, 3)", "Echo(List(1, 2, 3))");
    }

    // --- Printed output ---

    @Test
    void printsInteger() throws Exception {
        fm.execute("Echo(1 + 1)");
        fm.assertPrint("2" + System.lineSeparator());
    }

    @Test
    void printsString() throws Exception {
        fm.execute("Echo(\"hello\")");
        fm.assertPrint("\"hello\"" + System.lineSeparator());
    }

    @Test
    void printsSymbol() throws Exception {
        fm.execute("Echo(x)");
        fm.assertPrint("x" + System.lineSeparator());
    }

    @Test
    void printsList() throws Exception {
        fm.execute("Echo(List(1, 2, 3))");
        fm.assertPrint("[1, 2, 3]" + System.lineSeparator());
    }

    // --- Usable inline ---

    @Test
    void echoInExpression() throws Exception {
        fm.assertExec("7", "Echo(3) + 4");
    }

    @Test
    void echoInSet() throws Exception {
        fm.execute("Set(x, Echo(42))");
        fm.assertExec("42", "x");
    }

}
