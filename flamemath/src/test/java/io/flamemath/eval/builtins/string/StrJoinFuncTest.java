package io.flamemath.eval.builtins.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class StrJoinFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("StrJoin", new StrJoinFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrJoin()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrJoin([\"a\", \"b\"])"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("StrJoin([\"a\"], \",\", \"x\")"));
    }

    // --- Type ---

    @Test
    void firstArgNotListThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrJoin(\"hello\", \",\")"));
    }

    @Test
    void listContainsNonStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrJoin([\"a\", 42], \",\")"));
    }

    @Test
    void delimiterNotStringThrows() {
        assertThrows(Exception.class, () -> fm.execute("StrJoin([\"a\", \"b\"], 1)"));
    }

    // --- Basic ---

    @Test
    void joinTwoStringsWithComma() throws Exception {
        fm.assertExec("\"a,b\"", "StrJoin([\"a\", \"b\"], \",\")");
    }

    @Test
    void joinThreeStringsWithSpace() throws Exception {
        fm.assertExec("\"hello world foo\"", "StrJoin([\"hello\", \"world\", \"foo\"], \" \")");
    }

    @Test
    void joinWithEmptyDelimiter() throws Exception {
        fm.assertExec("\"abc\"", "StrJoin([\"a\", \"b\", \"c\"], \"\")");
    }

    @Test
    void joinSingleElement() throws Exception {
        fm.assertExec("\"hello\"", "StrJoin([\"hello\"], \",\")");
    }

    @Test
    void joinEmptyList() throws Exception {
        fm.assertExec("\"\"", "StrJoin([], \",\")");
    }

    @Test
    void joinWithMultiCharDelimiter() throws Exception {
        fm.assertExec("\"a, b, c\"", "StrJoin([\"a\", \"b\", \"c\"], \", \")");
    }

}
