package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class HasKeyFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void keyExists() throws Exception {
        fm.assertExec("True", "HasKey({\"a\": 1}, \"a\")");
    }

    @Test
    public void keyMissing() throws Exception {
        fm.assertExec("False", "HasKey({\"a\": 1}, \"b\")");
    }

    @Test
    public void emptyDict() throws Exception {
        fm.assertExec("False", "HasKey({}, \"a\")");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("HasKey()"));
    }

    @Test
    public void nonDictThrows() {
        assertThrows(Exception.class, () -> fm.execute("HasKey(42, \"a\")"));
    }
}
