package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

public class RangeFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void singleArgRange() throws Exception {
        fm.assertExec("[0, 1, 2, 3]", "Range(4)");
    }

    @Test
    public void negativeOrZeroSingleArgRangeIsEmpty() throws Exception {
        fm.assertExec("[]", "Range(-5)");
        fm.assertExec("[]", "Range(0)");
    }

    @Test
    public void doubleArgAscendingRange() throws Exception {
        fm.assertExec("[2, 3, 4, 5, 6]", "Range(2, 7)");
        fm.assertExec("[-5, -4, -3, -2, -1, 0, 1]", "Range(-5, 2)");
    }

    @Test
    public void doubleArgSameRangeIsEmpty() throws Exception {
        fm.assertExec("[]", "Range(2, 2)");
    }

    @Test
    public void doubleArgConsecutiveNumbers() throws Exception {
        fm.assertExec("[6]", "Range(6, 7)"); // Hehe 67
    }

    @Test
    public void doubleArgDescendingIsEmpty() throws Exception {
        fm.assertExec("[]", "Range(5, 2)");
    }

    // --- Triple arg (start, end, step) ---

    @Test
    public void tripleArgAscending() throws Exception {
        fm.assertExec("[0, 2, 4, 6, 8]", "Range(0, 10, 2)");
    }

    @Test
    public void tripleArgStepNotLandingOnEnd() throws Exception {
        fm.assertExec("[1, 4, 7]", "Range(1, 10, 3)");
    }

    @Test
    public void tripleArgDescending() throws Exception {
        fm.assertExec("[10, 8, 6, 4, 2]", "Range(10, 0, -2)");
    }

    @Test
    public void tripleArgDescendingByOne() throws Exception {
        fm.assertExec("[5, 4, 3]", "Range(5, 2, -1)");
    }

    @Test
    public void tripleArgNegativeRange() throws Exception {
        fm.assertExec("[-10, -8, -6]", "Range(-10, -5, 2)");
    }

    @Test
    public void tripleArgWrongDirectionIsEmpty() throws Exception {
        fm.assertExec("[]", "Range(0, 10, -1)");
        fm.assertExec("[]", "Range(10, 0, 1)");
    }

    @Test
    public void tripleArgZeroStepThrows() {
        assertThrows(Exception.class, () -> fm.execute("Range(0, 10, 0)"));
    }

    // --- Single arg edge cases ---

    @Test
    public void singleArgOne() throws Exception {
        fm.assertExec("[0]", "Range(1)");
    }

    @Test
    public void singleArgLarge() throws Exception {
        fm.assertExec("10", "Len(Range(10))");
    }

    // --- Used with other functions ---

    @Test
    public void mapOverRange() throws Exception {
        fm.assertExec("[0, 2, 4, 6]", "Map((x) => x * 2, Range(4))");
    }

    @Test
    public void lenOfRange() throws Exception {
        fm.assertExec("5", "Len(Range(2, 7))");
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Range()"));
    }

    @Test
    public void moreThanThreeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Range(0, 1, 2, 3)"));
    }

}
