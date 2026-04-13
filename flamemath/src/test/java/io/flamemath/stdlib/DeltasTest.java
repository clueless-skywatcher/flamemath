package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DeltasTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // Deltas computes l[i] - l[i+1] for i in 0..Len(l)-2,
    // i.e. "current minus next" (backward deltas).

    // --- Monotonic sequences ---

    @Test
    void strictlyIncreasing() throws Exception {
        fm.assertExec("[-1, -1, -1]", "Deltas([1, 2, 3, 4])");
    }

    @Test
    void strictlyDecreasing() throws Exception {
        fm.assertExec("[1, 1, 1]", "Deltas([4, 3, 2, 1])");
    }

    @Test
    void nonIncreasingWithRepeats() throws Exception {
        fm.assertExec("[0, 1, 0]", "Deltas([3, 3, 2, 2])");
    }

    // --- Irregular ---

    @Test
    void mixedDeltas() throws Exception {
        fm.assertExec("[-2, -3, -4]", "Deltas([1, 3, 6, 10])");
    }

    @Test
    void negativeValues() throws Exception {
        fm.assertExec("[-3, 5]", "Deltas([-5, -2, -7])");
    }

    // --- Trivial inputs ---

    @Test
    void singleton() throws Exception {
        fm.assertExec("[]", "Deltas([5])");
    }

    @Test
    void twoElements() throws Exception {
        fm.assertExec("[-3]", "Deltas([7, 10])");
    }

    // --- Real-valued ---

    @Test
    void realValues() throws Exception {
        fm.assertExec("[-0.5, -0.5]", "Deltas([1.0, 1.5, 2.0])");
    }

    // --- Composed ---

    @Test
    void deltasFromRange() throws Exception {
        fm.assertExec("[-1, -1, -1, -1]", "Deltas(Range(5))");
    }

    @Test
    void deltasFromVariable() throws Exception {
        fm.assertExec("[1, 1, 1]", "{l = [4, 3, 2, 1]; Deltas(l)}");
    }

    // --- Output length ---

    @Test
    void lengthIsOneLessThanInput() throws Exception {
        fm.assertExec("9", "Len(Deltas(Range(10)))");
    }
}
