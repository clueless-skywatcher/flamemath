package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class StrHasTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Contains ---

    @Test
    void containsSubstring() throws Exception {
        fm.assertExec("True", "StrHas(\"hello world\", \"world\")");
    }

    @Test
    void containsAtStart() throws Exception {
        fm.assertExec("True", "StrHas(\"hello world\", \"hello\")");
    }

    @Test
    void containsSelf() throws Exception {
        fm.assertExec("True", "StrHas(\"abc\", \"abc\")");
    }

    @Test
    void containsEmptyString() throws Exception {
        fm.assertExec("True", "StrHas(\"hello\", \"\")");
    }

    @Test
    void containsSingleChar() throws Exception {
        fm.assertExec("True", "StrHas(\"hello\", \"e\")");
    }

    // --- Does not contain ---

    @Test
    void doesNotContain() throws Exception {
        fm.assertExec("False", "StrHas(\"hello\", \"xyz\")");
    }

    @Test
    void caseSensitive() throws Exception {
        fm.assertExec("False", "StrHas(\"Hello\", \"hello\")");
    }

    @Test
    void emptyHaystack() throws Exception {
        fm.assertExec("False", "StrHas(\"\", \"a\")");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicFirstArgReturnsRaw() throws Exception {
        fm.assertExec("StrHas(x, \"hello\")", "StrHas(x, \"hello\")");
    }

    @Test
    void symbolicSecondArgReturnsRaw() throws Exception {
        fm.assertExec("StrHas(\"hello\", y)", "StrHas(\"hello\", y)");
    }

    // --- Variables ---

    @Test
    void fromVariables() throws Exception {
        fm.assertExec("True", "{s = \"flamemath\"; StrHas(s, \"math\")}");
    }
}
