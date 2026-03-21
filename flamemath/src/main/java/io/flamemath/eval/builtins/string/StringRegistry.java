package io.flamemath.eval.builtins.string;

import io.flamemath.eval.FunctionRegistry;

public class StringRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new StrLengthFunc());
        registry.register(new ToStrFunc());
        
        return registry;
    }
}
