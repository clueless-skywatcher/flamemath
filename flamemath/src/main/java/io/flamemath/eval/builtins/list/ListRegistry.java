package io.flamemath.eval.builtins.list;

import io.flamemath.eval.FunctionRegistry;

public class ListRegistry  {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        
        registry.register(new ListFunc());

        return registry;
    }
}
