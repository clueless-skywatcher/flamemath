package io.flamemath.eval.builtins.construct;

import io.flamemath.expr.Compound;
import io.flamemath.lang.parser.FlameParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeqFuncTest {

    @Test
    void braceBlockDesugarsToSeq() throws Exception {
        var parsed = new FlameParser("{ a; b }").parse();
        assertInstanceOf(Compound.class, parsed);
        Compound seq = (Compound) parsed;
        assertEquals("Seq", seq.head());
        assertEquals(2, seq.children().size());
    }
}
