package io.flamemath.eval.builtins.string;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.StringAtom;

public class StrSplitFunc implements FlameFunction {

    @Override
    public String name() {
        return "StrSplit";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        if (!(args.get(0) instanceof StringAtom s)) {
            throw new Exception("First element should be a string");
        }

        if (!(args.get(1) instanceof StringAtom split)) {
            throw new Exception("Second element should be a string");
        }

        String[] splitStr = s.value().split(split.value());
        List<Expr> splitExprs = new ArrayList<>();
        for (var str: splitStr) {
            splitExprs.add(new StringAtom(str));
        }
        return new ListExpr(splitExprs);
    }
    
}
