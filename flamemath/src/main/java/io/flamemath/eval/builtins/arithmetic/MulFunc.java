package io.flamemath.eval.builtins.arithmetic;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;

public class MulFunc implements FlameFunction {
    @Override
    public String name() {
        return "Mul";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() == 0) {
            return IntegerAtom.ZERO;
        }
        if (args.size() == 1) {
            return args.get(0);
        }

        boolean hasReal = false;
        double numericProduct = 1.0;

        List<Expr> results = new ArrayList<>();

        for (int i = 0; i < args.size(); i++) {
            Expr arg = args.get(i);
            if (arg instanceof IntegerAtom intArg) {
                numericProduct *= intArg.value();
            } else if (arg instanceof RealAtom realArg) {
                numericProduct *= realArg.value();
                hasReal = true;
            } else {
                results.add(arg);
            }
        }

        if (numericProduct == 0) {
            return IntegerAtom.ZERO;
        }

        if (numericProduct != 1 || results.isEmpty()) {
            Expr num = hasReal ? new RealAtom(numericProduct) : new IntegerAtom((long) numericProduct);
            results.addFirst(num);
        }

        if (results.size() == 1) {
            return results.getFirst();
        }
        return new Compound("Mul", results);
    }

    @Override
    public boolean isFlat() {
        return true;
    }
}
