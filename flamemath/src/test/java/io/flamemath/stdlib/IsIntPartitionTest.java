package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class IsIntPartitionTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // IsIntPartition(l, n): true iff l is a non-increasing list of
    // non-negative integers summing to n.

    // --- Valid partitions ---

    @Test
    void singleton() throws Exception {
        fm.assertExec("True", "IsIntPartition([5], 5)");
    }

    @Test
    void strictlyDecreasingPartition() throws Exception {
        fm.assertExec("True", "IsIntPartition([3, 2, 1], 6)");
    }

    @Test
    void partitionWithRepeats() throws Exception {
        fm.assertExec("True", "IsIntPartition([3, 3, 2, 2], 10)");
    }

    @Test
    void partitionOfOne() throws Exception {
        fm.assertExec("True", "IsIntPartition([1], 1)");
    }

    @Test
    void longPartition() throws Exception {
        fm.assertExec("True", "IsIntPartition([4, 3, 2, 2, 1], 12)");
    }

    // --- Wrong sum ---

    @Test
    void wrongSum() throws Exception {
        fm.assertExec("False", "IsIntPartition([3, 2, 1], 7)");
    }

    @Test
    void zeroTargetMismatch() throws Exception {
        fm.assertExec("False", "IsIntPartition([1, 1], 0)");
    }

    // --- Wrong ordering ---

    @Test
    void increasingRejected() throws Exception {
        fm.assertExec("False", "IsIntPartition([1, 2, 3], 6)");
    }

    @Test
    void unsortedRejected() throws Exception {
        fm.assertExec("False", "IsIntPartition([2, 1, 3], 6)");
    }

    // --- Bad argument types ---

    @Test
    void nonIntegerTargetRejected() throws Exception {
        fm.assertExec("False", "IsIntPartition([3, 2, 1], 6.0)");
    }

    @Test
    void nonListFirstArgRejected() throws Exception {
        fm.assertExec("False", "IsIntPartition(6, 6)");
    }

    @Test
    void nestedListRejected() throws Exception {
        fm.assertExec("False", "IsIntPartition([[3, 2, 1]], 6)");
    }

    // --- Integration with IntegerPartitions ---

    @Test
    void allIntegerPartitionsOfFiveValidate() throws Exception {
        // Reverse each partition so it's non-increasing (IntegerPartitions
        // returns non-decreasing order).
        fm.assertExec(
            "True",
            "{ ps = IntegerPartitions(5); ok = True; "
            + "For(p, ps, { If(!IsIntPartition(Reverse(p), 5), ok = False) }); "
            + "ok }"
        );
    }

    // --- Composed ---

    @Test
    void fromVariables() throws Exception {
        fm.assertExec("True", "{l = [4, 2, 1]; n = 7; IsIntPartition(l, n)}");
    }
}
