package io.flamemath.expr;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullExprTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void nullSymbolEvaluatesToNullExpr() throws Exception {
        assertSame(NullExpr.INSTANCE, fm.execute("Null"));
    }

    @Test
    void isSingleton() {
        assertSame(NullExpr.INSTANCE, NullExpr.INSTANCE);
    }

    @Test
    void isAtomic() {
        assertTrue(NullExpr.INSTANCE.isAtomic());
    }

    @Test
    void head() {
        assertEquals("Null", NullExpr.INSTANCE.head());
    }

    @Test
    void toStringIsNull() {
        assertEquals("Null", NullExpr.INSTANCE.toString());
    }

    @Test
    void isNotTrue() {
        assertFalse(NullExpr.INSTANCE.isTrue());
    }

    @Test
    void isNotFalse() {
        assertFalse(NullExpr.INSTANCE.isFalse());
    }

    @Test
    void isNotNumeric() {
        assertFalse(NullExpr.INSTANCE.isNumeric());
    }

    @Test
    void equality() {
        assertEquals(NullExpr.INSTANCE, NullExpr.INSTANCE);
    }
}
