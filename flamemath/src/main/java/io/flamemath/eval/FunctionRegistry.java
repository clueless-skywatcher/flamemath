package io.flamemath.eval;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FunctionRegistry {
    private final Map<String, FlameFunction> functions = new HashMap<>();

    public void register(FlameFunction fn) {
        functions.put(fn.name(), fn);
    }

    public Optional<FlameFunction> lookup(String name) {
        return Optional.ofNullable(functions.get(name));
    }

    public void registerAll(FunctionRegistry other) {
        functions.putAll(other.functions);
    }

    public boolean has(String name) {
        return functions.containsKey(name);
    }
}
