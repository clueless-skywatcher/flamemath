package io.flamemath.internal.math;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.Symbol;

public class PolyExpr {
    private Map<FlaMonomial, Expr> terms;

    private PolyExpr(Map<FlaMonomial, Expr> terms) {
        this.terms = terms;
    }

    public static PolyExpr fromExpr(Expr expr, FlameValuator evaluator) throws Exception {
        Map<FlaMonomial, Expr> terms = new LinkedHashMap<>();
        
        List<Expr> exprTerms = new ArrayList<>();
        if (expr.isHead("Add")) {
            exprTerms.addAll(expr.getChildren());
        } else {
            exprTerms.add(expr);
        }

        for (var term: exprTerms) {
            MonomialCoeffPair pair = parseTerm(term);
            if (pair == null) return null;
            if (terms.containsKey(pair.monomial())) {
                terms.put(pair.monomial(), evaluator.eval(
                    new Compound("Add", List.of(terms.get(pair.monomial()), pair.coeff()))
                ));
            } else {
                terms.put(pair.monomial(), pair.coeff());
            }
        }

        terms.values().removeIf(Expr::isZero);

        return new PolyExpr(terms);
    }

    private static MonomialCoeffPair parseTerm(Expr term) {
        if (term.isNumeric()) {
            return new MonomialCoeffPair(FlaMonomial.of(), term);
        }
        if (term instanceof Symbol s) {
            return new MonomialCoeffPair(FlaMonomial.of(s, 1), IntegerAtom.ONE);
        }
        if (term.isHead("Pow")) {
            Expr base = term.getChildren().get(0);
            Expr exp = term.getChildren().get(1);
            if (base instanceof Symbol baseS && exp.isInteger()) {
                if (exp.isNegative()) return null;
                return new MonomialCoeffPair(FlaMonomial.of(baseS, exp), IntegerAtom.ONE);
            }
        }
        if (term.isHead("Mul")) {
            Map<Symbol, Integer> monomial = new LinkedHashMap<>();
            Expr coeff = IntegerAtom.ONE;
            for (var t: term.getChildren()) {
                MonomialCoeffPair parsed = parseTerm(t);
                
            }
        }

        return null;
    }
}
