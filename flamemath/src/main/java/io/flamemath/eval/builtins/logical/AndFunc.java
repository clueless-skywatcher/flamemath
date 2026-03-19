package io.flamemath.eval.builtins.logical;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;

public class AndFunc implements FlameFunction {

    @Override
    public String name() {
        return "And";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        List<Expr> remaining = new ArrayList<>();
        for (var arg : args) {
            Expr evaled = evaluator.eval(arg);
            if (evaled.isFalse()) return BooleanAtom.FALSE;
            if (!evaled.isTrue()) remaining.add(evaled);
        }
        if (remaining.isEmpty()) return BooleanAtom.TRUE;
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
