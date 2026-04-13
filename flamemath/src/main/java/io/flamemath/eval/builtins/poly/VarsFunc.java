package io.flamemath.eval.builtins.poly;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.Symbol;
import io.flamemath.internal.math.ExprUtils;

public class VarsFunc implements FlameFunction {

    @Override
    public String name() {
        return "Vars";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        return new ListExpr(ExprUtils.extractVars(args.get(0)));
    }

}
