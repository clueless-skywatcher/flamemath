package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class EuclideanDistanceTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void distance2D() throws Exception {
        fm.assertExec("Sqrt(8)", "EuclideanDistance([1, 2], [3, 4])");
    }

    @Test
    void distance3D() throws Exception {
        fm.assertExec("Sqrt(27)", "EuclideanDistance([1, 2, 3], [4, 5, 6])");
    }

    @Test
    void distance1D() throws Exception {
        fm.assertExec("3", "EuclideanDistance([2], [5])");
    }

    // --- Zero distance ---

    @Test
    void identicalVectors() throws Exception {
        fm.assertExec("0", "EuclideanDistance([1, 2, 3], [1, 2, 3])");
    }

    @Test
    void zeroVectors() throws Exception {
        fm.assertExec("0", "EuclideanDistance([0, 0], [0, 0])");
    }

    // --- Negative coordinates ---

    @Test
    void negativeCoordinates() throws Exception {
        fm.assertExec("Sqrt(52)", "EuclideanDistance([3, 4], [-1, -2])");
    }

    @Test
    void bothNegative() throws Exception {
        fm.assertExec("Sqrt(8)", "EuclideanDistance([-1, -2], [-3, -4])");
    }

    // --- Perfect square result ---

    @Test
    void perfectSquareResult() throws Exception {
        fm.assertExec("5", "EuclideanDistance([0, 0], [3, 4])");
    }

    // --- Symbolic / unevaluated ---

    @Test
    void symbolicFirstArg() throws Exception {
        fm.assertExec("EuclideanDistance(x, [1, 2])", "EuclideanDistance(x, [1, 2])");
    }

    @Test
    void symbolicSecondArg() throws Exception {
        fm.assertExec("EuclideanDistance([1, 2], y)", "EuclideanDistance([1, 2], y)");
    }

    @Test
    void bothSymbolic() throws Exception {
        fm.assertExec("EuclideanDistance(x, y)", "EuclideanDistance(x, y)");
    }

    // --- Lists with symbolic elements ---

    @Test
    void symbolicElements1D() throws Exception {
        fm.assertExec("Sqrt((x - y)^2)", "EuclideanDistance([x], [y])");
    }

    @Test
    void mixedNumericAndSymbolic() throws Exception {
        fm.assertExec("Sqrt(4 + (x - 4)^2)", "EuclideanDistance([1, x], [3, 4])");
    }

    @Test
    void fullySymbolicElements() throws Exception {
        fm.assertExec("Sqrt((x - a)^2 + (y - b)^2)", "EuclideanDistance([x, y], [a, b])");
    }

    // --- Mismatched lengths ---

    @Test
    void mismatchedLengths() throws Exception {
        fm.assertExec("EuclideanDistance([1, 2], [3, 4, 5])", "EuclideanDistance([1, 2], [3, 4, 5])");
    }
}
