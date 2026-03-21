package io.flamemath.eval.builtins.string;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.StringAtom;

public class ToStrFunc implements FlameFunction {

    @Override
    public String name() {
        return "ToStr";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        return new StringAtom(args.get(0).toString());
    }
    
}
