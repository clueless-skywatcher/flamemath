package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class EulerPhiTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Small values ---

    @Test
    void eulerPhiOf1() throws Exception {
        fm.assertExec("1", "EulerPhi(1)");
    }

    @Test
    void eulerPhiOf2() throws Exception {
        fm.assertExec("1", "EulerPhi(2)");
    }

    @Test
    void eulerPhiOf3() throws Exception {
        fm.assertExec("2", "EulerPhi(3)");
    }

    @Test
    void eulerPhiOf4() throws Exception {
        fm.assertExec("2", "EulerPhi(4)");
    }

    @Test
    void eulerPhiOf6() throws Exception {
        fm.assertExec("2", "EulerPhi(6)");
    }

    @Test
    void eulerPhiOf10() throws Exception {
        fm.assertExec("4", "EulerPhi(10)");
    }

    // --- Primes (phi(p) = p - 1) ---

    @Test
    void eulerPhiOf7() throws Exception {
        fm.assertExec("6", "EulerPhi(7)");
    }

    @Test
    void eulerPhiOf13() throws Exception {
        fm.assertExec("12", "EulerPhi(13)");
    }

    @Test
    void eulerPhiOf29() throws Exception {
        fm.assertExec("28", "EulerPhi(29)");
    }

    // --- Prime powers (phi(p^k) = p^k - p^(k-1)) ---

    @Test
    void eulerPhiOf8() throws Exception {
        fm.assertExec("4", "EulerPhi(8)");
    }

    @Test
    void eulerPhiOf9() throws Exception {
        fm.assertExec("6", "EulerPhi(9)");
    }

    @Test
    void eulerPhiOf27() throws Exception {
        fm.assertExec("18", "EulerPhi(27)");
    }

    // --- Larger composites ---

    @Test
    void eulerPhiOf12() throws Exception {
        fm.assertExec("4", "EulerPhi(12)");
    }

    @Test
    void eulerPhiOf36() throws Exception {
        fm.assertExec("12", "EulerPhi(36)");
    }

    @Test
    void eulerPhiOf60() throws Exception {
        fm.assertExec("16", "EulerPhi(60)");
    }

    @Test
    void eulerPhiOf100() throws Exception {
        fm.assertExec("40", "EulerPhi(100)");
    }

    @Test
    void eulerPhiOf360() throws Exception {
        fm.assertExec("96", "EulerPhi(360)");
    }

    // --- Negative inputs return raw ---

    @Test
    void eulerPhiOfNeg6() throws Exception {
        fm.assertExec("EulerPhi(-6)", "EulerPhi(-6)");
    }

    @Test
    void eulerPhiOfNeg12() throws Exception {
        fm.assertExec("EulerPhi(-12)", "EulerPhi(-12)");
    }

    @Test
    void eulerPhiOfNeg1() throws Exception {
        fm.assertExec("EulerPhi(-1)", "EulerPhi(-1)");
    }

    // --- Zero (unevaluated) ---

    @Test
    void eulerPhiOfZeroReturnsRaw() throws Exception {
        fm.assertExec("EulerPhi(0)", "EulerPhi(0)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("EulerPhi(x)", "EulerPhi(x)");
    }
}
