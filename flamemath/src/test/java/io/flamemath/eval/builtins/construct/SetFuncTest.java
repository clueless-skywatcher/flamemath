package io.flamemath.eval.builtins.construct;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Normal assignment ---

    @Test
    void assignAndRetrieve() throws Exception {
        fm.assertExec("5", "{ x = 5; x }");
    }

    // --- Protected constants ---

    @Test
    void cannotAssignPi() {
        assertThrows(Exception.class, () -> fm.execute("Pi = 3"));
    }

    @Test
    void cannotAssignE() {
        assertThrows(Exception.class, () -> fm.execute("E = 3"));
    }

    @Test
    void cannotAssignTrue() {
        assertThrows(Exception.class, () -> fm.execute("True = 0"));
    }

    @Test
    void cannotAssignFalse() {
        assertThrows(Exception.class, () -> fm.execute("False = 1"));
    }

    // --- Protected builtin functions ---

    @Test
    void cannotAssignAdd() {
        assertThrows(Exception.class, () -> fm.execute("Add = 5"));
    }

    @Test
    void cannotAssignIf() {
        assertThrows(Exception.class, () -> fm.execute("If = 5"));
    }

    @Test
    void cannotAssignWhile() {
        assertThrows(Exception.class, () -> fm.execute("While = 5"));
    }

    @Test
    void cannotAssignMap() {
        assertThrows(Exception.class, () -> fm.execute("Map = 5"));
    }

    @Test
    void cannotAssignLen() {
        assertThrows(Exception.class, () -> fm.execute("Len = 5"));
    }

    @Test
    void cannotAssignPrintLn() {
        assertThrows(Exception.class, () -> fm.execute("PrintLn = 5"));
    }

    // --- Non-protected names still work ---

    @Test
    void canAssignRegularSymbol() throws Exception {
        fm.assertExec("42", "{ myVar = 42; myVar }");
    }

    @Test
    void canAssignLowercasePi() throws Exception {
        fm.assertExec("3", "{ pi = 3; pi }");
    }
}
