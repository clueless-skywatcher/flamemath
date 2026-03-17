package io.flamemath.eval.builtins.system;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class ExitFunc implements FlameFunction {

    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.isEmpty()) {
            System.exit(0);
        }
        if (args.size() == 1) {                                                                                     
            if (args.get(0) instanceof IntegerAtom intArg) {                                    
                System.exit((int) intArg.value());                                                                  
            }                                                                                                       
        }

        return new Compound("Exit", args);
    }
}
