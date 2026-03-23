package io.flamemath.eval.builtins.ntheory;

import java.util.List;

import io.flamemath.FlameUtils;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class GCDFunc implements FlameFunction {

    @Override
    public String name() {
        return "GCD";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (!(args.get(0) instanceof IntegerAtom i1)) {
            throw new Exception("First argument must be an integer");
        }

        if (!(args.get(0) instanceof IntegerAtom i2)) {
            throw new Exception("Second argument must be an integer");
        }

        return new IntegerAtom(FlameUtils.gcd(i1.value(), i2.value()));
    }
    
}
