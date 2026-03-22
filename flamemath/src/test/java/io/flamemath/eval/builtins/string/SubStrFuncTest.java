package io.flamemath.eval.builtins.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class SubStrFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("SubStr", new SubStrFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SubStr()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SubStr(\"hello\")"));
    }

    @Test
    void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SubStr(\"hello\", 1, 3, 5)"));
    }

    // --- Type ---

    @Test
    void firstArgNotStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("SubStr(42, 1, 3)"));
    }

    @Test
    void startIndexNotIntegerThrows() {
        assertThrows(Exception.class, () -> fm.execute("SubStr(\"hello\", \"a\", 3)"));
    }

    // --- Basic (two args: string, count) --- takes first n characters

    @Test
    void takeOne() throws Exception {
        fm.assertExec("\"h\"", "SubStr(\"hello\", 1)");
    }

    @Test
    void takeTwo() throws Exception {
        fm.assertExec("\"he\"", "SubStr(\"hello\", 2)");
    }

    @Test
    void takeThree() throws Exception {
        fm.assertExec("\"hel\"", "SubStr(\"hello\", 3)");
    }

    @Test
    void takeAll() throws Exception {
        fm.assertExec("\"hello\"", "SubStr(\"hello\", 5)");
    }

    // --- Basic (three args: string, start, end) --- exclusive of endpoints

    @Test
    void substringRange() throws Exception {
        fm.assertExec("\"ell\"", "SubStr(\"hello\", 1, 4)");
    }

    @Test
    void substringMiddle() throws Exception {
        fm.assertExec("\"ll\"", "SubStr(\"hello\", 2, 4)");
    }

    @Test
    void substringFullExclusive() throws Exception {
        fm.assertExec("\"ello\"", "SubStr(\"hello\", 1, 5)");
    }

    @Test
    void substringAdjacentIndices() throws Exception {
        fm.assertExec("\"e\"", "SubStr(\"hello\", 1, 2)");
    }

    @Test
    void emptyStringSubstr() throws Exception {
        assertThrows(Exception.class, () -> fm.execute("SubStr(\"\", 1)"));
    }

}
