package io.flamemath.stdlib;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DivTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void exactDivision() throws Exception {
        fm.assertExec("2", "Div(6, 3)");
    }

    @Test
    void rationalResult() throws Exception {
        fm.assertExec("7 / 2", "Div(7, 2)");
    }

    @Test
    void divideByOne() throws Exception {
        fm.assertExec("5", "Div(5, 1)");
    }

    @Test
    void zeroDividend() throws Exception {
        fm.assertExec("0", "Div(0, 3)");
    }

    @Test
    void realDivision() throws Exception {
        fm.assertExec("2.5", "Div(5.0, 2.0)");
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("Div(1, 0)"));
    }

}
