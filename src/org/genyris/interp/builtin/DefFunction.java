// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp.builtin;

import org.genyris.core.Exp;
import org.genyris.core.Lcons;
import org.genyris.interp.ApplicableFunction;
import org.genyris.interp.ClassicFunction;
import org.genyris.interp.Closure;
import org.genyris.interp.EagerProcedure;
import org.genyris.interp.Environment;
import org.genyris.interp.GenyrisException;
import org.genyris.interp.Interpreter;

public class DefFunction extends ApplicableFunction {

    public DefFunction(Interpreter interp) {
        super(interp);
    }

public Exp bindAndExecute(Closure proc, Exp[] arguments, Environment envForBindOperations)
            throws GenyrisException {
        Exp lambdaExpression = new Lcons(_lambda, arrayToList(arguments).cdr());
        // TODO inefficient
        EagerProcedure fn = new EagerProcedure(envForBindOperations, lambdaExpression, new ClassicFunction(_interp));
        envForBindOperations.defineVariable(arguments[0], fn);
        return fn;
    }

}