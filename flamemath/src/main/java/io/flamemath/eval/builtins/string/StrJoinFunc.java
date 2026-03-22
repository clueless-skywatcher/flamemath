package io.flamemath.eval.builtins.string;

import java.util.List;
import java.util.StringJoiner;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.StringAtom;

public class StrJoinFunc implements FlameFunction {

    @Override
    public String name() {
        return "StrJoin";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        Expr possibleList = args.get(0);
        if (!(possibleList instanceof ListExpr list)) {
            throw new Exception("First element should be a list");
        }

        for (var str : list.exprs()) {
            if (!(str instanceof StringAtom)) {
                throw new Exception("List must always contain only strings");
            }
        }

        Expr delimiter = args.get(1);
        if (!(delimiter instanceof StringAtom s)) {
            throw new Exception("Second element should be a string");
        }

        StringJoiner joiner = new StringJoiner(s.value());
        for (var str: list.exprs()) {
            joiner.add(((StringAtom) str).value());
        }

        return new StringAtom(joiner.toString());
    }

}
