package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.CanonicalComparator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;

public class SortFunc implements FlameFunction {
    @Override
    public String name() {
        return "Sort";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 1) {
            throw FlameArityException.atLeast(name(), 1, args.size());
        }
        if (args.size() > 2) {
            throw FlameArityException.atMost(name(), 2, args.size());
        }
        if (!(args.get(0) instanceof ListExpr l)) {
            throw new Exception("Sort argument should be a list");
        }
        Comparator<Expr> comparator = new CanonicalComparator();
        if (args.size() == 2) {
            if (!evaluator.isFunction(args.get(1))) {
                throw new Exception("Second argument should be a library function or a lambda");
            }
            final Flambda func;
            if (args.get(1) instanceof Flambda) {
                func = (Flambda) args.get(1);
            } else {
                // This branch is a Seq
                Compound c = (Compound) args.get(1); 
                Flambda foundFunc = null;
                for (int i = 0; i < c.children().size(); i++) {
                    Flambda potential = (Flambda) c.children().get(i);
                    if(potential.params().size() == 2) {
                        foundFunc = potential;
                        break;
                    } 
                }
                if (foundFunc == null) {
                    throw new Exception("Cannot find overload of arity 2");
                }
                func = foundFunc;
            }
            comparator = new Comparator<Expr>() {
                @Override
                public int compare(Expr arg0, Expr arg1) {
                    try {
                        Expr result = evaluator.applyLambda(func, List.of(arg0, arg1));
                        if (result instanceof IntegerAtom i) return (int) i.value().toLong();
                        return result.isTrue() ? -1 : 1;
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());          
                    }
                }
            };
        }

        List<Expr> sorted = new ArrayList<>(l.exprs());
        try {
            sorted.sort(comparator);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof Exception cause) throw cause;
            throw e;
        }
        return new ListExpr(sorted);
    }
}
