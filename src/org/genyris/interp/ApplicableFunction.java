// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp;

import org.genyris.core.Exp;
import org.genyris.core.Pair;
import org.genyris.core.PairSource;
import org.genyris.core.SimpleSymbol;
import org.genyris.exception.GenyrisException;

public abstract class ApplicableFunction {
    protected Interpreter _interp;

    protected SimpleSymbol NIL, TRUE;
    protected SimpleSymbol _lambda, _lambdam, _lambdaq;
    private String _name;
    private boolean _eager;
    protected SimpleSymbol REST;


    public ApplicableFunction(Interpreter interp, String name, boolean eager) {
        _name = name;
        _eager = eager;
        _interp = interp;
        NIL = interp.getSymbolTable().NIL();
        TRUE = interp.getSymbolTable().TRUE();
        _lambda = interp.getSymbolTable().LAMBDA();
        _lambdaq = interp.getSymbolTable().LAMBDAQ();
        _lambdam = interp.getSymbolTable().LAMBDAM();
        REST = interp.getSymbolTable().REST();
    }

    public Exp bindAndExecuteAux(Closure proc, Exp[] arguments,
            Environment envForBindOperations) throws GenyrisException {
        _interp.debugStackPush(proc);
    	Exp result = bindAndExecute(proc, arguments, envForBindOperations);
    	_interp.debugStackPop(proc);
    	return result;
    }

    public abstract Exp bindAndExecute(Closure proc, Exp[] arguments,
            Environment envForBindOperations) throws GenyrisException;

    protected Exp arrayToList(Exp[] array) {
        Exp expression = NIL;
        for (int i = array.length - 1; i >= 0; i--) {
            if( array[i] instanceof PairSource ) {
                expression = PairSource.clone((PairSource)array[i], expression);
            } else {
                expression = new Pair(array[i], expression);
            }
        }
        return expression;
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return _name;
    }
    
    public boolean isEager() {
        return _eager;
    }

    protected void checkArguments(Exp[] arguments, int exactly)
            throws GenyrisException {
        checkArguments(arguments, exactly, exactly);
    }

    protected void checkMinArguments(Exp[] arguments, int minimum)
            throws GenyrisException {
        if (arguments.length < minimum)
            throw new GenyrisException("Not enough arguments to " + getName()
                    + " was expecting at least " + minimum + ".");
    }

    protected void checkArguments(Exp[] arguments, int minimum, int maximum)
            throws GenyrisException {
        if (arguments.length < minimum || arguments.length > maximum)
            throw new GenyrisException("Incorrect number of arguments to "
                    + getName() + " was expecting between " + minimum + " and "
                    + maximum + ".");
    }

    protected void checkArgumentTypes(Class[] types, Exp[] args)
            throws GenyrisException {
    	if( args.length < types.length ) {
    		throw new GenyrisException(getName() + " not enough arguments.");
    	}
        for (int i = 0; i < types.length; i++) {
            if (!types[i].isInstance(args[i])) {
                throw new GenyrisException(getName() + " expects a "
                        + types[i].getName() + " at position " + i + " got <" + args[i] + "> a " + args[i].getClass().getName());
            }
        }
    }

    protected void checkFormalArgumentSyntax(Exp formals) throws GenyrisException {
        if (formals == NIL) {
            return;
        }
        if (!(formals instanceof Pair)) {
            throw new GenyrisException("Syntax error in " + _name +  ": arguments not a list: " + formals);
        }
        Exp head = formals;
        while (head.isPair() && head != NIL) {
            if (head.car() == REST) {
                if (!head.cdr().isPair())
                    throw new GenyrisException(
                            "Syntax error in " + _name +  ": &rest has no following formal argument: "
                                    + formals);
            }
            head = head.cdr();
        }
    }

}
