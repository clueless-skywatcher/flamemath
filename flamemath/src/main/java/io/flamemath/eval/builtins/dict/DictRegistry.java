package io.flamemath.eval.builtins.dict;

import io.flamemath.eval.FunctionRegistry;

public class DictRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new DictFunc());
        registry.register(new KeysFunc());
        registry.register(new ValuesFunc());
        registry.register(new LookupFunc());
        registry.register(new HasKeyFunc());
        registry.register(new DictSetFunc());
        registry.register(new DictRemoveFunc());
        registry.register(new MergeFunc());

        return registry;
    }
}
