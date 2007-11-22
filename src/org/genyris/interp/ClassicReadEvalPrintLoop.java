// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.genyris.core.Constants;
import org.genyris.core.Exp;
import org.genyris.core.Lsymbol;
import org.genyris.exception.AccessException;
import org.genyris.exception.GenyrisException;
import org.genyris.format.IndentedFormatter;
import org.genyris.io.ConvertEofInStream;
import org.genyris.io.InStream;
import org.genyris.io.IndentStream;
import org.genyris.io.Parser;
import org.genyris.io.StdioInStream;
import org.genyris.io.UngettableInStream;

public class ClassicReadEvalPrintLoop {

    public static void main(String[] args) {
        Interpreter interpreter;
        Lsymbol NIL;
        try {
            interpreter = new Interpreter();
            NIL = interpreter.getNil();
            System.out.println("loaded " + interpreter.init(true));
            InStream input = new UngettableInStream(new ConvertEofInStream(
                    new IndentStream(
                            new UngettableInStream(new StdioInStream()), true)));
            Parser parser = interpreter.newParser(input);
            Writer output = new PrintWriter(System.out);
            IndentedFormatter formatter = new IndentedFormatter(output, 1, interpreter);
            System.out.println("\n*** Genyris is listening...");
            Exp expression = null;
            do {
                try {
                    System.out.print("\n> ");
                    expression = parser.read();
                    if (expression.equals(interpreter.getSymbolTable().internString(Constants.EOF))) {
                        System.out.println("Bye..");
                        break;
                    }

                    Exp result = interpreter.evalInGlobalEnvironment(expression);

                    result.acceptVisitor(formatter);

                    output.write(" ;");
                    printClassNames(interpreter, NIL, output, result);
                    output.flush();
                }
                catch (GenyrisException e) {
                    System.out.println("*** Error: " + e.getMessage());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } while (true);
        }
        catch (GenyrisException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(-1);
        }

    }

    private static void printClassNames(Interpreter interpreter, Lsymbol NIL, Writer output, Exp result) throws AccessException, IOException, UnboundException {
        Exp klasses = result.getClasses(interpreter.getGlobalEnv());
        while(klasses != NIL){
            Environment klass = (Environment) klasses.car();
            output.write(" " + klass.lookupVariableShallow(interpreter.getSymbolTable().internString(Constants.CLASSNAME)).toString());
            klasses = klasses.cdr();
        }
    }

}
