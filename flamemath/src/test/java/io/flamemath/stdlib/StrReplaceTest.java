package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class StrReplaceTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic replacement ---

    @Test
    void replaceWord() throws Exception {
        fm.assertExec("\"hello flame\"", "StrReplace(\"hello world\", \"world\", \"flame\")");
    }

    @Test
    void replaceAtStart() throws Exception {
        fm.assertExec("\"goodbye world\"", "StrReplace(\"hello world\", \"hello\", \"goodbye\")");
    }

    @Test
    void replaceSingleChar() throws Exception {
        fm.assertExec("\"hxllo\"", "StrReplace(\"hello\", \"e\", \"x\")");
    }

    // --- Multiple occurrences ---

    @Test
    void replaceAllOccurrences() throws Exception {
        fm.assertExec("\"x-x-x\"", "StrReplace(\"a-a-a\", \"a\", \"x\")");
    }

    // --- No match ---

    @Test
    void noMatchReturnsOriginal() throws Exception {
        fm.assertExec("\"hello\"", "StrReplace(\"hello\", \"xyz\", \"abc\")");
    }

    // --- Edge cases ---

    @Test
    void replaceWithEmpty() throws Exception {
        fm.assertExec("\"hllo\"", "StrReplace(\"hello\", \"e\", \"\")");
    }

    @Test
    void replaceEmptyTarget() throws Exception {
        fm.assertExec("\"xhxexlxlxox\"", "StrReplace(\"hello\", \"\", \"x\")");
    }

    @Test
    void emptyString() throws Exception {
        fm.assertExec("\"x\"", "StrReplace(\"\", \"\", \"x\")");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicFirstArgReturnsRaw() throws Exception {
        fm.assertExec("StrReplace(x, \"a\", \"b\")", "StrReplace(x, \"a\", \"b\")");
    }

    @Test
    void symbolicSecondArgReturnsRaw() throws Exception {
        fm.assertExec("StrReplace(\"hello\", y, \"b\")", "StrReplace(\"hello\", y, \"b\")");
    }

    @Test
    void symbolicThirdArgReturnsRaw() throws Exception {
        fm.assertExec("StrReplace(\"hello\", \"a\", z)", "StrReplace(\"hello\", \"a\", z)");
    }

    // --- Variables ---

    @Test
    void fromVariables() throws Exception {
        fm.assertExec("\"hello flame\"", "{s = \"hello world\"; StrReplace(s, \"world\", \"flame\")}");
    }
}
