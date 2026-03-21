package io.flamemath.eval.builtins.construct;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.*;

import java.util.List;
import java.util.Set;

public class SetFunc implements FlameFunction {
    private final List<String> PROTECTED = List.of(
        "Pi", "E", "True", "False"
    );

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

        if (PROTECTED.contains(s.name()) || evaluator.getRegistry().has(s.name())) {
            throw new Exception(s.name() + " is a protected constant/function name");
        }

        evaluator.getEnv().set(s, args.get(1));
        return args.get(1);
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0);
    }
}
