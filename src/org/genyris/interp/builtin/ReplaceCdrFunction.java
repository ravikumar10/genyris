// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp.builtin;

import org.genyris.core.Exp;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public class ReplaceCdrFunction extends ApplicableFunction {
	
	public static String getStaticName() {return "rplacd" ;};
	public static boolean isEager() {return true;};
	
    public ReplaceCdrFunction(Interpreter interp) {
        super(interp);
    }

    public Exp bindAndExecute(Closure proc, Exp[] argument, Environment envForBindOperations) throws GenyrisException {
        if( argument.length != 2)
            throw new GenyrisException("Too many or few arguments to rplacd: " + argument.length);
        return argument[0].setCdr(argument[1]);
    }

}
