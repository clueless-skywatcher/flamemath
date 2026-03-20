package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class AtFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();
    
    @Test
    public void operatorForm() throws Exception {
        fm.assertExec("5", "{l = [11, -1, 5, 3, x]}; l[2]}");
        fm.assertExec("5", "[11, -1, 5, 3, x][2]");
    }

    @Test
    public void functionForm() throws Exception {
        fm.assertExec("5", "{l = [11, -1, 5, 3, x]}; At(l, 2)}");
        fm.assertExec("5", "At([11, -1, 5, 3, x], 2)");
    }

    @Test
    public void firstAndLastElement() throws Exception {
        fm.assertExec("1", "[1, 2, 3][0]");
        fm.assertExec("3", "[1, 2, 3][2]");
    }

    @Test
    public void negativeIndexReturnsFromBack() throws Exception {
        fm.assertExec("3", "[1, 2, 3][-1]");
        fm.assertExec("1", "[1, 2, 3][-3]");
    }

    @Test
    public void outOfBoundsThrows() throws Exception {
        assertThrows(Exception.class, () -> fm.execute("[1, 2, 3][3]"));
        assertThrows(Exception.class, () -> fm.execute("[1, 2, 3][-4]"));
    }

    @Test
    public void nonListArgsUnevaluated() throws Exception {
        fm.assertExec("x[0]", "x[0]");
    }

    @Test
    public void evaluatedIndex() throws Exception {
        fm.assertExec("30", "[10, 20, 30][1 + 1]");
    }

    @Test
    public void evaluatedElements() throws Exception {
        fm.assertExec("30", "[1 * 10, 2 * 10, 3 * 10][1 + 1]");
        fm.assertExec("5*x", "[x, 2*x + x, 3*x + 2*x][1 + 1]");
    }

    @Test
    public void nestedListIndexing() throws Exception {
        fm.assertExec("[3, 4]", "[[1, 2], [3, 4]][1]");
        fm.assertExec("3", "[[1, 2], [3, 4]][1][0]");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("At()"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("At(x, y, z)"));
    }
}
