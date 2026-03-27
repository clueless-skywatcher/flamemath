package io.flamemath.eval.builtins.ntheory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class IsPrimeFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("IsPrime", new IsPrimeFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("IsPrime()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("IsPrime(2, 3)"));
    }

    // --- Small primes ---

    @Test
    void twoIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(2)");
    }

    @Test
    void threeIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(3)");
    }

    @Test
    void fiveIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(5)");
    }

    @Test
    void sevenIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(7)");
    }

    @Test
    void thirteenIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(13)");
    }

    @Test
    void thirtySevenIsPrime() throws Exception {
        fm.assertExec("True", "IsPrime(37)");
    }

    // --- Small composites ---

    @Test
    void fourIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(4)");
    }

    @Test
    void nineIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(9)");
    }

    @Test
    void fifteenIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(15)");
    }

    @Test
    void twentyFiveIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(25)");
    }

    // --- Edge cases ---

    @Test
    void zeroIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(0)");
    }

    @Test
    void oneIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(1)");
    }

    @Test
    void negativeIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(-7)");
    }

    @Test
    void negativeOneIsNotPrime() throws Exception {
        fm.assertExec("False", "IsPrime(-1)");
    }

    // --- Larger primes ---

    @Test
    void primeAboveBases() throws Exception {
        fm.assertExec("True", "IsPrime(41)");
    }

    @Test
    void largerPrime() throws Exception {
        fm.assertExec("True", "IsPrime(104729)");
    }

    @Test
    void largePrime() throws Exception {
        fm.assertExec("True", "IsPrime(999999937)");
    }

    @Test
    void largePrime11Digits() throws Exception {
        fm.assertExec("True", "IsPrime(69233224009)");
    }

    // --- Larger composites ---

    @Test
    void largerComposite() throws Exception {
        fm.assertExec("False", "IsPrime(104730)");
    }

    @Test
    void largeEven() throws Exception {
        fm.assertExec("False", "IsPrime(1000000000)");
    }

    // --- Carmichael numbers (fool Fermat but not Miller-Rabin) ---

    @Test
    void carmichael561() throws Exception {
        fm.assertExec("False", "IsPrime(561)");
    }

    @Test
    void carmichael1105() throws Exception {
        fm.assertExec("False", "IsPrime(1105)");
    }

    @Test
    void carmichael1729() throws Exception {
        fm.assertExec("False", "IsPrime(1729)");
    }

    // --- Non-integer argument ---

    @Test
    void nonIntegerThrows() {
        assertThrows(Exception.class, () -> fm.execute("IsPrime(3.5)"));
    }
}
