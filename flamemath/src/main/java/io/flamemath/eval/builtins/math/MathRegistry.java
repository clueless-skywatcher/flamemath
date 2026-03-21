package io.flamemath.eval.builtins.math;

import io.flamemath.eval.FunctionRegistry;

public class MathRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new NumFunc());
        registry.register(new AbsFunc());
        registry.register(new SqrtFunc());
        registry.register(new ExpFunc());
        registry.register(new LogFunc());
        registry.register(new SinFunc());
        registry.register(new CosFunc());
        registry.register(new TanFunc());

        return registry;
    }
}
