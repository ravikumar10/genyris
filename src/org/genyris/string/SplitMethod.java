// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.string;

import org.genyris.core.Constants;
import org.genyris.core.Exp;
import org.genyris.core.Lstring;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public class SplitMethod extends AbstractStringMethod {

	public SplitMethod(Interpreter interp) {
		super(interp);
	}

	public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment env)
			throws GenyrisException {
		Lstring regex = new Lstring(" ");
		if(arguments.length > 0) {
			if(!(arguments[0] instanceof Lstring)) {
				throw new GenyrisException("Non string passed to " + Constants.SPLIT);
			}
			regex = (Lstring) arguments[0];			
		}
		Lstring theString = getSelfString(env);
		return theString.split(NIL, regex);
	}
}