// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp;

import org.genyris.core.Exp;

public interface Closure {

    public abstract Exp[] computeArguments(Environment env, Exp exp) throws GenyrisException;

    public abstract Exp applyFunction(Environment environment, Exp[] arguments) throws GenyrisException;

}