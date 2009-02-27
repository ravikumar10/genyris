// Copyright 2009 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.web;

import java.io.IOException;
import java.math.BigDecimal;

import org.genyris.core.Bignum;
import org.genyris.core.Exp;
import org.genyris.core.Lstring;
import org.genyris.core.Lsymbol;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public class SpawnHTTPDFunction extends ApplicableFunction {
	
	public SpawnHTTPDFunction(Interpreter interp, Lsymbol name) {
		super(interp, name);
	}

	public Exp bindAndExecute(Closure proc, Exp[] arguments,
			Environment envForBindOperations) throws GenyrisException {
		if (arguments.length != 2)
			throw new GenyrisException("Too many or few arguments to " + getName()
					+ arguments.length);
		// TODO: unsafe downcasts
		int port = ((BigDecimal)arguments[0].getJavaValue()).intValue();
		String filename = (String)arguments[1].getJavaValue();
		GenyrisHTTPD httpd1 = new GenyrisHTTPD(port, filename);
		try {
			Thread t = httpd1.run();
			return new Bignum(t.getId());
		} catch (IOException e) {
			return new Lstring(e.getMessage());
		}

	}

}