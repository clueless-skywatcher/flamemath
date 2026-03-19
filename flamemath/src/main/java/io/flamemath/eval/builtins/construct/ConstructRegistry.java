package io.flamemath.eval.builtins.construct;

import io.flamemath.eval.FunctionRegistry;
import io.flamemath.eval.builtins.general.HeadFunc;

public class ConstructRegistry {
    /** Functions pertaining to constructing code */
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();
        registry.register(new HeadFunc());
        registry.register(new SeqFunc());
        registry.register(new SetFunc());
        registry.register(new IfFunc());
        registry.register(new WhileFunc());
        registry.register(new ReturnFunc());
        return registry;
    }
}
