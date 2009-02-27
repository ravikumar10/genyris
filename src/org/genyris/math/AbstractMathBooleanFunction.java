// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.math;

import org.genyris.core.Exp;
import org.genyris.core.Lsymbol;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public abstract class AbstractMathBooleanFunction extends ApplicableFunction {

	private Lsymbol _name;

    public AbstractMathBooleanFunction(Interpreter interp, Lsymbol name) {
        super(interp, name);
		_name = name;
    }

    public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment envForBindOperations) throws GenyrisException {
        if( arguments.length != 2)
            throw new GenyrisException("Not two arguments to " + _name);
        try {
            return mathOperation(arguments[0], arguments[1]);
        }
        catch(RuntimeException e) {
            throw new GenyrisException(e.getMessage());
        }
    }

	protected abstract Exp mathOperation(Exp a, Exp b);
    
}
