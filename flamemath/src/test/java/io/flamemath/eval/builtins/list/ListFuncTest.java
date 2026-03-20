package io.flamemath.eval.builtins.list;

import io.flamemath.FlameTestingUtils;

import org.junit.jupiter.api.Test;

public class ListFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic construction ---

    @Test
    public void emptyList() throws Exception {
        fm.assertExec("List()", "[]");
    }

    @Test
    public void singleElement() throws Exception {
        fm.assertExec("List(1)", "[1]");
    }

    @Test
    public void multipleIntegers() throws Exception {
        fm.assertExec("List(1, 2, 3)", "[1, 2, 3]");
    }

    @Test
    public void mixedTypes() throws Exception {
        fm.assertExec("List(1, 2.5, \"hello\", True)", "[1, 2.5, \"hello\", True]");
    }

    // --- Arguments are evaluated ---

    @Test
    public void evaluatesArithmeticArgs() throws Exception {
        fm.assertExec("List(1 + 2, 3 * 4)", "[3, 12]");
    }

    @Test
    public void evaluatesNestedCalls() throws Exception {
        fm.assertExec("List(Head(42))", "[Integer]");
    }

    // --- Bracket syntax ---

    @Test
    public void bracketSyntaxEmpty() throws Exception {
        fm.assertExec("[]", "List()");
    }

    @Test
    public void bracketSyntaxMultiple() throws Exception {
        fm.assertExec("[1, 2, 3]", "List(1, 2, 3)");
    }

    @Test
    public void bracketSyntaxEvaluatesArgs() throws Exception {
        fm.assertExec("[1 + 1, 2 + 2]", "[2, 4]");
    }

    // --- Nested lists ---

    @Test
    public void nestedList() throws Exception {
        fm.assertExec("List(List(1, 2), List(3, 4))", "[[1, 2], [3, 4]]");
    }

    @Test
    public void nestedBracketSyntax() throws Exception {
        fm.assertExec("[[1, 2], [3, 4]]", "List(List(1, 2), List(3, 4))");
    }

    // --- Head of list ---

    @Test
    public void headOfList() throws Exception {
        fm.assertExec("Head([1, 2, 3])", "List");
    }

    // --- Symbolic elements ---

    @Test
    public void symbolicElements() throws Exception {
        fm.assertExec("List(x, y, z)", "[x, y, z]");
    }

    @Test
    public void mixedSymbolicAndNumeric() throws Exception {
        fm.assertExec("List(1, x, 2 + 3)", "[1, x, 5]");
    }
}
