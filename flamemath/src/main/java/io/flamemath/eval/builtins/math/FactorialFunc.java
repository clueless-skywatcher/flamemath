package io.flamemath.eval.builtins.math;

import java.util.List;

import io.flamemath.FlameUtils;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.internal.FlameInt;

public class FactorialFunc implements FlameFunction {

    @Override
    public String name() {
        return "Factorial";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        if (!(arg instanceof IntegerAtom intArg)) {
            return new Compound(name(), args);
        }

        FlameInt n = intArg.value();
        if (n.isNegative()) {
            return new Compound(name(), args);
        }

        if (!n.fitsInLong()) {
            return new Compound(name(), args);
        }

        return new IntegerAtom(FlameUtils.factorial((int) n.toLong()));
    }
}
