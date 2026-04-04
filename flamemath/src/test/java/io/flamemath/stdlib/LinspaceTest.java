package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class LinspaceTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void twoPoints() throws Exception {
        fm.assertExec("[0.0, 1.0]", "Linspace(0, 1, 2)");
    }

    @Test
    void threePoints() throws Exception {
        fm.assertExec("[0.0, 0.5, 1.0]", "Linspace(0, 1, 3)");
    }

    @Test
    void fivePoints() throws Exception {
        fm.assertExec("[0.0, 0.25, 0.5, 0.75, 1.0]", "Linspace(0, 1, 5)");
    }

    // --- Negative range ---

    @Test
    void negativeToPositive() throws Exception {
        fm.assertExec("[-1.0, 0.0, 1.0]", "Linspace(-1, 1, 3)");
    }

    @Test
    void negativeRange() throws Exception {
        fm.assertExec("[-10.0, -5.0, 0.0]", "Linspace(-10, 0, 3)");
    }

    // --- Descending ---

    @Test
    void descendingRange() throws Exception {
        fm.assertExec("[1.0, 0.5, 0.0]", "Linspace(1, 0, 3)");
    }

    // --- Same start and stop ---

    @Test
    void sameStartStop() throws Exception {
        fm.assertExec("[5.0, 5.0, 5.0]", "Linspace(5, 5, 3)");
    }

    // --- Floating point inputs ---

    @Test
    void floatInputs() throws Exception {
        fm.assertExec("[0.5, 1.0, 1.5]", "Linspace(0.5, 1.5, 3)");
    }

    // --- Edge cases ---

    @Test
    void singlePoint() throws Exception {
        fm.assertExec("[0]", "Linspace(0, 10, 1)");
    }

    @Test
    void zeroNReturnsEmpty() throws Exception {
        fm.assertExec("[]", "Linspace(0, 1, 0)");
    }

    @Test
    void negativeNReturnsEmpty() throws Exception {
        fm.assertExec("[]", "Linspace(0, 1, -1)");
    }

    // --- Length check ---

    @Test
    void correctLength() throws Exception {
        fm.assertExec("10", "Len(Linspace(0, 1, 10))");
    }

    // --- Endpoints are exact ---

    @Test
    void firstElementIsStart() throws Exception {
        fm.assertExec("3.0", "Linspace(3, 7, 5)[0]");
    }

    @Test
    void lastElementIsStop() throws Exception {
        fm.assertExec("7.0", "Linspace(3, 7, 5)[4]");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicStartReturnsRaw() throws Exception {
        fm.assertExec("Linspace(x, 1, 5)", "Linspace(x, 1, 5)");
    }

    @Test
    void symbolicStopReturnsRaw() throws Exception {
        fm.assertExec("Linspace(0, y, 5)", "Linspace(0, y, 5)");
    }

    @Test
    void symbolicNReturnsRaw() throws Exception {
        fm.assertExec("Linspace(0, 1, n)", "Linspace(0, 1, n)");
    }

    // --- Composed ---

    @Test
    void linspaceFromVariables() throws Exception {
        fm.assertExec("[0.0, 5.0, 10.0]", "{ a = 0; b = 10; Linspace(a, b, 3) }");
    }
}
