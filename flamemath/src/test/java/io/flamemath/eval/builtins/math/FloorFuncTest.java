package io.flamemath.eval.builtins.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class FloorFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Floor", new FloorFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Floor()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Floor(1, 2)"));
    }

    @Test
    void integerFloor() throws Exception {
        fm.assertExec("10", "Floor(10)");
        fm.assertExec("-1", "Floor(-1)");
    }

    @Test
    void realFloor() throws Exception {
        fm.assertExec("1", "Floor(1.25)");
        fm.assertExec("-2", "Floor(-1.25)");
    }
}
