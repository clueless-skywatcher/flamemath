package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class DictSetFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void addNewKey() throws Exception {
        fm.assertExec("10", "Lookup(DictSet({}, \"a\", 10), \"a\")");
    }

    @Test
    public void overwriteExistingKey() throws Exception {
        fm.assertExec("99", "Lookup(DictSet({\"a\": 1}, \"a\", 99), \"a\")");
    }

    @Test
    public void originalUnchanged() throws Exception {
        fm.assertExec("1", "{d = {\"a\": 1}; DictSet(d, \"a\", 99); Lookup(d, \"a\")}");
    }

    @Test
    public void listKeyThrows() {
        assertThrows(UnsupportedOperationException.class, () -> fm.execute("DictSet({}, [1], 2)"));
    }

    @Test
    public void arityThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("DictSet({})"));
    }
}
