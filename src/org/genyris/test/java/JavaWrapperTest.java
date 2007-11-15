package org.genyris.test.java;

//Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//

import java.io.StringWriter;

import junit.framework.TestCase;

import org.genyris.core.Exp;
import org.genyris.format.BasicFormatter;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;
import org.genyris.io.InStream;
import org.genyris.io.Parser;
import org.genyris.io.StringInStream;
import org.genyris.io.UngettableInStream;
import org.genyris.java.JavaWrapper;

public class JavaWrapperTest extends TestCase {

    private Interpreter _interpreter;
    private Environment _env;

    protected void setUp() throws Exception {
        super.setUp();
        _interpreter = new Interpreter();
        _env = _interpreter.getGlobalEnv();
    }

    void excerciseEval(String exp, String expected) throws Exception {
        InStream input = new UngettableInStream( new StringInStream(exp));
        Parser parser = _interpreter.newParser(input);
        Exp expression = parser.read();
        Exp result = _interpreter.evalInGlobalEnvironment(expression);

        StringWriter out = new StringWriter();
        BasicFormatter formatter = new BasicFormatter(out);
        result.acceptVisitor(formatter);
        assertEquals(expected, out.getBuffer().toString());
    }

    public void testExcerciseEval() throws Exception {
        excerciseEval("(defvar (quote foo) 23)", "23");
        excerciseEval("foo", "23");
    }

    public void testMake() throws Exception {
        String item = "item";
        Exp wrapped = new JavaWrapper(_interpreter.getGlobalEnv(), item);
        _env.defineVariable(_env.internString("item"), wrapped);
        excerciseEval("item", "<JavaObject: item>");
        excerciseEval("(item _classes)", "(<java class java.lang.String> <class JavaObject (Builtin) ()>)");
        excerciseEval("(item _fields)", "(_CASE_INSENSITIVE_ORDER)");
        excerciseEval("(item _methods)", "(_foo)");
    }

}
