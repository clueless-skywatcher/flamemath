package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class MapFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Symbol function over list ---

    @Test
    public void symbolOverList() throws Exception {
        fm.assertExec("[Head(1), Head(2), Head(3)]", "Map(Head, [1, 2, 3])");
    }

    @Test
    public void symbolOverEmptyList() throws Exception {
        fm.assertExec("[]", "Map(Head, [])");
    }

    @Test
    public void symbolOverSingletonList() throws Exception {
        fm.assertExec("[Head(42)]", "Map(Head, [42])");
    }

    // --- Lambda over list ---

    @Test
    public void lambdaOverList() throws Exception {
        fm.assertExec("[2, 3, 4]", "Map((x) => x + 1, [1, 2, 3])");
    }

    @Test
    public void lambdaOverEmptyList() throws Exception {
        fm.assertExec("[]", "Map((x) => x + 1, [])");
    }

    @Test
    public void lambdaOverSingletonList() throws Exception {
        fm.assertExec("[11]", "Map((x) => x + 1, [10])");
    }

    @Test
    public void lambdaMulOverList() throws Exception {
        fm.assertExec("[2, 4, 6]", "Map((x) => x * 2, [1, 2, 3])");
    }

    // --- Non-list second argument ---

    @Test
    public void symbolOverScalar() throws Exception {
        fm.assertExec("Head(42)", "Map(Head, 42)");
    }

    @Test
    public void lambdaOverScalar() throws Exception {
        fm.assertExec("11", "Map((x) => x + 1, 10)");
    }

    // --- Nested lists ---

    @Test
    public void symbolOverNestedList() throws Exception {
        fm.assertExec("[Len([1, 2]), Len([3, 4, 5])]", "Map(Len, [[1, 2], [3, 4, 5]])");
    }

    // --- Variable binding ---

    @Test
    public void lambdaFromVariable() throws Exception {
        fm.assertExec("[2, 3, 4]", "{f = (x) => x + 1}; Map(f, [1, 2, 3])}");
    }

    @Test
    public void listFromVariable() throws Exception {
        fm.assertExec("[2, 4, 6]", "{l = [1, 2, 3]}; Map((x) => x * 2, l)}");
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Map()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Map(Head)"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Map(Head, [1], [2])"));
    }
}
