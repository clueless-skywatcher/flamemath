package io.flamemath.eval.builtins.string;

import static io.flamemath.FlameUtils.*;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.StringAtom;

public class SubStrFunc implements FlameFunction {

    @Override
    public String name() {
        return "SubStr";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }
        if (args.size() > 3) {
            throw FlameArityException.atMost(name(), 3, args.size());
        }

        if (!(args.get(0) instanceof StringAtom s)) {
            throw new Exception("First element should be a string");
        }
        if (!args.get(1).isNumeric()) {
            throw new Exception("Second element should be a number");
        }

        int start = 0;
        int end = (int) numericValue(args.get(1));
        if (args.size() == 3) {
            if (!args.get(2).isNumeric()) {
                throw new Exception("Third element should be a number");
            }
            start = (int) numericValue(args.get(1));
            end = (int) numericValue(args.get(2));
        }

        if (start < 0 || end > s.value().length() || start > end) {
            throw new Exception("Invalid start or end index");
        }
        return new StringAtom(s.value().substring(start, end));
    }
    
}
