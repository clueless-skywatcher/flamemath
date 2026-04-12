package io.flamemath.internal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class ExprUtils {
    public static List<Expr> extractVars(Expr expr) {
        if (expr instanceof Symbol s) {
            return List.of(s);
        }

        Set<Expr> variables = new LinkedHashSet<>();
        if (expr instanceof Compound c) {
            for (var child: c.children()) {
                variables.addAll(extractVars(child));
            }
        }
        return new ArrayList<>(variables);
    }
}
