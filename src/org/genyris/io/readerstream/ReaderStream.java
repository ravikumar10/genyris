// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.io.readerstream;

import java.io.Reader;

import org.genyris.core.Bignum;
import org.genyris.core.Exp;
import org.genyris.core.ExpWithEmbeddedClasses;
import org.genyris.core.Internable;
import org.genyris.core.Symbol;
import org.genyris.core.Visitor;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.AbstractMethod;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;
import org.genyris.io.ConvertEofInStream;
import org.genyris.io.InStream;
import org.genyris.io.InStreamEOF;
import org.genyris.io.ReaderInStream;

public class ReaderStream extends ExpWithEmbeddedClasses {
    private InStream _input;

    public ReaderStream(InStream reader) {
        _input = reader;
    }

    public ReaderStream(InStreamEOF readerEOF) {
        _input = new ConvertEofInStream(readerEOF);
    }

    public ReaderStream(Reader reader) {
        _input = new ReaderInStream(reader);
    }

    public InStream getInStream() {
        return _input;
    }
    public void acceptVisitor(Visitor guest) throws GenyrisException {
        guest.visitExpWithEmbeddedClasses(this);
    }

    public String toString() {
        return _input.toString();
    }

    public void close() throws GenyrisException {
        _input.close();
    }

    public static abstract class AbstractReaderMethod extends AbstractMethod {

        public AbstractReaderMethod(Interpreter interp, String name) {
        	super(interp, name);
        }

        protected ReaderStream getSelfReader(Environment env) throws GenyrisException {
            getSelf(env);
            if (!(_self instanceof ReaderStream)) {
                throw new GenyrisException("Non-Reader passed to a Reader method.");
            } else {
                return (ReaderStream)_self;
            }
        }
    }
    public static class ReadMethod extends AbstractReaderMethod {

        public ReadMethod(Interpreter interp) {
        	super(interp, "read");
        }

        public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment env)
                throws GenyrisException {
            ReaderStream self = getSelfReader(env);
            return new Bignum(self._input.readNext());
        }
    }
    public static class HasDataMethod extends AbstractReaderMethod {

        public HasDataMethod(Interpreter interp) {
        	super(interp, "hasData");
        }

        public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment env)
                throws GenyrisException {
            ReaderStream self = getSelfReader(env);
            return (self._input.hasData() ? TRUE : NIL);
        }
    }
    public static class CloseMethod extends AbstractReaderMethod {

    	public static String getStaticName() {return "close";};
        public CloseMethod(Interpreter interp) {
        	super(interp, getStaticName());
        }

        public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment env)
                throws GenyrisException {
            getSelfReader(env).close();
            return NIL;
        }
    }
	public Symbol getBuiltinClassSymbol(Internable table) {
		return table.READER();
	}

}
