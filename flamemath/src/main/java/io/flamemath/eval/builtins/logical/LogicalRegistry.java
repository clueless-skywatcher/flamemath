package io.flamemath.eval.builtins.logical;

import io.flamemath.eval.FunctionRegistry;

public class LogicalRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        registry.register(new AndFunc());
        registry.register(new OrFunc());
        registry.register(new NotFunc());
        return registry;
    }
}
