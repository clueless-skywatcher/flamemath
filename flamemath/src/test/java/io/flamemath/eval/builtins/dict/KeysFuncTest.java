package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class KeysFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void emptyDict() throws Exception {
        fm.assertExec("[]", "Keys({})");
    }

    @Test
    public void singleKey() throws Exception {
        fm.assertExec("[\"a\"]", "Keys({\"a\": 1})");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Keys()"));
    }

    @Test
    public void nonDictThrows() {
        assertThrows(Exception.class, () -> fm.execute("Keys(42)"));
    }
}
