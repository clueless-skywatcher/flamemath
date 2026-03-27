package io.flamemath.eval.builtins.string;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.StringAtom;

public class StrReplaceFunc implements FlameFunction {

    @Override
    public String name() {
        return "StrReplace";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }

        if (!(args.get(0) instanceof StringAtom)
                || !(args.get(1) instanceof StringAtom)
                || !(args.get(2) instanceof StringAtom)) {
            return new Compound(name(), args);
        }

        StringAtom str = (StringAtom) args.get(0);
        StringAtom target = (StringAtom) args.get(1);
        StringAtom replacement = (StringAtom) args.get(2);

        return new StringAtom(str.value().replace(target.value(), replacement.value()));
    }
}
