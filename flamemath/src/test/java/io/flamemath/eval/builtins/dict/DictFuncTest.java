package io.flamemath.eval.builtins.dict;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.flamemath.FlameTestingUtils;
import io.flamemath.expr.DictExpr;

import org.junit.jupiter.api.Test;

public class DictFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    public void emptyDictLiteral() throws Exception {
        var result = fm.execute("{}");
        assertInstanceOf(DictExpr.class, result);
        assertTrue(((DictExpr) result).dict().isEmpty());
    }

    @Test
    public void singleEntry() throws Exception {
        fm.assertExec("1", "Lookup({\"a\": 1}, \"a\")");
    }

    @Test
    public void multipleEntries() throws Exception {
        fm.assertExec("1", "Lookup({\"a\": 1, \"b\": 2}, \"a\")");
        fm.assertExec("2", "Lookup({\"a\": 1, \"b\": 2}, \"b\")");
    }

    @Test
    public void integerKeys() throws Exception {
        fm.assertExec("\"hello\"", "Lookup({1: \"hello\", 2: \"world\"}, 1)");
    }

    @Test
    public void expressionValues() throws Exception {
        fm.assertExec("5", "Lookup({\"x\": 2 + 3}, \"x\")");
    }

    @Test
    public void dictViaFunction() throws Exception {
        fm.assertExec("2", "Lookup(Dict(\"a\": 1, \"b\": 2), \"b\")");
    }

    @Test
    public void listKeyThrows() {
        assertThrows(UnsupportedOperationException.class, () -> fm.execute("{[1, 2]: 3}"));
    }
}
