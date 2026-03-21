package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class RawFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Raw", new RawFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Raw()"));
    }

    // --- Type ---

    @Test
    void nonSymbolFirstArgThrows() {
        assertThrows(Exception.class, () -> fm.execute("Raw(42, x)"));
    }

    // --- Basic usage ---

    @Test
    void singleArgSymbol() throws Exception {
        fm.assertExec("Foo()", "Raw(Foo)");
    }

    @Test
    void symbolWithOneArg() throws Exception {
        fm.assertExec("Factorial(x)", "Raw(Factorial, x)");
    }

    @Test
    void symbolWithMultipleArgs() throws Exception {
        fm.assertExec("Add(x, y, z)", "Raw(Add, x, y, z)");
    }

    @Test
    void symbolWithNumericArg() throws Exception {
        fm.assertExec("Factorial(5)", "Raw(Factorial, 5)");
    }

    @Test
    void symbolWithStringArg() throws Exception {
        fm.assertExec("Foo(\"hello\")", "Raw(Foo, \"hello\")");
    }

    // --- Does not evaluate ---

    @Test
    void doesNotEvaluateArgs() throws Exception {
        // Raw should not evaluate its arguments
        fm.assertExec("Foo(x)", "Raw(Foo, x)");
    }

    @Test
    void doesNotCallFunction() throws Exception {
        // Even though Add is a builtin, Raw returns the unevaluated Compound
        // Raw(Add, 1, 2) returns Add(1, 2) which is not 3
        fm.assertExec("Raw(Add, 1, 2)", "Raw(Add, 1, 2)");
    }

}
