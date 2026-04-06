package io.flamemath.eval.builtins.general;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class OperandsFuncTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Addition ---

    @Test
    void operandsOfAdd() throws Exception {
        fm.assertExec("[x, y]", "Operands(x + y)");
    }

    @Test
    void operandsOfThreeTermSum() throws Exception {
        fm.assertExec("[x, y, z]", "Operands(x + y + z)");
    }

    // --- Multiplication ---

    @Test
    void operandsOfMul() throws Exception {
        fm.assertExec("[x, y]", "Operands(x * y)");
    }

    // --- Power ---

    @Test
    void operandsOfPow() throws Exception {
        fm.assertExec("[x, 2]", "Operands(x^2)");
    }

    // --- Function calls ---

    @Test
    void operandsOfFunctionCall() throws Exception {
        fm.assertExec("[x]", "Operands(Sin(x))");
    }

    @Test
    void operandsOfNestedFunction() throws Exception {
        fm.assertExec("[Cos(y), Sin(x)]", "Operands(Add(Sin(x), Cos(y)))");
    }

    // --- Atomic expressions return unevaluated ---

    @Test
    void unevaluatedOnSymbol() throws Exception {
        fm.assertExec("Operands(x)", "Operands(x)");
    }

    @Test
    void unevaluatedOnInteger() throws Exception {
        fm.assertExec("Operands(5)", "Operands(5)");
    }

    @Test
    void unevaluatedOnString() throws Exception {
        fm.assertExec("Operands(\"hello\")", "Operands(\"hello\")");
    }
}
