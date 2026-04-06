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

        return new ListExpr(applyVars(args.get(0), evaluator));
    }

    private List<Expr> applyVars(Expr arg, FlameValuator evaluator) {
        if (arg instanceof Symbol s) {
            return List.of(s);
        }

        Set<Expr> variables = new LinkedHashSet<>();
        if (arg instanceof Compound c) {
            for (var child: c.children()) {
                variables.addAll(applyVars(child, evaluator));
            }
        }
        return new ArrayList<>(variables);
    }

}
