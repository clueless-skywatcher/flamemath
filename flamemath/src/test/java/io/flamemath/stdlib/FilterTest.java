package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FilterTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void filterGreaterThan() throws Exception {
        fm.assertExec("[4, 5]", "Filter((x) => x > 3, [1, 2, 3, 4, 5])");
    }

    @Test
    void filterEven() throws Exception {
        fm.assertExec("[2, 4, 6]", "Filter((x) => Mod(x, 2) == 0, [1, 2, 3, 4, 5, 6])");
    }

    @Test
    void filterNoneMatch() throws Exception {
        fm.assertExec("[]", "Filter((x) => x > 10, [1, 2, 3])");
    }

    @Test
    void filterAllMatch() throws Exception {
        fm.assertExec("[1, 2, 3]", "Filter((x) => x > 0, [1, 2, 3])");
    }

    @Test
    void filterEmptyList() throws Exception {
        fm.assertExec("[]", "Filter((x) => x > 0, [])");
    }

    @Test
    void filterSingleMatch() throws Exception {
        fm.assertExec("[3]", "Filter((x) => x == 3, [1, 2, 3, 4, 5])");
    }

}
