package io.flamemath.eval.builtins.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class StrLengthFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("StrLength", new StrLengthFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrLength()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrLength(\"a\", \"b\")"));
    }

    // --- Type ---

    @Test
    void nonStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrLength(42)"));
    }

    // --- Basic ---

    @Test
    void emptyString() throws Exception {
        fm.assertExec("0", "StrLength(\"\")");
    }

    @Test
    void singleChar() throws Exception {
        fm.assertExec("1", "StrLength(\"a\")");
    }

    @Test
    void multipleChars() throws Exception {
        fm.assertExec("5", "StrLength(\"hello\")");
    }

    @Test
    void stringWithSpaces() throws Exception {
        fm.assertExec("11", "StrLength(\"hello world\")");
    }

    @Test
    void stringWithDigits() throws Exception {
        fm.assertExec("3", "StrLength(\"123\")");
    }

}
