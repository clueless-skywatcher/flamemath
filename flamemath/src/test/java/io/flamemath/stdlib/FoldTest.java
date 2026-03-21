package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FoldTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void foldSum() throws Exception {
        fm.assertExec("15", "Fold((acc, x) => acc + x, 0, [1, 2, 3, 4, 5])");
    }

    @Test
    void foldProduct() throws Exception {
        fm.assertExec("24", "Fold((acc, x) => acc * x, 1, [1, 2, 3, 4])");
    }

    @Test
    void foldCount() throws Exception {
        fm.assertExec("3", "Fold((acc, x) => acc + 1, 0, [10, 20, 30])");
    }

    @Test
    void foldEmptyList() throws Exception {
        fm.assertExec("0", "Fold((acc, x) => acc + x, 0, [])");
    }

    @Test
    void foldSingleElement() throws Exception {
        fm.assertExec("5", "Fold((acc, x) => acc + x, 0, [5])");
    }

    @Test
    void foldMax() throws Exception {
        fm.assertExec("5", "Fold((acc, x) => If(x > acc, x, acc), 0, [3, 1, 5, 2, 4])");
    }

}
