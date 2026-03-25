package io.flamemath.eval.builtins.string;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.StringAtom;

public class StrHasFunc implements FlameFunction {

    @Override
    public String name() {
        return "StrHas";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        if (!(args.get(0) instanceof StringAtom) || !(args.get(1) instanceof StringAtom)) {
            return new Compound(name(), args);
        }

        StringAtom haystack = (StringAtom) args.get(0);
        StringAtom needle = (StringAtom) args.get(1);

        return BooleanAtom.of(haystack.value().contains(needle.value()));
    }
}
