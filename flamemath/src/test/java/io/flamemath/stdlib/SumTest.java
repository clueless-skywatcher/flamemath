package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class SumTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void emptyList() throws Exception {
        fm.assertExec("0", "Sum([])");
    }

    @Test
    void singleElement() throws Exception {
        fm.assertExec("5", "Sum([5])");
    }

    @Test
    void multipleElements() throws Exception {
        fm.assertExec("6", "Sum([1, 2, 3])");
    }

    @Test
    void largerList() throws Exception {
        fm.assertExec("15", "Sum([1, 2, 3, 4, 5])");
    }

    @Test
    void withNegatives() throws Exception {
        fm.assertExec("0", "Sum([1, -1, 2, -2])");
    }

    @Test
    void withRange() throws Exception {
        fm.assertExec("10", "Sum(Range(1, 5))");
    }

}
