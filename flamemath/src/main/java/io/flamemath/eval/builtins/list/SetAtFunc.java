package io.flamemath.eval.builtins.list;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;

public class SetAtFunc implements FlameFunction {

    @Override
    public String name() {
        return "SetAt";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }
        if (args.get(0) instanceof ListExpr l) {
            Expr index = args.get(1);
            if (index instanceof IntegerAtom i) {
                long iv = i.value().toLong();
                int idx = (int) (iv >= 0 ? iv : l.exprs().size() + iv);
                try {
                    l.exprs().set(idx, args.get(2));
                } catch (IndexOutOfBoundsException e) {
                    throw new Exception(String.format("Index %d is out of bounds for list of size %d",
                        iv,
                        l.exprs().size()
                    ));
                }
            } else {
                throw new Exception("Index must be an integer");
            }
        } else {
            throw new Exception("Cannot set element of " + args.get(0).head());
        }
        return NullExpr.INSTANCE;
    }

}
