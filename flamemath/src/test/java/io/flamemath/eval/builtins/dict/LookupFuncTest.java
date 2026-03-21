package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class LookupFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void existingKey() throws Exception {
        fm.assertExec("42", "Lookup({\"x\": 42}, \"x\")");
    }

    @Test
    public void missingKeyReturnsNull() throws Exception {
        fm.assertExec("Null", "Lookup({\"x\": 42}, \"y\")");
    }

    @Test
    public void integerKey() throws Exception {
        fm.assertExec("\"yes\"", "Lookup({1: \"yes\"}, 1)");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Lookup()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Lookup({})"));
    }

    @Test
    public void nonDictThrows() {
        assertThrows(Exception.class, () -> fm.execute("Lookup(42, \"a\")"));
    }
}
