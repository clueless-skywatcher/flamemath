package io.flamemath.eval.builtins.general;

import io.flamemath.eval.FunctionRegistry;

public class GeneralRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        
        registry.register(new HeadFunc());
        registry.register(new AtFunc());
        registry.register(new RawFunc());
        registry.register(new ApplyFunc());
        registry.register(new SubstituteFunc());

        return registry;
    }
}
