package org.lispin.jlispin.test.format;

import java.io.StringWriter;

import junit.framework.TestCase;

import org.genyris.core.Exp;
import org.genyris.format.BasicFormatter;
import org.genyris.interp.Interpreter;
import org.genyris.io.InStream;
import org.genyris.io.Parser;
import org.genyris.io.StringInStream;
import org.genyris.io.UngettableInStream;

public class BasicFormatterTest extends TestCase {

	void excerciseFormatter(String given) throws Exception { 
		Interpreter interpreter = new Interpreter();
		InStream input = new UngettableInStream( new StringInStream(given));
		Parser parser = interpreter.newParser(input);
		Exp expression = parser.read(); 
		StringWriter out = new StringWriter();
		BasicFormatter formatter = new BasicFormatter(out);
		
		expression.acceptVisitor(formatter);
		
		assertEquals(given, out.getBuffer().toString());

	}

	public void test1() throws Exception {
		excerciseFormatter("(1 2.3 \"str\" (symbol))");	
	} 
	public void test2() throws Exception {
		excerciseFormatter("(1 2.3 (nil nil 23 45 : 89))");	
	} 
	public void test3() throws Exception {
		excerciseFormatter("(45 : 89)");	
	} 
}
