package io.flamemath.eval.builtins.poly;

import io.flamemath.eval.FunctionRegistry;

public class PolyRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new VarsFunc());
        registry.register(new DegreeFunc());
        registry.register(new ExpandFunc());

        return registry;
    }
}
