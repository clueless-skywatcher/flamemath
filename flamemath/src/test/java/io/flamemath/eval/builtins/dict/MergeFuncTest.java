package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class MergeFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void mergeDisjoint() throws Exception {
        fm.assertExec("1", "Lookup(Merge({\"a\": 1}, {\"b\": 2}), \"a\")");
        fm.assertExec("2", "Lookup(Merge({\"a\": 1}, {\"b\": 2}), \"b\")");
    }

    @Test
    public void laterKeyWins() throws Exception {
        fm.assertExec("99", "Lookup(Merge({\"a\": 1}, {\"a\": 99}), \"a\")");
    }

    @Test
    public void mergeWithEmpty() throws Exception {
        fm.assertExec("1", "Lookup(Merge({\"a\": 1}, {}), \"a\")");
        fm.assertExec("1", "Lookup(Merge({}, {\"a\": 1}), \"a\")");
    }

    @Test
    public void arityThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Merge({})"));
    }

    @Test
    public void nonDictThrows() {
        assertThrows(Exception.class, () -> fm.execute("Merge({}, 42)"));
    }
}
