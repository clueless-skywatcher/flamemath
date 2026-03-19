package io.flamemath.eval.builtins.logical;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;

public class OrFunc implements FlameFunction {

    @Override
    public String name() {
        return "Or";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        List<Expr> remaining = new ArrayList<>();
        for (var arg : args) {
            Expr evaled = evaluator.eval(arg);
            if (evaled.isTrue()) return BooleanAtom.TRUE;
            if (!evaled.isFalse()) remaining.add(evaled);
        }
        if (remaining.isEmpty()) return BooleanAtom.FALSE;
        if (remaining.size() == 1) return remaining.get(0);
        return new Compound(name(), remaining);
    }
    
    @Override
    public boolean isFlat() {
        return true;
    }

    @Override
    public boolean holdAll() {
        return true;
    }
}
