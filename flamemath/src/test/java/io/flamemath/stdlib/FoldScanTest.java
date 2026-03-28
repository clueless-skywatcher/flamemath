package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FoldScanTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void foldScanSum() throws Exception {
        fm.assertExec("[0, 1, 3, 6, 10]", "FoldScan((acc, x) => acc + x, 0, [1, 2, 3, 4])");
    }

    @Test
    void foldScanProduct() throws Exception {
        fm.assertExec("[1, 1, 2, 6, 24]", "FoldScan((acc, x) => acc * x, 1, [1, 2, 3, 4])");
    }

    @Test
    void foldScanEmptyList() throws Exception {
        fm.assertExec("[0]", "FoldScan((acc, x) => acc + x, 0, [])");
    }

    @Test
    void foldScanSingleElement() throws Exception {
        fm.assertExec("[0, 5]", "FoldScan((acc, x) => acc + x, 0, [5])");
    }

    @Test
    void foldScanSymbolicAdd() throws Exception {
        fm.assertExec("[0, a, a + b, a + b + c, a + b + c + d]", "FoldScan(Add, 0, [a, b, c, d])");
    }

    // @Test
    // void foldScanMax() throws Exception {
    //     fm.assertExec("[0, 3, 3, 5, 5, 5]", "FoldScan((acc, x) => If(x > acc, x, acc), 0, [3, 1, 5, 2, 4])");
    // }

}
