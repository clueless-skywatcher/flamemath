package io.flamemath.eval.builtins.general;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;

public class SeqFunc implements FlameFunction {

    @Override
    public String name() {
        return "Seq"; 
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        List<Expr> results = new ArrayList<>();
        for (var arg: args) {
            results.add(evaluator.eval(arg));
        }
        if (results.stream().allMatch(r -> r instanceof Flambda)) {
            return new Compound("Seq", results);
        }
        return results.getLast();
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
