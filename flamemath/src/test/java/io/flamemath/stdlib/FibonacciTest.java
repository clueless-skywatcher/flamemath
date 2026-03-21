package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FibonacciTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void fibZero() throws Exception {
        fm.assertExec("0", "Fibonacci(0)");
    }

    @Test
    void fibOne() throws Exception {
        fm.assertExec("1", "Fibonacci(1)");
    }

    // --- Small values ---

    @Test
    void fibTwo() throws Exception {
        fm.assertExec("1", "Fibonacci(2)");
    }

    @Test
    void fibThree() throws Exception {
        fm.assertExec("2", "Fibonacci(3)");
    }

    @Test
    void fibSix() throws Exception {
        fm.assertExec("8", "Fibonacci(6)");
    }

    @Test
    void fibTen() throws Exception {
        fm.assertExec("55", "Fibonacci(10)");
    }

    // --- Symbolic ---

    @Test
    void symbolicReturnsUnevaluated() throws Exception {
        fm.assertExec("Fibonacci(x)", "Fibonacci(x)");
    }

    @Test
    void symbolicExpressionReturnsUnevaluated() throws Exception {
        fm.assertExec("Fibonacci(x + 1)", "Fibonacci(x + 1)");
    }

}
