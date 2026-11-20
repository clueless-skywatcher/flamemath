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
        registry.register(new GenListFunc());
        registry.register(new SortFunc());
        registry.register(new SliceFunc());
        registry.register(new JoinFunc());
        registry.register(new UnionFunc());
        registry.register(new IntersectionFunc());
        registry.register(new SetAtFunc());
        registry.register(new DeleteFunc());
        registry.register(new DeleteCopyFunc());

        return registry;
    }
}
