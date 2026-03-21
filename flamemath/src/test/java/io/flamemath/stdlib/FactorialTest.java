package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FactorialTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void factorialZero() throws Exception {
        fm.assertExec("1", "Factorial(0)");
    }

    @Test
    void factorialOne() throws Exception {
        fm.assertExec("1", "Factorial(1)");
    }

    // --- Small values ---

    @Test
    void factorialTwo() throws Exception {
        fm.assertExec("2", "Factorial(2)");
    }

    @Test
    void factorialFive() throws Exception {
        fm.assertExec("120", "Factorial(5)");
    }

    @Test
    void factorialTen() throws Exception {
        fm.assertExec("3628800", "Factorial(10)");
    }

    // --- Symbolic ---

    @Test
    void symbolicReturnsUnevaluated() throws Exception {
        fm.assertExec("Factorial(x)", "Factorial(x)");
    }

    @Test
    void symbolicExpressionReturnsUnevaluated() throws Exception {
        fm.assertExec("Factorial(x + 1)", "Factorial(x + 1)");
    }

}
