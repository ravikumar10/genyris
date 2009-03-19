// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp;

import org.genyris.core.Exp;
import org.genyris.core.Pair;
import org.genyris.core.Symbol;
import org.genyris.exception.GenyrisException;

public abstract class ApplicableFunction {
    protected Interpreter _interp;
    protected Symbol     NIL, TRUE;
    protected Exp         _lambda, _lambdam, _lambdaq;
    private String  _name;
    private boolean _eager;
	protected Exp REST;
    
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

    public abstract Exp bindAndExecute(Closure proc, Exp[] arguments,
            Environment envForBindOperations) throws GenyrisException;

	protected Exp arrayToList(Exp[] array) {
        Exp expression = NIL;
        for (int i = array.length - 1; i >= 0; i--) {
            expression = new Pair(array[i], expression);
        }
        return expression;
    }

	public String getName() {
		return _name;
	}

	public boolean isEager() {
		return _eager;
	}
	protected void checkArguments(Exp[] arguments, int exactly) throws GenyrisException {
		checkArguments(arguments, exactly,exactly);
	}
	protected void checkMinArguments(Exp[] arguments, int minimum) throws GenyrisException {
        if( arguments.length < minimum)
            throw new GenyrisException("Not enough arguments to " + getName() + " was expecting at least " + minimum + "." );
	}
	protected void checkArguments(Exp[] arguments, int minimum, int maximum) throws GenyrisException {
        if( arguments.length < minimum ||  arguments.length > maximum)
            throw new GenyrisException("Incorrect number of arguments to " + getName() + " was expecting between " + minimum + " and " + maximum + "." );
	}
	protected void checkArgumentTypes(Class[] types, Exp[] args) throws GenyrisException {
		for(int i=0; i< types.length; i++) {
			if (!types[i].isInstance(args[i])) {
				throw new GenyrisException(getName() + " expects a " + types[i].getName() + " at position " + i);
			}			
		}
	}

}
