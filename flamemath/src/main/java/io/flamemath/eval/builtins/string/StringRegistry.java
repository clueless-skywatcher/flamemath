package io.flamemath.eval.builtins.string;

import io.flamemath.eval.FunctionRegistry;

public class StringRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new StrLengthFunc());
        registry.register(new ToStrFunc());
        registry.register(new StrJoinFunc());
        registry.register(new StrSplitFunc());
        registry.register(new SubStrFunc());
        
        return registry;
    }
}
