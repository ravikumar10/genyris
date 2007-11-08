// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.logic;

import org.genyris.core.Exp;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Evaluator;
import org.genyris.interp.Interpreter;
import org.genyris.interp.GenyrisException;

public class AndFunction extends ApplicableFunction {

    public AndFunction(Interpreter interp) {
        super(interp);
    }

    public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment envForBindOperations)
            throws GenyrisException {
        if (arguments.length < 2)
            throw new GenyrisException("Too few arguments to and: " + arguments.length);

        for (int i = 0; i < arguments.length; i++) {
            Exp result = Evaluator.eval(envForBindOperations, arguments[i]);
            if (result == NIL) {
                return NIL;
            }
        }
        return TRUE;

    }

}