package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class CatalanNumberTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void catalanZero() throws Exception {
        fm.assertExec("1", "CatalanNumber(0)");
    }

    @Test
    void catalanOne() throws Exception {
        fm.assertExec("1", "CatalanNumber(1)");
    }

    // --- Standard values ---

    @Test
    void catalanTwo() throws Exception {
        fm.assertExec("2", "CatalanNumber(2)");
    }

    @Test
    void catalanThree() throws Exception {
        fm.assertExec("5", "CatalanNumber(3)");
    }

    @Test
    void catalanFour() throws Exception {
        fm.assertExec("14", "CatalanNumber(4)");
    }

    @Test
    void catalanFive() throws Exception {
        fm.assertExec("42", "CatalanNumber(5)");
    }

    @Test
    void catalanSix() throws Exception {
        fm.assertExec("132", "CatalanNumber(6)");
    }

    @Test
    void catalanSeven() throws Exception {
        fm.assertExec("429", "CatalanNumber(7)");
    }

    @Test
    void catalanTen() throws Exception {
        fm.assertExec("16796", "CatalanNumber(10)");
    }

    // --- Larger values ---

    @Test
    void catalanFifteen() throws Exception {
        fm.assertExec("9694845", "CatalanNumber(15)");
    }

    @Test
    void catalanTwenty() throws Exception {
        fm.assertExec("6564120420", "CatalanNumber(20)");
    }

    // --- Formula identity: CatalanNumber(n) = Binomial(2n, n) / (n + 1) ---

    @Test
    void catalanMatchesBinomialFormula() throws Exception {
        fm.assertExec("CatalanNumber(8)", "Binomial(16, 8) / 9");
    }

    // --- Composed ---

    @Test
    void catalanFromVariable() throws Exception {
        fm.assertExec("42", "{ n = 5; CatalanNumber(n) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void catalanSymbolReturnsRaw() throws Exception {
        fm.assertExec("CatalanNumber(x)", "CatalanNumber(x)");
    }
}
