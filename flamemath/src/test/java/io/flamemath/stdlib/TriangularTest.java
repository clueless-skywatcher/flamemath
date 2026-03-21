package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class TriangularTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void triangularOne() throws Exception {
        fm.assertExec("1", "Triangular(1)");
    }

    @Test
    void triangularFive() throws Exception {
        fm.assertExec("15", "Triangular(5)");
    }

    @Test
    void triangularTen() throws Exception {
        fm.assertExec("55", "Triangular(10)");
    }

    @Test
    void triangularHundred() throws Exception {
        fm.assertExec("5050", "Triangular(100)");
    }

}
