package io.flamemath.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.flamemath.eval.builtins.arithmetic.ArithmeticRegistry;
import io.flamemath.eval.builtins.comparison.ComparisonRegistry;
import io.flamemath.eval.builtins.construct.ConstructRegistry;
import io.flamemath.eval.builtins.general.GeneralRegistry;
import io.flamemath.eval.builtins.list.ListRegistry;
import io.flamemath.eval.builtins.logical.LogicalRegistry;
import io.flamemath.eval.builtins.system.SystemRegistry;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.exceptions.ReturningException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;
import io.flamemath.expr.Symbol;

public class FlameValuator {
    private FunctionRegistry registry;
    private FlameVironment env;

    public Expr eval(Expr expr) throws Exception {
        init();

        if (expr instanceof Symbol s) {
            if (s.name().equals("Null")) return NullExpr.INSTANCE;
            if (env.has(s))
                return env.get(s);
        }

        if (expr.isAtomic()) {
            return expr;
        }

        if (expr instanceof ListExpr l) {
            List<Expr> finalList = new ArrayList<>();
            for (var arg: l.exprs()) {
                finalList.add(eval(arg));
            }
            return new ListExpr(finalList);
        }

        if (expr instanceof Flambda f) return new Flambda(f.params(), f.body(), this.env);

        Compound comp = (Compound) expr;

        if (registry.has(comp.head())) {
            FlameFunction fn = registry.lookup(comp.head()).get();
            Set<Integer> held = fn.heldArgIndexes();
            List<Expr> args = new ArrayList<>();

            List<Expr> children = fn.isFlat()
                    ? flattenChildren(comp.head(), comp.children())
                    : comp.children();

            if (fn.holdAll()) {
                args.addAll(children);
            } else {
                for (int i = 0; i < children.size(); i++) {
                    if (held.contains(i)) {
                        args.add(children.get(i));
                    } else {
                        args.add(eval(children.get(i)));
                    }
                }
            }

            return fn.apply(args, this);
        } else {
            Symbol head = new Symbol(comp.head());
            if (env.has(head)) {
                Expr value = env.get(head);
                List<Expr> evaluatedArgs = evalChildren(comp.children());
                if (value instanceof Flambda lambda) {
                    return applyLambda(lambda, evaluatedArgs);
                } else if (value instanceof Compound c
                    && c.isHead("Seq")
                    && c.children().stream().allMatch(child -> child instanceof Flambda)
                ) {
                    for (Expr child: c.children()) {
                        Flambda lambda = (Flambda) child;
                        if (lambda.params().size() == evaluatedArgs.size()) {
                            return applyLambda(lambda, evaluatedArgs);
                        }
                    }
                    throw new FlameArityException(comp.head(), -1, evaluatedArgs.size());
                }
            }
        }

        return new Compound(comp.head(), evalChildren(comp.children()));
    }

    private Expr applyLambda(Flambda lambda, List<Expr> args) throws Exception {
        FlameVironment childEnv = new FlameVironment(lambda.env());
        if (lambda.params().size() != args.size()) {
            throw new FlameArityException("Lambda", lambda.params().size(), args.size());
        }

        for (int i = 0; i < lambda.params().size(); i++) {
            childEnv.set(lambda.params().get(i), args.get(i));
        }

        FlameVironment oldEnv = this.env;
        this.env = childEnv;
        try {
            Expr result = eval(lambda.body());
            return result;
        } catch (ReturningException r) {
            return r.getExpr();
        } finally {
            this.env = oldEnv;
        }
    }

    private List<Expr> flattenChildren(String head, List<Expr> children) {
        List<Expr> result = new ArrayList<>();
        for (Expr child : children) {
            if (child instanceof Compound nested && nested.head().equals(head)) {
                result.addAll(flattenChildren(head, nested.children()));
            } else {
                result.add(child);
            }
        }
        return result;
    }

    private List<Expr> evalChildren(List<Expr> children) throws Exception {
        List<Expr> result = new ArrayList<>();
        for (Expr child : children) {
            result.add(eval(child));
        }
        return result;
    }

    public FlameVironment getEnv() {
        return env;
    }

    private void init() {
        if (registry != null) return;
        env = new FlameVironment();
        registry = new FunctionRegistry();

        registry.registerAll(ArithmeticRegistry.create());
        registry.registerAll(ComparisonRegistry.create());
        registry.registerAll(LogicalRegistry.create());
        registry.registerAll(GeneralRegistry.create());
        registry.registerAll(SystemRegistry.create());
        registry.registerAll(ConstructRegistry.create());
        registry.registerAll(ListRegistry.create());
    }
}
