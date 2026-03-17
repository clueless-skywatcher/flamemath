package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.*;

import java.util.ArrayList;
import java.util.List;

public class AddFunc implements FlameFunction {

    @Override
    public String name() {
        return "Add";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() == 0) {
            return new IntegerAtom(0);
        }
        if (args.size() == 1) {
            return args.get(0);
        }

        boolean hasReal = false;
        double numericSum = 0.0;

        List<Expr> results = new ArrayList<>();

        for (int i = 0; i < args.size(); i++) {
            Expr arg = args.get(i);
            if (arg instanceof IntegerAtom intArg) {
                numericSum += intArg.value();
            } else if (arg instanceof RealAtom realArg) {
                numericSum += realArg.value();
                hasReal = true;
            } else {
                results.add(arg);
            }
        }

        if (numericSum != 0 || results.isEmpty()) {
            Expr num = (hasReal) ? new RealAtom(numericSum) : new IntegerAtom((long) numericSum);
            results.addFirst(num);
        }

        if (results.size() == 1) {
            return results.get(0);
        }
        return new Compound("Add", results);
    }
}
