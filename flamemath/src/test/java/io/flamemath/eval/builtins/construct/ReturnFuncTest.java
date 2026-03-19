package io.flamemath.eval.builtins.construct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.exceptions.ReturningException;

public class ReturnFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Return", new ReturnFunc().name());
    }

    @Test
    void topLevelReturnThrowsException() {
        assertThrows(ReturningException.class, () -> fm.execute(
            "Return(42)"
        ));
    }

    @Test
    void arityExceptions() {
        assertThrows(FlameArityException.class, () -> fm.execute(
            "Return()"
        ));
        assertThrows(FlameArityException.class, () -> fm.execute(
            "Return(1, 2)"
        ));
    }

    @Test
    void returnInsideLambda() throws Exception {
        fm.assertExec("6", """
        {
            F = (x) => Return(x + 1);
            F(5)
        }
        """);
        fm.assertExec("42", """
        {
            F = () => 42;
            F()
        }
        """);
    }

    @Test
    void earlyExitFromSeq() throws Exception {
        fm.assertExec("5", """
        {
            F = () => {
                Return(5);
                25
            };
            F()
        }
        """);
    }

    @Test
    void earlyExitInWhile() throws Exception {
        fm.assertExec("25", """
        {
            F = (x) => {
                While(True, {
                    If(x == 5, Return(x ^ 2));
                    x = x + 1
                })
            };
            F(5)
        }
        """);
    }

    @Test
    void insideNestedLambdas() throws Exception {
        fm.assertExec("10", """
        {
            f = () => {
                g = () => {
                    Return(10);
                    25
                };
                g() 
            };
            f()
        }
        """);
    }

    @Test
    void environmentRestoredAfterReturn() throws Exception {
        fm.assertExec("1", """
        {
            x = 1;
            f = () => {
                x = 2;
                Return(5)
            };
            f();
            x
        }
        """);
        fm.assertExec("10", """
        {
            f = (x) => Return(x);
            f(5);
            f(10)
        }
        """); 
    }

    @Test
    void returnWithEvaluatedExpressions() throws Exception {
        fm.assertExec("5", """
        {
            f = () => Return(2 + 3);
            f()
        }
        """);
        fm.assertExec("False", """
        {
            f = () => Return(Or(False, False));
            f()
        }
        """);
    }

    @Test
    void returnWithAccumulation() throws Exception {
        fm.assertExec("15", """
        {
            SumTillN = (n) => {
                sum = 0;
                i = 1;
                While(True, {
                    sum = sum + i;
                    i = i + 1;
                    If(i > n, Return(sum))
                })    
            };
            SumTillN(5)
        }
        """);
    }
}
