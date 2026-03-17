package io.flamemath.eval.builtins.general;

import io.flamemath.eval.FlameArityException;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.*;

import java.util.List;
import java.util.Set;

public class SetFunc implements FlameFunction {

    @Override
    public String name() {
        return "Set";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException("Set", 2, args.size());
        }

        if (!(args.get(0) instanceof Symbol s)) {
            throw new Exception("Left side of Set must be a symbol");
        }

        evaluator.getEnv().set(s, args.get(1));
        return args.get(1);
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0);
    }
}
