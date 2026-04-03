package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class OrderModTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void order2Mod7() throws Exception {
        // 2^1=2, 2^2=4, 2^3=1 (mod 7) → order 3
        fm.assertExec("3", "OrderMod(2, 7)");
    }

    @Test
    void order3Mod7() throws Exception {
        // 3^1=3, 3^2=2, 3^3=6, 3^4=4, 3^5=5, 3^6=1 (mod 7) → order 6
        fm.assertExec("6", "OrderMod(3, 7)");
    }

    @Test
    void order2Mod5() throws Exception {
        // 2^1=2, 2^2=4, 2^3=3, 2^4=1 (mod 5) → order 4
        fm.assertExec("4", "OrderMod(2, 5)");
    }

    @Test
    void order4Mod5() throws Exception {
        // 4^1=4, 4^2=1 (mod 5) → order 2
        fm.assertExec("2", "OrderMod(4, 5)");
    }

    // --- Order 1: a ≡ 1 (mod n) ---

    @Test
    void order1Mod5() throws Exception {
        fm.assertExec("1", "OrderMod(1, 5)");
    }

    @Test
    void orderLargeACongruentTo1() throws Exception {
        // 8 mod 7 = 1, so order is 1
        fm.assertExec("1", "OrderMod(8, 7)");
    }

    // --- Primitive roots (order = phi(n)) ---

    @Test
    void primitiveRoot2Mod13() throws Exception {
        // 2 is a primitive root mod 13, order = 12
        fm.assertExec("12", "OrderMod(2, 13)");
    }

    @Test
    void primitiveRoot3Mod17() throws Exception {
        // 3 is a primitive root mod 17, order = 16
        fm.assertExec("16", "OrderMod(3, 17)");
    }

    // --- Composite modulus ---

    @Test
    void order2Mod9() throws Exception {
        // 2^1=2, 2^2=4, 2^3=8, 2^4=7, 2^5=5, 2^6=1 (mod 9) → order 6
        fm.assertExec("6", "OrderMod(2, 9)");
    }

    @Test
    void order4Mod15() throws Exception {
        // 4^1=4, 4^2=1 (mod 15) → order 2
        fm.assertExec("2", "OrderMod(4, 15)");
    }

    // --- Negative a (should reduce mod n) ---

    @Test
    void negativeA() throws Exception {
        // -1 mod 7 = 6, 6^2 = 36 = 1 (mod 7) → order 2
        fm.assertExec("2", "OrderMod(-1, 7)");
    }

    @Test
    void negativeAReduces() throws Exception {
        // -3 mod 7 = 4, order of 4 mod 7: 4^1=4, 4^2=2, 4^3=1 → order 3
        fm.assertExec("3", "OrderMod(-3, 7)");
    }

    // --- a > n ---

    @Test
    void aGreaterThanN() throws Exception {
        // 10 mod 7 = 3, order of 3 mod 7 = 6
        fm.assertExec("6", "OrderMod(10, 7)");
    }

    // --- Not coprime returns raw ---

    @Test
    void notCoprimeReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(2, 4)", "OrderMod(2, 4)");
    }

    @Test
    void notCoprimeReturnsRaw2() throws Exception {
        fm.assertExec("OrderMod(6, 9)", "OrderMod(6, 9)");
    }

    @Test
    void zeroAReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(0, 5)", "OrderMod(0, 5)");
    }

    // --- Invalid modulus returns raw ---

    @Test
    void modulusOneReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(1, 1)", "OrderMod(1, 1)");
    }

    @Test
    void modulusZeroReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(3, 0)", "OrderMod(3, 0)");
    }

    @Test
    void negativeModulusReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(2, -5)", "OrderMod(2, -5)");
    }

    // --- Symbolic inputs return raw ---

    @Test
    void symbolicFirstReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(x, 7)", "OrderMod(x, 7)");
    }

    @Test
    void symbolicSecondReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(3, y)", "OrderMod(3, y)");
    }

    @Test
    void bothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("OrderMod(x, y)", "OrderMod(x, y)");
    }
}
