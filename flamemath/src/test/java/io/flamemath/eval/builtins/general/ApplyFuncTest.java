package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

class ApplyFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Apply", new ApplyFunc().name());
    }

    // --- Builtins via symbol ---

    @Test
    void applyAddToList() throws Exception {
        fm.assertExec("6", "Apply(Add, [1, 2, 3])");
    }

    @Test
    void applyMulToList() throws Exception {
        fm.assertExec("24", "Apply(Mul, [1, 2, 3, 4])");
    }

    @Test
    void applySortToSingleElementList() throws Exception {
        fm.assertExec("[1, 2, 3]", "Apply(Sort, [[3, 1, 2]])");
    }

    @Test
    void applyLenToList() throws Exception {
        fm.assertExec("3", "Apply(Len, [[1, 2, 3]])");
    }

    // --- Lambda ---

    @Test
    void applyLambdaToList() throws Exception {
        fm.assertExec("7", "Apply((x, y) => x + y, [3, 4])");
    }

    @Test
    void applyLambdaSingleArg() throws Exception {
        fm.assertExec("9", "Apply((x) => x * x, [3])");
    }

    @Test
    void applyLambdaFromVariable() throws Exception {
        fm.assertExec("5", "{ f = (a, b) => a + b; Apply(f, [2, 3]) }");
    }

    // --- Overloaded (Seq) dispatch ---

    @Test
    void applyOverloadedExactMatch() throws Exception {
        fm.assertExec("1", """
        {
            f = {
                (x) => 1;
                (x, y) => 2
            };
            Apply(f, [42])
        }
        """);
    }

    @Test
    void applyOverloadedTwoArgs() throws Exception {
        fm.assertExec("2", """
        {
            f = {
                (x) => 1;
                (x, y) => 2
            };
            Apply(f, [1, 2])
        }
        """);
    }

    // --- Variadic lambda ---

    @Test
    void applyVariadicLambda() throws Exception {
        fm.assertExec("[2, 3]", "{ f = (x, ...rest) => rest; Apply(f, [1, 2, 3]) }");
    }

    @Test
    void applyOverloadedVariadicFallback() throws Exception {
        fm.assertExec("\"exact\"", """
        {
            f = {
                (x) => "exact";
                (x, ...rest) => "variadic"
            };
            Apply(f, [42])
        }
        """);
    }

    @Test
    void applyOverloadedVariadicUsed() throws Exception {
        fm.assertExec("\"variadic\"", """
        {
            f = {
                (x) => "exact";
                (x, ...rest) => "variadic"
            };
            Apply(f, [1, 2, 3])
        }
        """);
    }

    // --- List from variable ---

    @Test
    void applyWithListVariable() throws Exception {
        fm.assertExec("6", "{ l = [1, 2, 3]; Apply(Add, l) }");
    }

    // --- Empty list ---

    @Test
    void applyToEmptyList() throws Exception {
        fm.assertExec("0", "Apply(Add, [])");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void applyNonListReturnsRaw() throws Exception {
        fm.assertExec("Apply(Add, x)", "Apply(Add, x)");
    }

    @Test
    void applyIntegerReturnsRaw() throws Exception {
        fm.assertExec("Apply(Add, 42)", "Apply(Add, 42)");
    }

    @Test
    void applyNonFunctionReturnsRaw() throws Exception {
        fm.assertExec("Apply(42, [1, 2])", "Apply(42, [1, 2])");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Apply()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Apply(Add)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Apply(Add, [1], [2])"));
    }
}
