package io.flamemath.eval.builtins.ntheory;

import io.flamemath.eval.FunctionRegistry;

public class NumberTheoryRegistry {
    public static FunctionRegistry create() {
        FunctionRegistry registry = new FunctionRegistry();

        registry.register(new GCDFunc());
        registry.register(new LCMFunc());
        registry.register(new IsPrimeFunc());
        registry.register(new PowModFunc());
        registry.register(new PrimeFunc());
        registry.register(new PrimesInRangeFunc());
        registry.register(new PrimeFactorsFunc());

        return registry;
    }
}
