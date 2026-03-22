package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

class JoinFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Join", new JoinFunc().name());
    }

    // --- Two lists ---

    @Test
    void joinTwoLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Join([1, 2], [3, 4])");
    }

    @Test
    void joinEmptyAndNonEmpty() throws Exception {
        fm.assertExec("[1, 2]", "Join([], [1, 2])");
    }

    @Test
    void joinNonEmptyAndEmpty() throws Exception {
        fm.assertExec("[1, 2]", "Join([1, 2], [])");
    }

    @Test
    void joinTwoEmptyLists() throws Exception {
        fm.assertExec("[]", "Join([], [])");
    }

    // --- Three or more lists ---

    @Test
    void joinThreeLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5, 6]", "Join([1, 2], [3, 4], [5, 6])");
    }

    @Test
    void joinFourLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Join([1], [2], [3], [4])");
    }

    @Test
    void joinWithEmptyInMiddle() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Join([1, 2], [], [3, 4])");
    }

    // --- Mixed element types ---

    @Test
    void joinMixedTypes() throws Exception {
        fm.assertExec("[1, \"a\", True]", "Join([1], [\"a\"], [True])");
    }

    @Test
    void joinNestedLists() throws Exception {
        fm.assertExec("[[1, 2], [3, 4]]", "Join([[1, 2]], [[3, 4]])");
    }

    // --- From variables ---

    @Test
    void joinFromVariables() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "{ a = [1, 2]; b = [3, 4]; Join(a, b) }");
    }

    // --- Non-mutating ---

    @Test
    void joinDoesNotMutateOriginal() throws Exception {
        fm.assertExec("[1, 2]", "{ a = [1, 2]; Join(a, [3, 4]); a }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void joinNonListReturnsRaw() throws Exception {
        fm.assertExec("Join(x, [1, 2])", "Join(x, [1, 2])");
    }

    @Test
    void joinSecondNonListReturnsRaw() throws Exception {
        fm.assertExec("Join([1, 2], x)", "Join([1, 2], x)");
    }

    @Test
    void joinIntegerReturnsRaw() throws Exception {
        fm.assertExec("Join(42, [1])", "Join(42, [1])");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Join()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Join([1])"));
    }
}
