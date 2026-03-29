package io.flamemath.eval.builtins.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;

class SqrtFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Sqrt", new SqrtFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sqrt()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sqrt(1, 2)"));
    }

    // --- Perfect squares → exact integer ---

    @Test
    void sqrtZero() throws Exception {
        fm.assertExec("0", "Sqrt(0)");
    }

    @Test
    void sqrtOne() throws Exception {
        fm.assertExec("1", "Sqrt(1)");
    }

    @Test
    void sqrtFour() throws Exception {
        fm.assertExec("2", "Sqrt(4)");
    }

    @Test
    void sqrtNine() throws Exception {
        fm.assertExec("3", "Sqrt(9)");
    }

    @Test
    void sqrtSixteen() throws Exception {
        fm.assertExec("4", "Sqrt(16)");
    }

    @Test
    void sqrtTwentyFive() throws Exception {
        fm.assertExec("5", "Sqrt(25)");
    }

    @Test
    void sqrtHundred() throws Exception {
        fm.assertExec("10", "Sqrt(100)");
    }

    @Test
    void sqrtLargePerfectSquare() throws Exception {
        fm.assertExec("100", "Sqrt(10000)");
    }

    // --- Non-perfect square integers → symbolic Pow(n, (1/2)) ---

    @Test
    void sqrtTwo() throws Exception {
        fm.assertExec("Pow(2, (1/2))", "Sqrt(2)");
    }

    @Test
    void sqrtThree() throws Exception {
        fm.assertExec("Pow(3, (1/2))", "Sqrt(3)");
    }

    @Test
    void sqrtFive() throws Exception {
        fm.assertExec("Pow(5, (1/2))", "Sqrt(5)");
    }

    @Test
    void sqrtSeven() throws Exception {
        fm.assertExec("Pow(7, (1/2))", "Sqrt(7)");
    }

    @Test
    void sqrtTen() throws Exception {
        fm.assertExec("Pow(10, (1/2))", "Sqrt(10)");
    }

    // --- Non-perfect squares with perfect-square factors → extract ---

    @Test
    void sqrtEight() throws Exception {
        fm.assertExec("Mul(2, Pow(2, (1/2)))", "Sqrt(8)");
    }

    @Test
    void sqrtTwelve() throws Exception {
        fm.assertExec("Mul(2, Pow(3, (1/2)))", "Sqrt(12)");
    }

    @Test
    void sqrtEighteen() throws Exception {
        fm.assertExec("Mul(3, Pow(2, (1/2)))", "Sqrt(18)");
    }

    @Test
    void sqrtFortyFive() throws Exception {
        fm.assertExec("Mul(3, Pow(5, (1/2)))", "Sqrt(45)");
    }

    @Test
    void sqrtSeventyTwo() throws Exception {
        fm.assertExec("Mul(6, Pow(2, (1/2)))", "Sqrt(72)");
    }

    // --- Sqrt of product → extract numeric perfect-square factors ---

    @Test
    void sqrtFourTimesX() throws Exception {
        fm.assertExec("Mul(2, Sqrt(x))", "Sqrt(4 * x)");
    }

    @Test
    void sqrtNineTimesX() throws Exception {
        fm.assertExec("Mul(3, Sqrt(x))", "Sqrt(9 * x)");
    }

    @Test
    void sqrtFourTimesXSquared() throws Exception {
        fm.assertExec("Mul(2, Sqrt(Pow(x, 2)))", "Sqrt(4 * x^2)");
    }

    @Test
    void sqrtEightTimesX() throws Exception {
        fm.assertExec("Mul(2, Sqrt(Mul(2, x)))", "Sqrt(8 * x)");
    }

    // --- Sqrt(n) * Sqrt(n) → n (via power grouping) ---

    @Test
    void sqrtTimesItself() throws Exception {
        fm.assertExec("2", "Sqrt(2) * Sqrt(2)");
    }

    @Test
    void sqrtThreeTimesItself() throws Exception {
        fm.assertExec("3", "Sqrt(3) * Sqrt(3)");
    }

    // --- Real → compute directly ---

    @Test
    void sqrtRealFour() throws Exception {
        assertEquals(new IntegerAtom(2), fm.execute("Sqrt(4.0)"));
    }

    @Test
    void sqrtRealTwo() throws Exception {
        var result = (RealAtom) fm.execute("Sqrt(2.0)");
        assertEquals(Math.sqrt(2.0), result.value(), 1e-10);
    }

    @Test
    void sqrtRealHalf() throws Exception {
        var result = (RealAtom) fm.execute("Sqrt(0.5)");
        assertEquals(Math.sqrt(0.5), result.value(), 1e-10);
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void sqrtSymbol() throws Exception {
        fm.assertExec("Sqrt(x)", "Sqrt(x)");
    }

    @Test
    void sqrtExpression() throws Exception {
        fm.assertExec("Sqrt(Add(1, x))", "Sqrt(1 + x)");
    }
}
