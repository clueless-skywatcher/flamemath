package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.RealAtom;

import static io.flamemath.FlameUtils.*;

public class RangeFunc implements FlameFunction {

    @Override
    public String name() {
        return "Range";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 1 || args.size() > 3) {
            throw new FlameArityException(name(), new int[] { 1, 2, 3 }, args.size());
        }

        for (Expr arg : args) {
            if (!arg.isNumeric()) {
                throw new Exception("Not appropriate bounds for Range()");
            }
        }

        Expr start = IntegerAtom.ZERO;
        Expr end = args.get(0);
        Expr step = IntegerAtom.ONE;

        if (args.size() >= 2) {
            start = args.get(0);
            end = args.get(1);
            if (args.size() == 3) {
                step = args.get(2);
            }
        }

        double startN = numericValue(start);
        double endN = numericValue(end);
        double stepN = numericValue(step);

        if (stepN == 0) {
            throw new Exception("Range step cannot be 0");
        }

        boolean useReal = args.stream().anyMatch(a -> a instanceof RealAtom);

        List<Expr> finalList = new ArrayList<>();
        for (int i = 0; ; i++) {
            double x = startN + i * stepN;
            if (stepN > 0 ? x >= endN : x <= endN) break;
            finalList.add(useReal ? new RealAtom(x) : new IntegerAtom((long) x));
        }

        return new ListExpr(finalList);
    }

}
