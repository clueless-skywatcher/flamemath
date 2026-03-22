package io.flamemath.eval.builtins.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class StrSplitFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("StrSplit", new StrSplitFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrSplit()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrSplit(\"hello\")"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrSplit(\"a,b\", \",\", \"x\")"));
    }

    // --- Type ---

    @Test
    void firstArgNotStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrSplit(42, \",\")"));
    }

    @Test
    void delimiterNotStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrSplit(\"a,b\", 1)"));
    }

    // --- Basic ---

    @Test
    void splitByComma() throws Exception {
        fm.assertExec("[\"a\", \"b\", \"c\"]", "StrSplit(\"a,b,c\", \",\")");
    }

    @Test
    void splitBySpace() throws Exception {
        fm.assertExec("[\"hello\", \"world\"]", "StrSplit(\"hello world\", \" \")");
    }

    @Test
    void splitNoMatch() throws Exception {
        fm.assertExec("[\"hello\"]", "StrSplit(\"hello\", \",\")");
    }

    @Test
    void splitEmptyString() throws Exception {
        fm.assertExec("[\"\"]", "StrSplit(\"\", \",\")");
    }

    @Test
    void splitMultiCharDelimiter() throws Exception {
        fm.assertExec("[\"a\", \"b\", \"c\"]", "StrSplit(\"a, b, c\", \", \")");
    }

    @Test
    void splitWithEmptyParts() throws Exception {
        fm.assertExec("[\"a\", \"\", \"b\"]", "StrSplit(\"a,,b\", \",\")");
    }

}
