package io.flamemath.eval.builtins.general;

import io.flamemath.eval.FunctionRegistry;
import io.flamemath.eval.builtins.construct.IfFunc;
import io.flamemath.eval.builtins.construct.SeqFunc;
import io.flamemath.eval.builtins.construct.SetFunc;
import io.flamemath.eval.builtins.construct.WhileFunc;

public class GeneralRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        registry.register(new HeadFunc());
        registry.register(new SeqFunc());
        registry.register(new SetFunc());
        registry.register(new IfFunc());
        registry.register(new WhileFunc());
        return registry;
    }
}
