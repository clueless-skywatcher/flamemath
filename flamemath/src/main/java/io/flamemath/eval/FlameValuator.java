package io.flamemath.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.flamemath.eval.builtins.arithmetic.AddFunc;
import io.flamemath.eval.builtins.arithmetic.MulFunc;
import io.flamemath.eval.builtins.arithmetic.PowFunc;
import io.flamemath.eval.builtins.general.HeadFunc;
import io.flamemath.eval.builtins.general.SetFunc;
import io.flamemath.eval.builtins.system.ExitFunc;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class FlameValuator {
    private FunctionRegistry registry;
    private FlameVironment env;

    public Expr eval(Expr expr) throws Exception {
        init();

        if (expr instanceof Symbol s) {
            if (env.has(s))
                return env.get(s);
        }

        if (expr.isAtomic()) {
            return expr;
        }

        Compound comp = (Compound) expr;

        if (registry.has(comp.head())) {
            FlameFunction fn = registry.lookup(comp.head()).get();
            Set<Integer> held = fn.heldArgIndexes();
            List<Expr> args = new ArrayList<>();

            List<Expr> children = fn.isFlat()
                    ? flattenChildren(comp.head(), comp.children())
                    : comp.children();

            for (int i = 0; i < children.size(); i++) {
                if (held.contains(i)) {
                    args.add(children.get(i));
                } else {
                    args.add(eval(children.get(i)));
                }
            }

            return fn.apply(args, this);
        }

        return new Compound(comp.head(), evalChildren(comp.children()));
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
        
        registry.register(new AddFunc());
        registry.register(new MulFunc());
        registry.register(new PowFunc());

        registry.register(new HeadFunc());

        registry.register(new SetFunc());

        registry.register(new ExitFunc());
    }
}
