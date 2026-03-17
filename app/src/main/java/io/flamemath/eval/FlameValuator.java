package io.flamemath.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.flamemath.eval.builtins.arithmetic.AddFunc;
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

            for (int i = 0; i < comp.children().size(); i++) {
                if (held.contains(i)) {
                    args.add(comp.children().get(i));
                } else {
                    args.add(eval(comp.children().get(i)));
                }
            }

            return fn.apply(args, this);
        }

        // Unknown head — eval all children
        return new Compound(comp.head(), evalChildren(comp.children()));
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

        registry.register(new SetFunc());

        registry.register(new ExitFunc());
    }
}
