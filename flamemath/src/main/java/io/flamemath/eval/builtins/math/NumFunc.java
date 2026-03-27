package io.flamemath.eval.builtins.math;

import static java.util.Map.entry;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.eval.FunctionRegistry;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.Symbol;
import static io.flamemath.FlameUtils.*;

public class NumFunc implements FlameFunction {
    private final Map<String, Double> WELL_KNOWN_VALUES = Map.ofEntries(
        entry("Pi", Math.PI),
        entry("E", Math.E)
    );

    @Override
    public String name() {
        return "Num";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        return numerifyExpr(args.get(0), evaluator);
    }

    private Expr numerifyExpr(Expr arg, FlameValuator evaluator) throws Exception {
        // Already a float
        if (arg instanceof RealAtom) {
            return arg;
        }

        // Integer → convert to RealAtom
        if (arg instanceof IntegerAtom i) {
            return new RealAtom(i.value().toDouble());
        }

        // RationalAtom → convert to RealAtom
        if (arg instanceof RationalAtom) {
            return new RealAtom(numericValue(arg));
        }

        // Well-known constant symbols
        if (arg instanceof Symbol s) {
            if (WELL_KNOWN_VALUES.containsKey(s.name())) {
                return new RealAtom(WELL_KNOWN_VALUES.get(s.name()));
            }
            return arg;
        }

        // Compound: numerify children, re-eval, then try numerify on the function
        if (arg instanceof Compound c) {
            List<Expr> numChildren = new ArrayList<>();
            for (var child : c.children()) {
                numChildren.add(numerifyExpr(child, evaluator));
            }

            // Re-eval with numerified children
            Expr result = evaluator.eval(new Compound(c.head(), numChildren));

            // If still a compound with same head, try numerify via the function
            if (result instanceof Compound rc && rc.head().equals(c.head())) {
                FunctionRegistry registry = evaluator.getRegistry();
                if (registry.has(rc.head())) {
                    FlameFunction fn = registry.lookup(rc.head()).get();
                    Expr numerified = fn.numerify(rc.children());
                    if (numerified != null) {
                        return numerified;
                    }
                }
            }

            return result;
        }

        return arg;
    }
}
