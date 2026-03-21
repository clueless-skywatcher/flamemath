package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class DictRemoveFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void removeExistingKey() throws Exception {
        fm.assertExec("False", "HasKey(DictRemove({\"a\": 1}, \"a\"), \"a\")");
    }

    @Test
    public void removeMissingKeyNoError() throws Exception {
        fm.assertExec("True", "HasKey(DictRemove({\"a\": 1}, \"b\"), \"a\")");
    }

    @Test
    public void originalUnchanged() throws Exception {
        fm.assertExec("True", "{d = {\"a\": 1}; DictRemove(d, \"a\"); HasKey(d, \"a\")}");
    }

    @Test
    public void arityThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("DictRemove({})"));
    }
}
