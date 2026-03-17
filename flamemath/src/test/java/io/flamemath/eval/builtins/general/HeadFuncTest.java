package io.flamemath.eval.builtins.general;

import static io.flamemath.FlameTestingUtils.assertExec;
import static io.flamemath.FlameTestingUtils.execute;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.eval.FlameArityException;
import org.junit.jupiter.api.Test;

public class HeadFuncTest {
    @Test
    public void integerHead() throws Exception {
        assertExec("Head(4)", "Integer");
    }

    @Test
    public void realHead() throws Exception {
        assertExec("Head(4.4)", "Real");
    }

    @Test
    public void booleanHead() throws Exception {
        assertExec("Head(True)", "Boolean");
        assertExec("Head(False)", "Boolean");
    }

    @Test
    public void stringHead() throws Exception {
        assertExec("Head(\"abc\")", "String");
    }

    @Test
    public void symbolHead() throws Exception {
        assertExec("Head(x)", "Symbol");
        assertExec("Head(Head)", "Symbol");
    }

    @Test
    public void compoundHead() throws Exception {
        assertExec("Head(Sin(x))", "Sin");
        assertExec("Head(Head(x))", "Symbol");
    }

    // --- Arithmetic heads ---

    @Test
    public void addHead() throws Exception {
        assertExec("Head(x + y)", "Add");
    }

    @Test
    public void mulHead() throws Exception {
        assertExec("Head(2*x)", "Mul");
    }

    @Test
    public void powHead() throws Exception {
        assertExec("Head(x^2)", "Pow");
    }

    // --- List head ---

    @Test
    public void listHead() throws Exception {
        assertExec("Head([1, 2, 3])", "List");
    }

    // --- Head of evaluated result ---

    @Test
    public void headOfEvaluatedAdd() throws Exception {
        // 2 + 3 evaluates to 5 (IntegerAtom), so Head is Integer
        assertExec("Head(2 + 3)", "Integer");
    }

    @Test
    public void headOfPartiallyEvaluatedAdd() throws Exception {
        // x + 1 stays as Add(1, x)
        assertExec("Head(x + 1)", "Add");
    }

    // --- Arity errors throw ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> execute("Head()"));
    }

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> execute("Head(x, y)"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> execute("Head(x, y, z)"));
    }
}
