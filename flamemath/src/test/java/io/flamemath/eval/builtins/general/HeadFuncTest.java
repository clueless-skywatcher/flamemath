package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.eval.FlameArityException;
import org.junit.jupiter.api.Test;

public class HeadFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();
    @Test
    public void integerHead() throws Exception {
        fm.assertExec("Head(4)", "Integer");
    }

    @Test
    public void realHead() throws Exception {
        fm.assertExec("Head(4.4)", "Real");
    }

    @Test
    public void booleanHead() throws Exception {
        fm.assertExec("Head(True)", "Boolean");
        fm.assertExec("Head(False)", "Boolean");
    }

    @Test
    public void stringHead() throws Exception {
        fm.assertExec("Head(\"abc\")", "String");
    }

    @Test
    public void symbolHead() throws Exception {
        fm.assertExec("Head(x)", "Symbol");
        fm.assertExec("Head(Head)", "Symbol");
    }

    @Test
    public void compoundHead() throws Exception {
        fm.assertExec("Head(Sin(x))", "Sin");
        fm.assertExec("Head(Head(x))", "Symbol");
    }

    // --- Arithmetic heads ---

    @Test
    public void addHead() throws Exception {
        fm.assertExec("Head(x + y)", "Add");
    }

    @Test
    public void mulHead() throws Exception {
        fm.assertExec("Head(2*x)", "Mul");
    }

    @Test
    public void powHead() throws Exception {
        fm.assertExec("Head(x^2)", "Pow");
    }

    // --- List head ---

    @Test
    public void listHead() throws Exception {
        fm.assertExec("Head([1, 2, 3])", "List");
    }

    // --- Head of evaluated result ---

    @Test
    public void headOfEvaluatedAdd() throws Exception {
        // 2 + 3 evaluates to 5 (IntegerAtom), so Head is Integer
        fm.assertExec("Head(2 + 3)", "Integer");
    }

    @Test
    public void headOfPartiallyEvaluatedAdd() throws Exception {
        // x + 1 stays as Add(1, x)
        fm.assertExec("Head(x + 1)", "Add");
    }

    // --- Arity errors throw ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Head()"));
    }

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Head(x, y)"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Head(x, y, z)"));
    }
}
