package io.flamemath;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.flamemath.expr.*;
import io.flamemath.internal.FlameInt;
import java.util.List;

class AppTest {
    @Test
    void exprIntegerAtom() {
        var atom = new IntegerAtom(42);
        assertEquals(new FlameInt(42), atom.value());
    }

    @Test
    void exprCompoundImmutable() {
        var compound = new Compound("Add", List.of(
                IntegerAtom.ONE,
                new IntegerAtom(2)
        ));
        assertEquals("Add", compound.head());
        assertEquals(2, compound.children().size());
    }

    @Test
    void symbolEvaluatesToItself() {
        var sym = new Symbol("x");
        assertEquals("x", sym.name());
    }
}
