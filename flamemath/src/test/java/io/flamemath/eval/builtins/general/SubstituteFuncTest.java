package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

public class SubstituteFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Substitute", new SubstituteFunc().name());
    }

    // --- Basic substitution ---

    @Test
    void substituteWithInt() throws Exception {
        fm.assertExec("12", "Substitute(x^2 + x, x, 3)");
    }

    @Test
    void substituteWithSymbol() throws Exception {
        fm.assertExec("y^2 + y", "Substitute(x^2 + x, x, y)");
    }

    @Test
    void substituteWithReal() throws Exception {
        fm.assertExec("4.5", "Substitute(x + 2, x, 2.5)");
    }

    @Test
    void substituteWithString() throws Exception {
        fm.assertExec("\"hello\"", "Substitute(x, x, \"hello\")");
    }

    @Test
    void substituteWithList() throws Exception {
        fm.assertExec("[1, 2, 3]", "Substitute(x, x, [1, 2, 3])");
    }

    @Test
    void substituteWithBoolean() throws Exception {
        fm.assertExec("True", "Substitute(x, x, True)");
    }

    // --- Expressions ---

    @Test
    void substituteInProduct() throws Exception {
        fm.assertExec("6", "Substitute(Substitute(x * y, x, 2), y, 3)");
    }

    @Test
    void substituteInNestedExpr() throws Exception {
        fm.assertExec("5", "Substitute(Sqrt(x), x, 25)");
    }

    @Test
    void substituteInFunctionCall() throws Exception {
        fm.assertExec("0", "Substitute(Sin(x), x, 0)");
    }

    @Test
    void substitutePreservesUnmatchedSymbols() throws Exception {
        fm.assertExec("y + 3", "Substitute(x + y, x, 3)");
    }

    @Test
    void substituteMultipleOccurrences() throws Exception {
        fm.assertExec("9", "Substitute(x + x + x, x, 3)");
    }

    // --- Scoping: does not leak ---

    @Test
    void substituteDoesNotLeakToOuterScope() throws Exception {
        fm.assertExec("x", "{ Substitute(x + 1, x, 5); x }");
    }

    @Test
    void substituteRestoresExistingBinding() throws Exception {
        fm.assertExec("10", "{ x = 10; Substitute(x + 1, x, 5); x }");
    }

    // --- Composed ---

    @Test
    void substituteInListExpr() throws Exception {
        fm.assertExec("[2, 4, 6]", "Substitute(Map((i) => i * x, [1, 2, 3]), x, 2)");
    }

    @Test
    void nestedSubstitute() throws Exception {
        fm.assertExec("9", "Substitute(Substitute(x + y, x, 2), y, 7)");
    }

    // --- Symbolic (partial substitution) ---

    @Test
    void partialSubstitutionRemainsSymbolic() throws Exception {
        fm.assertExec("Sin(y)", "Substitute(Sin(x), x, y)");
    }

    @Test
    void noMatchReturnsOriginal() throws Exception {
        fm.assertExec("y + 1", "Substitute(y + 1, x, 99)");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Substitute()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Substitute(x, x)"));
    }

    // --- Validation errors ---

    @Test
    void evenArgCountThrows() {
        assertThrows(Exception.class, () -> fm.execute("Substitute(x + y, x, 1, y)"));
    }

    @Test
    void nonSymbolKeyThrows() {
        assertThrows(Exception.class, () -> fm.execute("Substitute(x, 42, 1)"));
    }

    @Test
    void nonSymbolKeyInSecondPairThrows() {
        assertThrows(Exception.class, () -> fm.execute("Substitute(x + y, x, 1, 42, 2)"));
    }
}
