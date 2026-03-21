package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class SubTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void basicSubtraction() throws Exception {
        fm.assertExec("2", "Sub(5, 3)");
    }

    @Test
    void negativeResult() throws Exception {
        fm.assertExec("-3", "Sub(1, 4)");
    }

    @Test
    void subtractZero() throws Exception {
        fm.assertExec("5", "Sub(5, 0)");
    }

    @Test
    void subtractFromZero() throws Exception {
        fm.assertExec("-5", "Sub(0, 5)");
    }

    @Test
    void subtractSame() throws Exception {
        fm.assertExec("0", "Sub(3, 3)");
    }

    @Test
    void realSubtraction() throws Exception {
        fm.assertExec("1.5", "Sub(2.5, 1.0)");
    }

}
