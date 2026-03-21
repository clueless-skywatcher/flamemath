package io.flamemath.eval.builtins.string;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.StringAtom;

public class StrLengthFunc implements FlameFunction {

    @Override
    public String name() {
        return "StrLength";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (!(args.get(0) instanceof StringAtom s)) {
            throw new Exception("Expected string at first position");
        }

        return new IntegerAtom(s.value().length());
    }
    
}
