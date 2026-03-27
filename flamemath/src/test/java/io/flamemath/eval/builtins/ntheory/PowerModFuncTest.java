package io.flamemath.eval.builtins.ntheory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class PowModFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("PowMod", new PowModFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PowMod()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PowMod(2, 3)"));
    }

    @Test
    void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PowMod(2, 3, 5, 7)"));
    }

    // --- Basic cases ---

    @Test
    void basicPowMod() throws Exception {
        fm.assertExec("24", "PowMod(2, 10, 1000)");
    }

    @Test
    void smallValues() throws Exception {
        fm.assertExec("1", "PowMod(2, 4, 5)");
    }

    @Test
    void exponentOne() throws Exception {
        fm.assertExec("2", "PowMod(2, 1, 7)");
    }

    @Test
    void exponentZero() throws Exception {
        fm.assertExec("1", "PowMod(2, 0, 5)");
    }

    @Test
    void modOne() throws Exception {
        fm.assertExec("0", "PowMod(5, 3, 1)");
    }

    @Test
    void baseZero() throws Exception {
        fm.assertExec("0", "PowMod(0, 10, 7)");
    }

    @Test
    void baseOne() throws Exception {
        fm.assertExec("1", "PowMod(1, 1000000, 7)");
    }

    // --- Fermat's little theorem ---

    @Test
    void fermatLittleTheorem() throws Exception {
        // 2^(p-1) mod p == 1 for prime p
        fm.assertExec("1", "PowMod(2, 12, 13)");
    }

    @Test
    void fermatLargerPrime() throws Exception {
        fm.assertExec("1", "PowMod(3, 96, 97)");
    }

    // --- Large values (overflow-safe) ---

    @Test
    void largeModulus() throws Exception {
        fm.assertExec("1", "PowMod(2, 1092, 1194649)");
    }

    @Test
    void largeExponent() throws Exception {
        fm.assertExec("1", "PowMod(2, 3510, 12327121)");
    }

    // --- Wieferich prime verification ---

    @Test
    void wieferich1093() throws Exception {
        // 2^(1093-1) mod 1093^2 == 1
        fm.assertExec("1", "PowMod(2, 1092, 1194649)");
    }

    @Test
    void wieferich3511() throws Exception {
        // 2^(3511-1) mod 3511^2 == 1
        fm.assertExec("1", "PowMod(2, 3510, 12327121)");
    }

    // --- Negative base ---

    @Test
    void negativeBase() throws Exception {
        fm.assertExec("2", "PowMod(-2, 3, 5)");
    }

    // --- Modulus zero ---

    @Test
    void modZeroThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("PowMod(2, 3, 0)"));
    }

    // --- Non-integer argument ---

    @Test
    void nonIntegerThrows() {
        assertThrows(Exception.class, () -> fm.execute("PowMod(2.5, 3, 5)"));
    }

    // --- Big values (overflow long) ---

    @Test
    void bigBaseAndModulus() throws Exception {
        // 10^20 mod 97 = 10^20 mod 97
        fm.assertExec("73", "PowMod(10, 20, 97)");
    }

    @Test
    void bigExponentBigModulus() throws Exception {
        // 2^1000 mod 1000000007 (large prime)
        fm.assertExec("688423210", "PowMod(2, 1000, 1000000007)");
    }
}
