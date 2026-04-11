package io.flamemath.eval.builtins.math;

import io.flamemath.eval.FunctionRegistry;

public class MathRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new NumFunc());
        registry.register(new AbsFunc());
        registry.register(new SqrtFunc());
        registry.register(new ExpFunc());
        registry.register(new LogFunc());
        registry.register(new SinFunc());
        registry.register(new CosFunc());
        registry.register(new TanFunc());
        registry.register(new FloorFunc());
        registry.register(new CeilFunc());
        registry.register(new RoundFunc());
        registry.register(new ModFunc());
        registry.register(new ArcSinFunc());
        registry.register(new ArcCosFunc());
        registry.register(new ArcTanFunc());
        registry.register(new SinhFunc());
        registry.register(new CoshFunc());
        registry.register(new TanhFunc());
        registry.register(new ArcTan2Func());
        registry.register(new MultinomialFunc());
        registry.register(new FactorialFunc());

        return registry;
    }
}
