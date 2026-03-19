package io.flamemath.eval.builtins.general;

import io.flamemath.FlameTestingUtils;
import io.flamemath.eval.builtins.construct.IfFunc;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("If", new IfFunc().name());
    }

    // --- True branch ---

    @Test
    void trueBranchInteger() throws Exception {
        fm.assertExec("1", "If(True, 1, 2)");
    }

    @Test
    void trueBranchSymbol() throws Exception {
        fm.assertExec("x", "If(True, x, y)");
    }

    @Test
    void trueBranchExpression() throws Exception {
        fm.assertExec("3", "If(True, 1 + 2, 4 + 5)");
    }

    // --- False branch ---

    @Test
    void falseBranchInteger() throws Exception {
        fm.assertExec("2", "If(False, 1, 2)");
    }

    @Test
    void falseBranchSymbol() throws Exception {
        fm.assertExec("y", "If(False, x, y)");
    }

    @Test
    void falseBranchExpression() throws Exception {
        fm.assertExec("9", "If(False, 1 + 2, 4 + 5)");
    }

    // --- Condition is an expression ---

    @Test
    void conditionFromVariable() throws Exception {
        fm.assertExec("1", "c = True; If(c, 1, 0)");
    }

    @Test
    void conditionFromVariableFalse() throws Exception {
        fm.assertExec("0", "c = False; If(c, 1, 0)");
    }

    // --- Non-boolean condition treated as false ---

    @Test
    void integerConditionIsFalse() throws Exception {
        fm.assertExec("2", "If(1, 1, 2)");
    }

    @Test
    void symbolConditionIsFalse() throws Exception {
        fm.assertExec("2", "If(x, 1, 2)");
    }

    @Test
    void stringConditionIsFalse() throws Exception {
        fm.assertExec("2", "If(\"hello\", 1, 2)");
    }

    // --- HoldAll: only the chosen branch is evaluated ---

    @Test
    void trueBranchOnlyEvaluated() throws Exception {
        // If Set in the false branch were evaluated, x would be 99
        fm.assertExec("1", "If(True, 1, x = 99); If(True, 1, x)");
    }

    @Test
    void falseBranchOnlyEvaluated() throws Exception {
        // If Set in the true branch were evaluated, x would be 99
        fm.assertExec("2", "If(False, x = 99, 2); If(False, x, 2)");
    }

    // --- Nested If ---

    @Test
    void nestedIfInTrueBranch() throws Exception {
        fm.assertExec("1", "If(True, If(True, 1, 2), 3)");
    }

    @Test
    void nestedIfInFalseBranch() throws Exception {
        fm.assertExec("4", "If(False, 3, If(False, 1, 4))");
    }

    // --- If with variable state ---

    @Test
    void ifWithAssignment() throws Exception {
        fm.assertExec("10", "x = 10; If(True, x, 0)");
    }

    @Test
    void ifResultUsedInExpression() throws Exception {
        fm.assertExec("6", "If(True, 3, 5) + If(False, 1, 3)");
    }

    // --- If with lambda ---

    @Test
    void ifInsideLambda() throws Exception {
        fm.assertExec("1", "Abs = (x) => If(True, x, 0 - x); Abs(1)");
    }

    // --- Two-arg If (no else branch) ---

    @Test
    void twoArgTrueReturnsValue() throws Exception {
        fm.assertExec("42", "If(True, 42)");
    }

    @Test
    void twoArgFalseReturnsNull() throws Exception {
        fm.assertExec("Null", "If(False, 42)");
    }

    @Test
    void twoArgTrueExpression() throws Exception {
        fm.assertExec("5", "If(True, 2 + 3)");
    }

    @Test
    void twoArgFalseDoesNotEvaluateBody() throws Exception {
        // If the body were evaluated, x would be 99
        fm.assertExec("1", "{ x = 1; If(False, x = 99); x }");
    }

    @Test
    void twoArgWithComparison() throws Exception {
        fm.assertExec("10", "{ x = 5; If(x > 3, x * 2) }");
    }

    @Test
    void twoArgFalseComparisonReturnsNull() throws Exception {
        fm.assertExec("Null", "{ x = 5; If(x > 10, x * 2) }");
    }

    @Test
    void twoArgNestedInSeq() throws Exception {
        fm.assertExec("1", "{ x = 1; If(False, x = 99); x }");
    }

    @Test
    void twoArgInsideWhile() throws Exception {
        fm.assertExec("5", "{ i = 0; While(i < 5, { i = i + 1; If(i == 3, PrintLn(i)) }); i }");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("If()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("If(True)"));
    }

    @Test
    void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("If(True, 1, 2, 3)"));
    }
}
