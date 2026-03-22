package io.flamemath.eval.builtins.general;

import java.util.List;
import java.util.Set;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.Symbol;

public class ApplyFunc implements FlameFunction {
    @Override
    public String name() {
        return "Apply";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        Expr func = args.get(0);
        Expr argExpr = args.get(1);

        if (!(argExpr instanceof ListExpr l)) {
            return new Compound(name(), args);
        }

        if (func instanceof Symbol s) {
            return evaluator.eval(new Compound(s.name(), l.exprs()));
        }

        if (func instanceof Flambda lambda) {
            return evaluator.applyLambda(lambda, l.exprs());
        }

        if (func instanceof Compound c
                && c.isHead("Seq")
                && c.children().stream().allMatch(child -> child instanceof Flambda)) {
            for (Expr child : c.children()) {
                Flambda lambda = (Flambda) child;
                if (!lambda.variadic() && lambda.params().size() == l.exprs().size()) {
                    return evaluator.applyLambda(lambda, l.exprs());
                }
            }
            for (Expr child : c.children()) {
                Flambda lambda = (Flambda) child;
                if (lambda.variadic() && l.exprs().size() >= lambda.params().size() - 1) {
                    return evaluator.applyLambda(lambda, l.exprs());
                }
            }
            throw new FlameArityException(name(), -1, l.exprs().size());
        }

        return new Compound(name(), args);
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0);
    }
}
