package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class CompositionsTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void singlePartComposition() throws Exception {
        fm.assertExec("[[5]]", "Compositions(5, 1)");
    }

    @Test
    void allOnesComposition() throws Exception {
        fm.assertExec("[[1, 1, 1]]", "Compositions(3, 3)");
    }

    @Test
    void kGreaterThanN() throws Exception {
        fm.assertExec("[]", "Compositions(3, 5)");
    }

    // --- Standard values ---

    @Test
    void compositionsOf4Into2() throws Exception {
        fm.assertExec("[[1, 3], [2, 2], [3, 1]]", "Compositions(4, 2)");
    }

    @Test
    void compositionsOf5Into2() throws Exception {
        fm.assertExec("[[1, 4], [2, 3], [3, 2], [4, 1]]", "Compositions(5, 2)");
    }

    @Test
    void compositionsOf5Into3() throws Exception {
        fm.assertExec("[[1, 1, 3], [1, 2, 2], [1, 3, 1], [2, 1, 2], [2, 2, 1], [3, 1, 1]]", "Compositions(5, 3)");
    }

    @Test
    void compositionsOf4Into3() throws Exception {
        fm.assertExec("[[1, 1, 2], [1, 2, 1], [2, 1, 1]]", "Compositions(4, 3)");
    }

    // --- Counts match C(n-1, k-1) ---

    @Test
    void countCompositions6Into3() throws Exception {
        fm.assertExec("10", "Len(Compositions(6, 3))");
    }

    @Test
    void countCompositions7Into3() throws Exception {
        fm.assertExec("15", "Len(Compositions(7, 3))");
    }

    @Test
    void countCompositions6Into4() throws Exception {
        fm.assertExec("10", "Len(Compositions(6, 4))");
    }

    @Test
    void countCompositions8Into4() throws Exception {
        fm.assertExec("35", "Len(Compositions(8, 4))");
    }

    // --- Each composition sums to n ---

    @Test
    void allCompositionsSumToN() throws Exception {
        fm.assertExec("True", "{ cs = Compositions(7, 3); result = True; For(c, cs, { If(Sum(c) != 7, result = False) }); result }");
    }

    // --- Each composition has exactly k parts ---

    @Test
    void allCompositionsHaveKParts() throws Exception {
        fm.assertExec("True", "{ cs = Compositions(6, 4); result = True; For(c, cs, { If(Len(c) != 4, result = False) }); result }");
    }

    // --- All parts are positive ---

    @Test
    void allPartsPositive() throws Exception {
        fm.assertExec("True", "{ cs = Compositions(6, 3); result = True; For(c, cs, { For(x, c, { If(x < 1, result = False) }) }); result }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicNReturnsRaw() throws Exception {
        fm.assertExec("Compositions(x, 3)", "Compositions(x, 3)");
    }

    @Test
    void symbolicKReturnsRaw() throws Exception {
        fm.assertExec("Compositions(5, k)", "Compositions(5, k)");
    }

    // --- Edge cases ---

    @Test
    void negativeNReturnsRaw() throws Exception {
        fm.assertExec("Compositions(-1, 2)", "Compositions(-1, 2)");
    }

    @Test
    void zeroNReturnsRaw() throws Exception {
        fm.assertExec("Compositions(0, 2)", "Compositions(0, 2)");
    }

    @Test
    void zeroKReturnsRaw() throws Exception {
        fm.assertExec("Compositions(5, 0)", "Compositions(5, 0)");
    }

    // --- Composed ---

    @Test
    void compositionsFromVariables() throws Exception {
        fm.assertExec("[[1, 3], [2, 2], [3, 1]]", "{ n = 4; k = 2; Compositions(n, k) }");
    }
}
