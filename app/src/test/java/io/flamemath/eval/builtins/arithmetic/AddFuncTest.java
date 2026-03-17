package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.expr.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddFuncTest {
    private final AddFunc add = new AddFunc();

    private Expr apply(Expr... args) throws Exception {
        return add.apply(List.of(args), null);
    }

    private Compound c(String head, Expr... children) {
        return new Compound(head, List.of(children));
    }

    @Test
    void name() {
        assertEquals("Add", add.name());
    }

    @Test
    void zeroArgs() throws Exception {
        assertEquals(new IntegerAtom(0), add.apply(List.of(), null));
    }

    @Test
    void singleInteger() throws Exception {
        assertEquals(new IntegerAtom(5), apply(new IntegerAtom(5)));
    }

    @Test
    void singleReal() throws Exception {
        assertEquals(new RealAtom(3.14), apply(new RealAtom(3.14)));
    }

    @Test
    void singleSymbol() throws Exception {
        assertEquals(new Symbol("x"), apply(new Symbol("x")));
    }

    // --- Two numeric args ---

    @Test
    void twoIntegers() throws Exception {
        assertEquals(new IntegerAtom(5), apply(new IntegerAtom(2), new IntegerAtom(3)));
    }

    @Test
    void twoReals() throws Exception {
        assertEquals(new RealAtom(5.5), apply(new RealAtom(2.5), new RealAtom(3.0)));
    }

    @Test
    void integerPlusReal() throws Exception {
        assertEquals(new RealAtom(5.5), apply(new IntegerAtom(2), new RealAtom(3.5)));
    }

    @Test
    void realPlusInteger() throws Exception {
        assertEquals(new RealAtom(5.5), apply(new RealAtom(3.5), new IntegerAtom(2)));
    }

    // --- Many numeric args ---

    @Test
    void multipleIntegers() throws Exception {
        assertEquals(
                new IntegerAtom(10),
                apply(new IntegerAtom(1), new IntegerAtom(2), new IntegerAtom(3), new IntegerAtom(4)));
    }

    @Test
    void mixedNumerics() throws Exception {
        assertEquals(
                new RealAtom(6.5),
                apply(new IntegerAtom(1), new RealAtom(2.5), new IntegerAtom(3)));
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void twoSymbols() throws Exception {
        assertEquals(
                c("Add", new Symbol("x"), new Symbol("y")),
                apply(new Symbol("x"), new Symbol("y")));
    }

    @Test
    void symbolPlusZero() throws Exception {
        // x + 0 → x (zero collapses, only symbolic remains)
        assertEquals(
                new Symbol("x"),
                apply(new Symbol("x"), new IntegerAtom(0)));
    }

    @Test
    void zeroPlusSymbol() throws Exception {
        assertEquals(
                new Symbol("x"),
                apply(new IntegerAtom(0), new Symbol("x")));
    }

    // --- Mixed numeric + symbolic ---

    @Test
    void integerPlusSymbol() throws Exception {
        // 3 + x → Add(3, x)
        assertEquals(
                c("Add", new IntegerAtom(3), new Symbol("x")),
                apply(new IntegerAtom(3), new Symbol("x")));
    }

    @Test
    void symbolPlusInteger() throws Exception {
        // x + 3 → Add(3, x)  (numeric collected first)
        assertEquals(
                c("Add", new IntegerAtom(3), new Symbol("x")),
                apply(new Symbol("x"), new IntegerAtom(3)));
    }

    @Test
    void multipleNumericAndSymbolic() throws Exception {
        // 1 + x + 2 + y → Add(3, x, y)
        assertEquals(
                c("Add", new IntegerAtom(3), new Symbol("x"), new Symbol("y")),
                apply(new IntegerAtom(1), new Symbol("x"), new IntegerAtom(2), new Symbol("y")));
    }

    @Test
    void realAndSymbolic() throws Exception {
        // 1.5 + x + 2 → Add(3.5, x)
        assertEquals(
                c("Add", new RealAtom(3.5), new Symbol("x")),
                apply(new RealAtom(1.5), new Symbol("x"), new IntegerAtom(2)));
    }

    // --- Nested compound args (left unevaluated) ---

    @Test
    void compoundArgs() throws Exception {
        // Add expects pre-evaluated args, so compounds pass through
        Expr sinX = c("Sin", new Symbol("x"));
        Expr cosY = c("Cos", new Symbol("y"));
        assertEquals(
                c("Add", sinX, cosY),
                apply(sinX, cosY));
    }

    @Test
    void compoundPlusNumeric() throws Exception {
        Expr sinX = c("Sin", new Symbol("x"));
        assertEquals(
                c("Add", new IntegerAtom(5), sinX),
                apply(new IntegerAtom(2), sinX, new IntegerAtom(3)));
    }

    // --- Edge: all zeros ---

    @Test
    void allZeros() throws Exception {
        assertEquals(
                new IntegerAtom(0),
                apply(new IntegerAtom(0), new IntegerAtom(0)));
    }

    // --- Negative numbers ---

    @Test
    void negativeIntegers() throws Exception {
        assertEquals(new IntegerAtom(-1), apply(new IntegerAtom(2), new IntegerAtom(-3)));
    }

    @Test
    void negativeCancellation() throws Exception {
        assertEquals(new IntegerAtom(0), apply(new IntegerAtom(5), new IntegerAtom(-5)));
    }

    @Test
    void negativeCancellationWithSymbol() throws Exception {
        // 5 + x + (-5) → numeric cancels to 0, just x remains
        assertEquals(
                new Symbol("x"),
                apply(new IntegerAtom(5), new Symbol("x"), new IntegerAtom(-5)));
    }
}
