package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class IntegerPartitionsTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void partitionsOfZero() throws Exception {
        fm.assertExec("[[0]]", "IntegerPartitions(0)");
    }

    @Test
    void partitionsOfOne() throws Exception {
        fm.assertExec("[[1]]", "IntegerPartitions(1)");
    }

    @Test
    void partitionsOfTwo() throws Exception {
        fm.assertExec("[[1, 1], [2]]", "IntegerPartitions(2)");
    }

    @Test
    void partitionsOfThree() throws Exception {
        fm.assertExec("[[1, 1, 1], [1, 2], [3]]", "IntegerPartitions(3)");
    }

    // --- Standard values ---

    @Test
    void partitionsOfFour() throws Exception {
        fm.assertExec("[[1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2], [4]]", "IntegerPartitions(4)");
    }

    @Test
    void partitionsOfFive() throws Exception {
        fm.assertExec("[[1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 4], [2, 3], [5]]", "IntegerPartitions(5)");
    }

    @Test
    void partitionsOfSix() throws Exception {
        fm.assertExec("[[1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 2], [1, 1, 1, 3], [1, 1, 2, 2], [1, 1, 4], [1, 2, 3], [1, 5], [2, 2, 2], [2, 4], [3, 3], [6]]",
            "IntegerPartitions(6)");
    }

    // --- Partition counts (p(n) = number of partitions) ---

    @Test
    void countPartitionsOfSeven() throws Exception {
        fm.assertExec("15", "Len(IntegerPartitions(7))");
    }

    @Test
    void countPartitionsOfEight() throws Exception {
        fm.assertExec("22", "Len(IntegerPartitions(8))");
    }

    @Test
    void countPartitionsOfTen() throws Exception {
        fm.assertExec("42", "Len(IntegerPartitions(10))");
    }

    // --- Each partition sums to n ---

    @Test
    void allPartitionsSumToN() throws Exception {
        fm.assertExec("True", "{ ps = IntegerPartitions(8); result = True; For(p, ps, { If(Sum(p) != 8, result = False) }); result }");
    }

    // --- Parts are in non-decreasing order ---

    @Test
    void partsAreNonDecreasing() throws Exception {
        fm.assertExec("True", "{ ps = IntegerPartitions(7); result = True; For(p, ps, { For(j, Range(1, Len(p)), { If(p[j] < p[j - 1], result = False) }) }); result }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("IntegerPartitions(x)", "IntegerPartitions(x)");
    }

    // --- Composed ---

    @Test
    void partitionsFromVariable() throws Exception {
        fm.assertExec("[[1, 1, 1], [1, 2], [3]]", "{ n = 3; IntegerPartitions(n) }");
    }
}
