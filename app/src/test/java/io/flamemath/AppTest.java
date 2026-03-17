package io.flamemath;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.flamemath.expr.*;
import java.util.List;

class AppTest {
    @Test
    void exprIntegerAtom() {
        var atom = new IntegerAtom(42);
        assertEquals(42, atom.value());
    }

    @Test
    void exprCompoundImmutable() {
        var compound = new Compound("Plus", List.of(
                new IntegerAtom(1),
                new IntegerAtom(2)
        ));
        assertEquals("Plus", compound.head());
        assertEquals(2, compound.children().size());
    }

    @Test
    void symbolEvaluatesToItself() {
        var sym = new Symbol("x");
        assertEquals("x", sym.name());
    }
}
