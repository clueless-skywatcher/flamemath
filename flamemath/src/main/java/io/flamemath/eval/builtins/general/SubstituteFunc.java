package io.flamemath.eval.builtins.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class SubstituteFunc implements FlameFunction {

    @Override
    public String name() {
        return "Substitute";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 3) {
            throw FlameArityException.atLeast(name(), 3, args.size());
        }
        if (args.size() % 2 == 0) {
            throw new Exception("Substitute requires an odd number of arguments (expr, symbol, value, ...)");
        }

        Map<Symbol, Expr> oldValueMap = new HashMap<>();
        for (int i = 1; i < args.size(); i += 2) {
            if (args.get(i) instanceof Symbol s) {
                oldValueMap.put(s, evaluator.getEnv().get(s));
                evaluator.getEnv().set(s, evaluator.eval(args.get(i + 1)));
            } else {
                throw new Exception(i + "-th element should be a Symbol");
            }
        }
        
        try {
            Expr result = evaluator.eval(args.get(0));
            return result;
        } finally {
            for (var entry: oldValueMap.entrySet()) {
                if (entry.getKey() != entry.getValue()) {
                    evaluator.getEnv().set(entry.getKey(), entry.getValue());
                } else {
                    evaluator.getEnv().clear(entry.getKey());
                }
            }
        }
    }
    
    @Override
    public boolean holdAll() {
        return true;
    }
}
