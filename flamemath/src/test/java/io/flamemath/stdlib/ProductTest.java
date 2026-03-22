package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ProductTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic functionality ---

    @Test
    void productOfIntegers() throws Exception {
        fm.assertExec("24", "Product([2, 3, 4])");
    }

    @Test
    void productOfSingleElement() throws Exception {
        fm.assertExec("5", "Product([5])");
    }

    @Test
    void productWithOne() throws Exception {
        fm.assertExec("6", "Product([1, 2, 3])");
    }

    @Test
    void productWithZero() throws Exception {
        fm.assertExec("0", "Product([1, 2, 0, 4])");
    }

    // --- Negative numbers ---

    @Test
    void productWithNegative() throws Exception {
        fm.assertExec("-6", "Product([-1, 2, 3])");
    }

    @Test
    void productTwoNegatives() throws Exception {
        fm.assertExec("6", "Product([-2, -3])");
    }

    // --- Real numbers ---

    @Test
    void productOfReals() throws Exception {
        fm.assertExec("6.0", "Product([1.5, 4.0])");
    }

    @Test
    void productMixedIntegerAndReal() throws Exception {
        fm.assertExec("6.0", "Product([2, 3.0])");
    }

    // --- Variable binding ---

    @Test
    void productFromVariable() throws Exception {
        fm.assertExec("24", "{l = [2, 3, 4]}; Product(l)}");
    }

    // --- Composed ---

    @Test
    void productOfRange() throws Exception {
        fm.assertExec("24", "Product(Range(1, 5))");
    }
}
