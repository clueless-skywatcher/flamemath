package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class TallyTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic tallying ---

    @Test
    void tallySingleElement() throws Exception {
        fm.assertExec("[[1, 1]]", "Tally([1])");
    }

    @Test
    void tallyAllSame() throws Exception {
        fm.assertExec("[[5, 3]]", "Tally([5, 5, 5])");
    }

    @Test
    void tallyAllDistinct() throws Exception {
        fm.assertExec("[[1, 1], [2, 1], [3, 1]]", "Tally([1, 2, 3])");
    }

    @Test
    void tallyMixedCounts() throws Exception {
        fm.assertExec("[[1, 3], [2, 2], [3, 1]]", "Tally([1, 2, 1, 2, 1, 3])");
    }

    // --- Empty list ---

    @Test
    void tallyEmptyList() throws Exception {
        fm.assertExec("[]", "Tally([])");
    }

    // --- Preserves first-occurrence order ---

    @Test
    void tallyPreservesOrder() throws Exception {
        fm.assertExec("[[3, 1], [1, 2], [2, 1]]", "Tally([3, 1, 2, 1])");
    }

    // --- Different types ---

    @Test
    void tallyStrings() throws Exception {
        fm.assertExec("[[\"a\", 2], [\"b\", 1]]", "Tally([\"a\", \"b\", \"a\"])");
    }

    @Test
    void tallyBooleans() throws Exception {
        fm.assertExec("[[True, 2], [False, 1]]", "Tally([True, False, True])");
    }

    @Test
    void tallyMixedTypes() throws Exception {
        fm.assertExec("[[1, 2], [\"a\", 1]]", "Tally([1, \"a\", 1])");
    }

    // --- Nested lists ---

    @Test
    void tallyNestedLists() throws Exception {
        fm.assertExec("[[[1, 2], 2], [[3, 4], 1]]", "Tally([[1, 2], [3, 4], [1, 2]])");
    }

    // --- Variables ---

    @Test
    void tallyFromVariable() throws Exception {
        fm.assertExec("[[1, 2], [2, 1]]", "{l = [1, 2, 1]; Tally(l)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void tallySymbolReturnsRaw() throws Exception {
        fm.assertExec("Tally(x)", "Tally(x)");
    }

    @Test
    void tallyIntegerReturnsRaw() throws Exception {
        fm.assertExec("Tally(42)", "Tally(42)");
    }
}
