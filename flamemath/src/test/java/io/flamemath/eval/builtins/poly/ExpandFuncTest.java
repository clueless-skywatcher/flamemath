package io.flamemath.eval.builtins.poly;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExpandFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Passthrough (current behavior) ---

    @Test
    public void expandConstant() throws Exception {
        fm.assertExec("5", "Expand(5)");
    }

    @Test
    public void expandSymbol() throws Exception {
        fm.assertExec("x", "Expand(x)");
    }

    @Test
    public void expandSum() throws Exception {
        fm.assertExec("1 + x", "Expand(x + 1)");
    }

    @Test
    public void expandProductOverSum() throws Exception {
        fm.assertExec("x*y + x*z", "Expand(x * (y + z))");
    }

    @Test
    public void expandSquaredBinomial() throws Exception {
        fm.assertExec("1 + 2*x + x^2", "Expand((x + 1)^2)");
    }

    @Test
    public void expandNestedProduct() throws Exception {
        fm.assertExec("a*c + a*d + b*c + b*d", "Expand((a + b) * (c + d))");
    }

    // --- Binomial expansion ---

    @Test
    public void expandBinomialCubed() throws Exception {
        fm.assertExec("x^3 + 3*x^2 + 3*x + 1", "Expand((x + 1)^3)");
    }

    @Test
    public void expandBinomialFourth() throws Exception {
        fm.assertExec("x^4 + 4*x^3 + 6*x^2 + 4*x + 1", "Expand((x + 1)^4)");
    }

    @Test
    public void expandBinomialNegativeConstant() throws Exception {
        fm.assertExec("x^2 - 2*x + 1", "Expand((x - 1)^2)");
    }

    @Test
    public void expandBinomialNegativeConstantCubed() throws Exception {
        fm.assertExec("x^3 - 3*x^2 + 3*x - 1", "Expand((x - 1)^3)");
    }

    @Test
    public void expandBinomialWithCoefficients() throws Exception {
        fm.assertExec("4*x^2 + 12*x + 9", "Expand((2*x + 3)^2)");
    }

    @Test
    public void expandBinomialTwoVarsFourth() throws Exception {
        fm.assertExec("a^4 + 4*a^3*b + 6*a^2*b^2 + 4*a*b^3 + b^4", "Expand((a + b)^4)");
    }

    @Test
    public void expandBinomialFirstPower() throws Exception {
        fm.assertExec("x + 1", "Expand((x + 1)^1)");
    }

    // --- Multinomial expansion ---

    @Test
    public void expandTrinomialSquared() throws Exception {
        fm.assertExec("x^2 + 2*x*y + 2*x*z + y^2 + 2*y*z + z^2", "Expand((x + y + z)^2)");
    }

    @Test
    public void expandTrinomialCubed() throws Exception {
        fm.assertExec(
            "x^3 + 3*x^2*y + 3*x^2*z + 3*x*y^2 + 6*x*y*z + 3*x*z^2 + y^3 + 3*y^2*z + 3*y*z^2 + z^3",
            "Expand((x + y + z)^3)"
        );
    }

    @Test
    public void expandFourTermSquared() throws Exception {
        fm.assertExec(
            "a^2 + 2*a*b + 2*a*c + 2*a*d + b^2 + 2*b*c + 2*b*d + c^2 + 2*c*d + d^2",
            "Expand((a + b + c + d)^2)"
        );
    }

    // --- Mixed (distribution + multinomial) ---

    @Test
    public void expandSquaredTimesLinear() throws Exception {
        fm.assertExec("a^2*c + a^2*d + 2*a*b*c + 2*a*b*d + b^2*c + b^2*d", "Expand((a + b)^2 * (c + d))");
    }

    @Test
    public void expandSquaredTimesLinearSameVar() throws Exception {
        fm.assertExec("x^3 + 4*x^2 + 5*x + 2", "Expand((x + 1)^2 * (x + 2))");
    }

    @Test
    public void expandScalarTimesCubed() throws Exception {
        fm.assertExec("2*x^3 + 6*x^2 + 6*x + 2", "Expand(2 * (x + 1)^3)");
    }

    // --- Edge cases ---

    @Test
    public void expandAlreadyExpanded() throws Exception {
        fm.assertExec("x^2 + 2*x + 1", "Expand(x^2 + 2*x + 1)");
    }

    @Test
    public void expandSymbolicExponent() throws Exception {
        fm.assertExec("(x + 1)^n", "Expand((x + 1)^n)");
    }

    @Test
    public void expandNegativeExponent() throws Exception {
        fm.assertExec("(x + 1)^(-2)", "Expand((x + 1)^(-2))");
    }

    @Test
    public void expandPureNumeric() throws Exception {
        fm.assertExec("45", "Expand((2 + 3) * (4 + 5))");
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Expand()"));
    }

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Expand(x, y)"));
    }
}
