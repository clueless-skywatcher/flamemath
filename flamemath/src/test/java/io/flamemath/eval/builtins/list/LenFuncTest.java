package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class LenFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void emptyList() throws Exception {
        fm.assertExec("0", "Len([])");
    }

    @Test
    public void singleElement() throws Exception {
        fm.assertExec("1", "Len([42])");
    }

    @Test
    public void multipleElements() throws Exception {
        fm.assertExec("3", "Len([1, 2, 3])");
    }

    @Test
    public void nestedListCountsTopLevel() throws Exception {
        fm.assertExec("2", "Len([[1, 2], [3, 4]])");
    }

    @Test
    public void evaluatedElements() throws Exception {
        fm.assertExec("3", "Len([1 + 2, 3 * 4, x])");
    }

    @Test
    public void listFromVariable() throws Exception {
        fm.assertExec("3", "{l = [10, 20, 30]}; Len(l)}");
    }

    @Test
    public void nonListReturnsZero() throws Exception {
        fm.assertExec("0", "Len(42)");
        fm.assertExec("0", "Len(\"hello\")");
        fm.assertExec("0", "Len(True)");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Len()"));
    }

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Len([1], [2])"));
    }
}
