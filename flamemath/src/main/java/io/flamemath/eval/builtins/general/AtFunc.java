package io.flamemath.eval.builtins.general;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;

public class AtFunc implements FlameFunction {

    @Override
    public String name() {
        return "At";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        if (args.get(0) instanceof ListExpr l) {
            Expr index = args.get(1);
            if (index instanceof IntegerAtom i) {
                int idx = (int) (i.value() >= 0 ? i.value() : l.exprs().size() + i.value());
                try {
                    return l.exprs().get(idx);
                } catch (IndexOutOfBoundsException e) {
                    throw new Exception(String.format("Index %d is out of bounds for list of size %d", 
                        i.value(),
                        l.exprs().size()
                    ));
                }
            } else {
                return new Compound(name(), args);
            }
        } else {
            return new Compound(name(), args);
        }
    }
    
}
