package io.flamemath.eval.builtins.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class ModFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Mod", new ModFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Mod()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Mod(5)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Mod(5, 3, 2)"));
    }

    // --- Division by zero ---

    @Test
    void modByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("Mod(5, 0)"));
    }

    // --- Integer mod ---

    @Test
    void basicMod() throws Exception {
        fm.assertExec("2", "Mod(5, 3)");
    }

    @Test
    void exactlyDivisible() throws Exception {
        fm.assertExec("0", "Mod(6, 3)");
    }

    @Test
    void modOne() throws Exception {
        fm.assertExec("0", "Mod(7, 1)");
    }

    @Test
    void zeroModN() throws Exception {
        fm.assertExec("0", "Mod(0, 5)");
    }

    @Test
    void modBySelf() throws Exception {
        fm.assertExec("0", "Mod(5, 5)");
    }

    @Test
    void largerDivisor() throws Exception {
        fm.assertExec("3", "Mod(3, 7)");
    }

    @Test
    void modTwo() throws Exception {
        fm.assertExec("1", "Mod(7, 2)");
    }

    @Test
    void evenModTwo() throws Exception {
        fm.assertExec("0", "Mod(8, 2)");
    }

    // --- Negative arguments (Mathematica convention: result sign matches divisor) ---

    @Test
    void negativeDividend() throws Exception {
        // Mathematica: Mod[-5, 3] = 1 (not -2)
        fm.assertExec("1", "Mod(-5, 3)");
    }

    @Test
    void negativeDivisor() throws Exception {
        // Mathematica: Mod[5, -3] = -1 (not 2)
        fm.assertExec("-1", "Mod(5, -3)");
    }

    @Test
    void bothNegative() throws Exception {
        // Mathematica: Mod[-5, -3] = -2
        fm.assertExec("-2", "Mod(-5, -3)");
    }

    @Test
    void negativeDividendExactlyDivisible() throws Exception {
        fm.assertExec("0", "Mod(-6, 3)");
    }

    // --- Real mod ---

    @Test
    void realMod() throws Exception {
        fm.assertExec("0.5", "Mod(5.5, 2.5)");
    }

    @Test
    void integerAndRealMix() throws Exception {
        fm.assertExec("0.5", "Mod(5, 1.5)");
    }

    // --- Rational mod ---

    @Test
    void rationalMod() throws Exception {
        // Mod(5/2, 1) = Mod(2.5, 1) = 0.5
        fm.assertExec("1 / 2", "Mod(5 / 2, 1)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicDividend() throws Exception {
        fm.assertExec("Mod(x, 3)", "Mod(x, 3)");
    }

    @Test
    void symbolicDivisor() throws Exception {
        fm.assertExec("Mod(5, y)", "Mod(5, y)");
    }

    @Test
    void bothSymbolic() throws Exception {
        fm.assertExec("Mod(x, y)", "Mod(x, y)");
    }
}
