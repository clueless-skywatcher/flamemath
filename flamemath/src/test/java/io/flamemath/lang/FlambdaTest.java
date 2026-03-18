package io.flamemath.lang;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import io.flamemath.expr.Expr;

import static org.junit.jupiter.api.Assertions.*;

class FlambdaTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Single lambda ---

    @Test
    void singleParamLambda() throws Exception {
        fm.assertExec("9", "F = (x) => x + 1; F(8)");
    }

    @Test
    void twoParamLambda() throws Exception {
        fm.assertExec("5", "F = (a, b) => a + b; F(2, 3)");
    }

    @Test
    void zeroParamLambda() throws Exception {
        fm.assertExec("42", "AnswerToLife = () => 42; AnswerToLife()");
    }

    // --- Block body ---

    @Test
    void blockBody() throws Exception {
        fm.assertExec("11", "F = (x) => { y = x + 1; y + x }; F(5)");
    }

    // --- Scoping ---

    @Test
    void lambdaDoesNotLeakParams() throws Exception {
        fm.assertExec("x", "F = (x) => x + 1; F(5); x");
    }

    @Test
    void lambdaSeesOuterScope() throws Exception {
        fm.assertExec("15", "a = 10; F = (x) => x + a; F(5)");
    }

    @Test
    void blockVarsDoNotLeak() throws Exception {
        fm.assertExec("y", "F = (x) => { y = x * 2; y }; F(5); y");
    }

    // --- Overloading ---

    @Test
    void overloadByArity() throws Exception {
        fm.assertExec("5", "F = { (a, b) => a + b; (a) => a * 2 }; F(2, 3)");
    }

    @Test
    void overloadSelectsCorrectClause() throws Exception {
        fm.assertExec("10", "F = { (a, b) => a + b; (a) => a * 2 }; F(5)");
    }

    @Test
    void overloadArityMismatchThrows() {
        assertThrows(Exception.class, () -> fm.execute("F = { (a, b) => a + b; (a) => a * 2 }; F(1, 2, 3)"));
    }

    // --- Nested lambdas ---

    @Test
    void lambdaReturnsLambda() throws Exception {
        fm.assertExec("7", "F = (x) => (y) => x + y; G = F(3); G(4)");
    }

    // --- Lambda as value ---

    @Test
    void lambdaEvaluatesToItself() throws Exception {
        Expr result = fm.execute("F = (x) => x + 1");
        assertEquals("Lambda", result.head());
    }

    // --- Args evaluated in caller scope ---

    @Test
    void argsEvaluatedBeforeBinding() throws Exception {
        fm.assertExec("3", "a = 1; F = (x) => x + 1; F(a + 1)");
    }

    // --- Lambda called multiple times ---

    @Test
    void lambdaCalledTwice() throws Exception {
        fm.assertExec("10", "F = (x) => x * 2; F(3); F(2) + F(1) + F(2)");
    }

    @Test
    void lambdaCalledWithDifferentArgs() throws Exception {
        fm.assertExec("25", "Square = (x) => x ^ 2; Square(5)");
    }

    // --- Overloading with blocks ---

    @Test
    void overloadWithBlockBodies() throws Exception {
        fm.assertExec("11", "F = { (a, b) => { c = a + b; c + 1 }; (a) => a * 3 }; F(4, 6)");
    }

    @Test
    void overloadSingleArgWithBlock() throws Exception {
        fm.assertExec("15", "F = { (a, b) => a + b; (a) => { b = a * 3; b } }; F(5)");
    }

    // --- Nested closures ---

    @Test
    void nestedClosureCapturesCorrectScope() throws Exception {
        fm.assertExec("12", "Adder = (x) => (y) => x + y; Add5 = Adder(5); Add5(7)");
    }

    @Test
    void nestedClosuresIndependent() throws Exception {
        fm.assertExec("10", "Adder = (x) => (y) => x + y; Add3 = Adder(3); Add7 = Adder(7); Add3(3) + Add7(-3)");
    }

    // --- Lambda with symbolic args ---

    @Test
    void lambdaWithSymbolicArg() throws Exception {
        fm.assertExec("Add(1, x)", "F = (a) => a + 1; F(x)");
    }

    // --- Param shadowing ---

    @Test
    void paramShadowsOuterVar() throws Exception {
        fm.assertExec("3", "x = 10; F = (x) => x + 1; F(2)");
    }

    @Test
    void outerVarUnchangedAfterShadowing() throws Exception {
        fm.assertExec("10", "x = 10; F = (x) => x + 1; F(2); x");
    }

    // --- Three-arg overload ---

    @Test
    void threeClauseOverload() throws Exception {
        fm.assertExec("6", "F = { (a, b, c) => a + b + c; (a, b) => a * b; (a) => a }; F(1, 2, 3)");
    }

    @Test
    void threeClauseOverloadMiddle() throws Exception {
        fm.assertExec("6", "F = { (a, b, c) => a + b + c; (a, b) => a * b; (a) => a }; F(2, 3)");
    }

    @Test
    void threeClauseOverloadSingle() throws Exception {
        fm.assertExec("7", "F = { (a, b, c) => a + b + c; (a, b) => a * b; (a) => a }; F(7)");
    }
}
