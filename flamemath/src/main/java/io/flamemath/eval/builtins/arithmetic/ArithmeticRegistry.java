package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.eval.FunctionRegistry;

public class ArithmeticRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        registry.register(new AddFunc());
        registry.register(new MulFunc());
        registry.register(new PowFunc());
        return registry;
    }
}
