package io.flamemath;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

class FlameTestingUtils {
    public static Expr execute(String expr) throws Exception {
        return new FlameValuator().eval(new FlameParser(expr).parse());
    }
}