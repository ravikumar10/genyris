// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.test.core;

import java.io.StringWriter;

import junit.framework.TestCase;

import org.genyris.core.Bignum;
import org.genyris.core.EscapedSymbol;
import org.genyris.core.Exp;
import org.genyris.core.FullyQualifiedSymbol;
import org.genyris.core.Ldouble;
import org.genyris.core.Lstring;
import org.genyris.core.NilSymbol;
import org.genyris.core.SimpleSymbol;
import org.genyris.core.SymbolTable;
import org.genyris.exception.GenyrisException;
import org.genyris.format.BasicFormatter;
import org.genyris.format.Formatter;
import org.genyris.interp.Interpreter;
import org.genyris.io.InStream;
import org.genyris.io.Lex;
import org.genyris.io.Parser;
import org.genyris.io.StringInStream;
import org.genyris.io.UngettableInStream;

public class LexTest extends TestCase {

    public SymbolTable _table = new SymbolTable();

    public void setUp() {
        _table.init(null);
    }

    private void excerciseNextTokenInt(Exp expected, String toparse) throws GenyrisException {
        _table.init(null);
        Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
        assertEquals(expected, lexer.nextToken());
    }

    private void excerciseNextTokenDouble(Exp expected, String toparse) throws GenyrisException {
        _table.init(null);
        Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
        assertEquals(((Double)expected.getJavaValue()).doubleValue(), ((Double)lexer.nextToken().getJavaValue()).doubleValue(), 0.00001);
    }

    private void excerciseNextTokenBignum(Exp expected, String toparse) throws GenyrisException {
        _table.init(null);
        Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
        assertEquals(expected.getJavaValue().toString(), lexer.nextToken().getJavaValue().toString());
    }
    private void excerciseNextTokenExp2Times(Exp expected, String toparse) throws GenyrisException {
        _table.init(null);
        Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
        lexer.nextToken();
        assertEquals(expected.toString(), lexer.nextToken().toString());
    }

    private void excerciseNextTokenExp(Exp expected, String toparse) throws GenyrisException {
        _table.init(null);
        Lex lexer = new Lex(new UngettableInStream( new StringInStream(toparse)), _table);
        Exp result = lexer.nextToken();
        assertEquals(expected.getClass(), result.getClass());
        assertEquals(expected.toString(), result.toString());
    }

    public void testLex1() throws Exception {

        excerciseNextTokenInt(new Bignum("12"), "12");
        excerciseNextTokenInt(new Bignum("-12"), "-12");
        excerciseNextTokenBignum(new Bignum("12.34"), "12.34");
        excerciseNextTokenBignum(new Bignum("-12.34"), "-12.34");
        excerciseNextTokenDouble(new Ldouble(12.34e5), "12.34e5");
        excerciseNextTokenDouble(new Ldouble(-12.34e-5), "-12.34e-5");
        excerciseNextTokenDouble(new Ldouble(-12e-5), "-12.0e-5");
    }

    public void testLex2() throws Exception {

        excerciseNextTokenInt(new Bignum("12"), "   12");
        excerciseNextTokenInt(new Bignum("-12"), "\t\t-12");
        excerciseNextTokenBignum(new Bignum("12.34"), "\n\n12.34");
        excerciseNextTokenBignum(new Bignum("-12.34"), "\r\f -12.34");

    }

    public void testLexIdent1() throws Exception {

        excerciseNextTokenExp(new SimpleSymbol("foo"), "foo");
        excerciseNextTokenExp(new SimpleSymbol("foo*bar"), "foo\\*bar");
        excerciseNextTokenExp(new SimpleSymbol("quux"), "\n\nquux");
        excerciseNextTokenExp(new EscapedSymbol("123"), "  \t|123|");
        excerciseNextTokenExp(new SimpleSymbol("dynamic-symbol-value"), "  \t !x");

    }
    public void testLexIdentEscaped() throws Exception {

		try {
			excerciseNextTokenExp2Times(new SimpleSymbol("q"), "|werw\t|q|");
						fail();
		} catch (GenyrisException ignore) { }
		excerciseNextTokenExp2Times(new EscapedSymbol("q"), "|werw| |q|");

		
		excerciseNextTokenExp2Times(new SimpleSymbol("q|"), "|werw |q|");
		excerciseNextTokenExp(new EscapedSymbol("with a space in it"), "|with a space in it|");
		excerciseNextTokenExp(new FullyQualifiedSymbol("http://foo/bar space/#123"), "|http://foo/bar space/#123|");
		excerciseNextTokenExp(new FullyQualifiedSymbol("http://foo/bar%20space/#123"), "|http://foo/bar%20space/#123|");

		excerciseNextTokenExp(new EscapedSymbol("foo"), "|foo|");
        excerciseNextTokenExp(new EscapedSymbol("foo*bar"), "|foo\\*bar|");
        excerciseNextTokenExp(new EscapedSymbol("quux"), "\n\n|quux|");
        excerciseNextTokenExp(new EscapedSymbol("123"), "  \t|123|");
        excerciseNextTokenExp(new FullyQualifiedSymbol("http://foo/bar#123"), "|http://foo/bar#123|");
        excerciseNextTokenExp(new FullyQualifiedSymbol("http://foo/b|ar#123"), "|http://foo/b\\|ar#123|");

    }
    public void testLexIdentMinus() throws Exception {
        excerciseNextTokenExp(new SimpleSymbol("-"), "- 3");
        excerciseNextTokenExp(new SimpleSymbol("-f"), "-f");
        excerciseNextTokenExp(new SimpleSymbol("--"), "--");
    }
    public void testLexCommentStrip() throws Exception {
        excerciseNextTokenExp(new SimpleSymbol("X"), "X ; foo");
        excerciseNextTokenExp(new SimpleSymbol("Y"), "; stripped \nY");
        excerciseNextTokenExp(new Bignum(12), "   \n\t\f      ; stripped \n12");
        }


    public void testLexString() throws Exception {
        excerciseNextTokenExp(new Lstring("str"), "\"str\"");
        excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
        excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
        excerciseNextTokenExp(new Lstring("s\nr"), "\"s\nr\"");
        excerciseNextTokenExp(new Lstring("\n\t\f\r\\"), "\"\n\t\f\r\\\\\"");
        excerciseNextTokenExp(new Lstring("s1-"), "\"\\s\\1\\-\"");
    }

    public void testCombination1() throws Exception {
        Lex lexer = new Lex(new UngettableInStream( new StringInStream("int 12 double\n 12.34\r\n -12.34e5 \"string\" ")), _table);
        assertEquals(new SimpleSymbol("int").getJavaValue(), lexer.nextToken().getJavaValue());
        assertEquals(new Bignum("12"), lexer.nextToken());
        assertEquals(new SimpleSymbol("double").getJavaValue(), lexer.nextToken().getJavaValue());
        assertEquals(new Bignum("12.34"), lexer.nextToken());
        assertEquals(new Ldouble(-12.34e5), lexer.nextToken());
        assertEquals(new Lstring("string"), lexer.nextToken());

    }

    private void excerciseListParsing(String toParse) throws Exception {
        SimpleSymbol NIL = new NilSymbol();
        SymbolTable table = new SymbolTable();
        table.init(NIL);
        InStream input = new UngettableInStream( new StringInStream(toParse));
        Parser parser = new Parser(table, input);
        Exp result = parser.read();

        StringWriter out = new StringWriter();
        Formatter formatter = new BasicFormatter(out);
        result.acceptVisitor(formatter);
        assertEquals(toParse, out.getBuffer().toString());

    }

    private void excerciseListParsingLisp(String toParse, String expected) throws Exception {
        SimpleSymbol NIL = new NilSymbol();
        SymbolTable table = new SymbolTable();
        table.init(NIL);
        InStream input = new UngettableInStream( new StringInStream(toParse));
        Parser parser = new Parser(table, input, '.');
        Exp result = parser.read();

        StringWriter out = new StringWriter();
        Formatter formatter = new BasicFormatter(out);
        result.acceptVisitor(formatter);
        assertEquals(expected, out.getBuffer().toString());

    }


    public void testLists1() throws Exception {
        excerciseListParsing("(1 2 3)");
        excerciseListParsing("(1 (2) 3)");
        excerciseListParsing("(1 (2) 3 (4 (5 (6))))");

        excerciseListParsing("(1 : 2)");
        excerciseListParsing("(1 2 : 3)");

        excerciseListParsing("(\"a\" 1.2 30000 foo)");
        excerciseListParsing("(\"a\" 1.2 30000 foo (1 2 : 3))");
        excerciseListParsing("(\"a\" 1.2 30000 foo (1 2 : 3) (1 (2) 3 (4 (5 (6)))))");
        excerciseListParsing("(defun my-func (x) (cons x x))");
    }

    public void testListsLisp() throws Exception {

        excerciseListParsingLisp("(1 . 2)", "(1 : 2)");
        excerciseListParsingLisp("(1 2 . 3)", "(1 2 : 3)");

        excerciseListParsingLisp("(\"a\" 1.2 30000 foo)"
                               , "(\"a\" 1.2 30000 foo)");
        excerciseListParsingLisp("(\"a\" 1.2 30000 foo (1 2 . 3))"
                            , "(\"a\" 1.2 30000 foo (1 2 : 3))");
        excerciseListParsingLisp("(\"a\" 1.2 30000 foo (1 2 . 3) (1 (2) 3 (4 (5 (6)))))"
                               , "(\"a\" 1.2 30000 foo (1 2 : 3) (1 (2) 3 (4 (5 (6)))))");
    }

    private void excerciseSpecialParsing(String toParse, String expected) throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.init(false);

        InStream input = new UngettableInStream( new StringInStream(toParse));
        Parser parser = new Parser(interpreter.getSymbolTable(), input);
        Exp result = parser.read();

        StringWriter out = new StringWriter();
        Formatter formatter = new BasicFormatter(out);
        result.acceptVisitor(formatter);
        assertEquals(expected, out.getBuffer().toString());

    }

    public void testSpecialLexQuote() throws Exception {
        excerciseSpecialParsing("'a", "(quote a)");
        excerciseSpecialParsing("'12.34", "(quote 12.34)");
        excerciseSpecialParsing("'\"str\"", "(quote \"str\")");
        excerciseSpecialParsing("'(1 2)", "(quote (1 2))");
        excerciseSpecialParsing("'(1 : 2)", "(quote (1 : 2))");
        // excerciseSpecialParsing("'(: 2)", "(quote (1 : 2))");
        // excerciseSpecialParsing("'(:)", "(quote (1 : 2))");
        // excerciseSpecialParsing(":", "raises error");
        excerciseSpecialParsing("'(1:2)", "(quote (1 : 2))");
        excerciseSpecialParsing("'(1 :2)", "(quote (1 : 2))");
        excerciseSpecialParsing("'(a:b)", "(quote (a : b))");
        excerciseSpecialParsing("'(a :b)", "(quote (a : b))");
        excerciseSpecialParsing("'(a: b)", "(quote (a : b))");
        excerciseSpecialParsing("'(a: (3))", "(quote (a : (3)))");
    }

    public void testSpecialLexBackQuote() throws Exception {
        excerciseSpecialParsing("(`a)", "(template a)");
        excerciseSpecialParsing("(`12.34)", "(template 12.34)");
        excerciseSpecialParsing("(`\"str\")", "(template \"str\")");
        excerciseSpecialParsing("(`(1 2))", "(template (1 2))");
        excerciseSpecialParsing("(`(1 : 2))", "(template (1 : 2))");
        excerciseSpecialParsing("(``(1 : 2))", "(template template (1 : 2))");
        excerciseSpecialParsing("(`(1 : 2)`)", "(template (1 : 2) template)");
    }

    public void testSpecialLexComma() throws Exception {
        excerciseSpecialParsing(",a", "(comma a)");
    }

    public void testSpecialLexCommaAt() throws Exception {
        excerciseSpecialParsing(",@12", "(comma-at 12)");
    }

}
