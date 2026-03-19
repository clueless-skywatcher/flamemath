package io.flamemath.eval.builtins.comparison;

import static io.flamemath.FlameUtils.numericValue;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Expr;

public class EqFunc implements FlameFunction {

    @Override
    public String name() {
        return "Eq";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        Expr a = args.get(0);
        Expr b = args.get(1);

        if (a.isNumeric() && b.isNumeric()) {
            return BooleanAtom.of(numericValue(a) == numericValue(b));
        }

        return BooleanAtom.of(a.equals(b));
    }
    
}
