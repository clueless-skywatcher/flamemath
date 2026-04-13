package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class IsVectorTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Flat numeric lists ---

    @Test
    void integerList() throws Exception {
        fm.assertExec("True", "IsVector([1, 2, 3])");
    }

    @Test
    void realList() throws Exception {
        fm.assertExec("True", "IsVector([1.5, 2.25, 3.75])");
    }

    @Test
    void mixedNumericList() throws Exception {
        fm.assertExec("True", "IsVector([1, 2.5, 3])");
    }

    @Test
    void singletonList() throws Exception {
        fm.assertExec("True", "IsVector([7])");
    }

    @Test
    void emptyList() throws Exception {
        fm.assertExec("True", "IsVector([])");
    }

    // --- Non-numeric atoms are still accepted (no IsList) ---

    @Test
    void stringElementsAccepted() throws Exception {
        fm.assertExec("True", "IsVector([\"a\", \"b\"])");
    }

    // --- Lists of lists are rejected ---

    @Test
    void nestedListsRejected() throws Exception {
        fm.assertExec("False", "IsVector([[1, 2], [3, 4]])");
    }

    @Test
    void partiallyNestedRejected() throws Exception {
        fm.assertExec("False", "IsVector([1, [2, 3], 4])");
    }

    // --- Non-list arguments ---

    @Test
    void integerNotVector() throws Exception {
        fm.assertExec("False", "IsVector(5)");
    }

    @Test
    void stringNotVector() throws Exception {
        fm.assertExec("False", "IsVector(\"hello\")");
    }

    @Test
    void symbolNotVector() throws Exception {
        fm.assertExec("False", "IsVector(x)");
    }

    // --- Composed ---

    @Test
    void vectorFromRange() throws Exception {
        fm.assertExec("True", "IsVector(Range(5))");
    }

    @Test
    void vectorFromVariable() throws Exception {
        fm.assertExec("True", "{v = [1, 2, 3]; IsVector(v)}");
    }
}
