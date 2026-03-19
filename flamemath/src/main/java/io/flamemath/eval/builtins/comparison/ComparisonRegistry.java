package io.flamemath.eval.builtins.comparison;

import io.flamemath.eval.FunctionRegistry;

public class ComparisonRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        registry.register(new EqFunc());
        registry.register(new NotEqFunc());
        registry.register(new LessFunc());
        registry.register(new LessEqFunc());
        registry.register(new GreaterFunc());
        registry.register(new GreaterEqFunc());
        return registry;
    }
}
