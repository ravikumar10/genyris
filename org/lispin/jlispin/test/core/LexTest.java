package org.lispin.jlispin.test.core;

import org.lispin.jlispin.core.Exp;
import org.lispin.jlispin.core.InStream;
import org.lispin.jlispin.core.Ldouble;
import org.lispin.jlispin.core.Lex;
import org.lispin.jlispin.core.LexException;
import org.lispin.jlispin.core.Linteger;
import org.lispin.jlispin.core.Lstring;
import org.lispin.jlispin.core.ParseException;
import org.lispin.jlispin.core.Parser;
import org.lispin.jlispin.core.StringInStream;
import org.lispin.jlispin.core.Lsymbol;
import org.lispin.jlispin.core.SymbolTable;
import org.lispin.jlispin.core.UngettableInStream;

import junit.framework.TestCase;

public class LexTest extends TestCase {
	
	public SymbolTable _table = new SymbolTable();
	
	private void excerciseNextTokenInt(Exp expected, String toparse) throws LexException {
		Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
		assertEquals(expected, lexer.nextToken());		
	}
	
	private void excerciseNextTokenDouble(Exp expected, String toparse) throws LexException {
		Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
		assertEquals(((Double)expected.getJavaValue()).doubleValue(), ((Double)lexer.nextToken().getJavaValue()).doubleValue(), 0.00001);		
	}

	private void excerciseNextTokenExp(Exp expected, String toparse) throws LexException {
		Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
		assertEquals(expected, lexer.nextToken());		
	}

	public void testLex1() throws Exception {

		excerciseNextTokenInt(new Linteger(12), "12");
		excerciseNextTokenInt(new Linteger(-12), "-12");
		excerciseNextTokenDouble(new Ldouble(12.34), "12.34");
		excerciseNextTokenDouble(new Ldouble(-12.34), "-12.34");
		excerciseNextTokenDouble(new Ldouble(12.34e5), "12.34e5");
		excerciseNextTokenDouble(new Ldouble(-12.34e-5), "-12.34e-5");
		excerciseNextTokenDouble(new Ldouble(-12e-5), "-12.0e-5");		
	}

	public void testLex2() throws Exception {

		excerciseNextTokenInt(new Linteger(12), "   12");
		excerciseNextTokenInt(new Linteger(-12), "\t\t-12");
		excerciseNextTokenDouble(new Ldouble(12.34), "\n\n12.34");
		excerciseNextTokenDouble(new Ldouble(-12.34), "\r\f -12.34");

	}

	public void testLexIdent1() throws Exception {

		excerciseNextTokenExp(new Lsymbol("foo"), "foo");
		excerciseNextTokenExp(new Lsymbol("foo*bar"), "foo\\*bar");
		excerciseNextTokenExp(new Lsymbol("quux"), "\n\nquux");
		excerciseNextTokenExp(new Lsymbol("|123|"), "  \t|123|");

	}
	public void testLexIdentMinus() throws Exception {
		excerciseNextTokenExp(new Lsymbol("-f"), "-f");
		excerciseNextTokenExp(new Lsymbol("--"), "--");
	}
	public void testLexCommentStrip() throws Exception {
		excerciseNextTokenExp(new Lsymbol("X"), "X ; foo");
		excerciseNextTokenExp(new Lsymbol("Y"), "; stripped \nY");
		excerciseNextTokenExp(new Linteger(12), "   \n\t\f      ; stripped \n12");
		}


	public void testLexString() throws Exception {
		excerciseNextTokenExp(new Lstring("str"), "\"str\"");
		excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
		excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
		excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
		excerciseNextTokenExp(new Lstring("\n\t\f\r\\"), "\"\n\t\f\r\\\\\"");
		excerciseNextTokenExp(new Lstring("a1-"), "\"\\a\\1\\-\"");
	}
	
	public void testCombination1() throws Exception {
		Lex lexer = new Lex(new UngettableInStream( new StringInStream("int 12 double\n 12.34\r\n -12.34e5 \"string\" ")), _table);
		assertEquals(new Lsymbol("int"), lexer.nextToken());				
		assertEquals(new Linteger(12), lexer.nextToken());					
		assertEquals(new Lsymbol("double"), lexer.nextToken());					
		assertEquals(new Ldouble(12.34), lexer.nextToken());					
		assertEquals(new Ldouble(-12.34e5), lexer.nextToken());	
		assertEquals(new Lstring("string"), lexer.nextToken());	
		
	}

	private void excerciseListParsing(String toParse) throws LexException, ParseException {
		SymbolTable table = new SymbolTable();
		InStream input = new UngettableInStream( new StringInStream(toParse));
		Parser parser = new Parser(table, input);
		Exp result = parser.read();
		assertEquals(toParse, result.toString());
	}

	
	public void testLists1() throws Exception {
		excerciseListParsing("(1 2 3)"); 
		excerciseListParsing("(1 (2) 3)"); 
		excerciseListParsing("(1 (2) 3 (4 (5 (6))))"); 		

		excerciseListParsing("(1 . 2)"); 
		excerciseListParsing("(1 . (2 . 3))");	
		
		excerciseListParsing("(\"a\" 1.2 30000 foo)"); 
		excerciseListParsing("(\"a\" 1.2 30000 foo (1 . (2 . 3)))"); 
		excerciseListParsing("(\"a\" 1.2 30000 foo (1 . (2 . 3)) (1 (2) 3 (4 (5 (6)))))");		
		excerciseListParsing("(defun my-func (x) (cons x x))");	
	}

	
}