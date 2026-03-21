package io.flamemath.eval.builtins.list;

import static io.flamemath.FlameUtils.numericValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;

public class GenListFunc implements FlameFunction {

    @Override
    public String name() {
        return "GenList";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }

        return buildList(args, evaluator, 1);
    }

    private ListExpr buildList(List<Expr> args, FlameValuator evaluator, int i) throws Exception {
        Expr expr = args.get(0);
        
        if (!args.get(i).isHead("List")) {
            throw new Exception("Invalid argument. GenList takes an expression and any number of lists only");
        }
        ListExpr iteratorSpec = (ListExpr) args.get(i);
        if (iteratorSpec.exprs().size() < 2 || iteratorSpec.exprs().size() > 4) {
            throw new Exception("Invalid list as argument: " + iteratorSpec.toString());
        }
        if (!iteratorSpec.exprs().get(0).isHead("Symbol")) {
            throw new Exception("First element of every list in GenList should be a Symbol");
        }
        for (int it = 1; it < iteratorSpec.exprs().size(); it++) {
            if (!iteratorSpec.exprs().get(it).isNumeric()) {
                throw new Exception("Second element onwards of every list in GenList should be numeric");
            }
        }

        Symbol symbol = (Symbol) iteratorSpec.exprs().get(0);

        double start, end;
        double step = 1;
        if (iteratorSpec.exprs().size() == 2) {
            start = 0;
            end = numericValue(iteratorSpec.exprs().get(1));
        } else {
            start = numericValue(iteratorSpec.exprs().get(1));    
            end = numericValue(iteratorSpec.exprs().get(2));
            if (iteratorSpec.exprs().size() == 4) {
                step = numericValue(iteratorSpec.exprs().get(3));
            }
        }
        boolean useReal = iteratorSpec.exprs().stream().skip(1).anyMatch(a -> a instanceof RealAtom);

        Expr oldValue = evaluator.getEnv().get(symbol);
        
        List<Expr> finalList = new ArrayList<>();
        for (int j = 0; ; j++) {
            double x = start + j * step;
            if (step > 0 ? x >= end : x <= end) break;
            Expr xValue = useReal ? new RealAtom(x) : new IntegerAtom((long) x);
            evaluator.getEnv().set(symbol, xValue);
            if (i == args.size() - 1) {
                finalList.add(evaluator.eval(expr));
            } else {
                finalList.add(buildList(args, evaluator, i + 1));
            }
        }

        if (oldValue != symbol) {
            evaluator.getEnv().set(symbol, oldValue);
        } else {
            evaluator.getEnv().clear(symbol);
        }

        return new ListExpr(finalList);
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0);
    }
}
