// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp.builtin;

import org.genyris.core.Exp;
import org.genyris.core.SimpleSymbol;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public class DynamicSymbolValueFunction extends ApplicableFunction {

	public DynamicSymbolValueFunction(Interpreter interp) {
		super(interp, "dynamic-symbol-value", false);
	}

	public Exp bindAndExecute(Closure proc, Exp[] arguments,
			Environment envForBindOperations) throws GenyrisException {
		if (arguments.length != 1)
			throw new GenyrisException("symbol-value expects one argument.");
		if (!(arguments[0] instanceof SimpleSymbol)) {
			throw new GenyrisException("symbol-value expects a symbol.");
		}
		return envForBindOperations.lookupDynamicVariableValue(arguments[0]);
	}
}
