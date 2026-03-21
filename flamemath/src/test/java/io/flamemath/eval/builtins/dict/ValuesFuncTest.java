package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class ValuesFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void emptyDict() throws Exception {
        fm.assertExec("[]", "Values({})");
    }

    @Test
    public void singleValue() throws Exception {
        fm.assertExec("[1]", "Values({\"a\": 1})");
    }

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Values()"));
    }

    @Test
    public void nonDictThrows() {
        assertThrows(Exception.class, () -> fm.execute("Values([1, 2])"));
    }
}
