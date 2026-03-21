package io.flamemath.eval.builtins.system;

import io.flamemath.eval.FunctionRegistry;

public class SystemRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new ExitFunc());
        registry.register(new PrintLnFunc());
        registry.register(new EchoFunc());
        
        return registry;
    }
}
