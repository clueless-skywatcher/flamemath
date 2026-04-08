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

    // --- Distribution (expected future behavior) ---

    @Test
    @Disabled("Not yet implemented")
    public void expandProductOverSum() throws Exception {
        fm.assertExec("x*y + x*z", "Expand(x * (y + z))");
    }

    @Test
    @Disabled("Not yet implemented")
    public void expandSquaredBinomial() throws Exception {
        fm.assertExec("1 + 2*x + x^2", "Expand((x + 1)^2)");
    }

    @Test
    @Disabled("Not yet implemented")
    public void expandNestedProduct() throws Exception {
        fm.assertExec("a*c + a*d + b*c + b*d", "Expand((a + b) * (c + d))");
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
