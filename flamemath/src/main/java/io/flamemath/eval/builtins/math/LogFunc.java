package io.flamemath.eval.builtins.math;

import static io.flamemath.FlameUtils.numericValue;
import static io.flamemath.FlameUtils.toNumericAtom;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;

public class LogFunc implements FlameFunction {
    @Override
    public String name() {
        return "Log";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (args.get(0) instanceof Symbol s && s.name().equals("E")) {
            return IntegerAtom.ONE;
        }
        if (args.get(0).isNumeric() && numericValue(args.get(0)) == 1) {
            return IntegerAtom.ZERO;
        }
        if (args.get(0) instanceof RealAtom r) {
            return toNumericAtom(Math.log(r.value()));
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return new RealAtom(Math.log((double) numericValue(args.get(0))));
    }
}
