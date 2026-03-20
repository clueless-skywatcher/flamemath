package io.flamemath.eval.builtins.list;

import io.flamemath.eval.FunctionRegistry;

public class ListRegistry  {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        
        registry.register(new ListFunc());
        registry.register(new LenFunc());
        registry.register(new MapFunc());
        registry.register(new AppendFunc());
        registry.register(new PrependFunc());
        registry.register(new ExtendFunc());
        registry.register(new RangeFunc());

        return registry;
    }
}
