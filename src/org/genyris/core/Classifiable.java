// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;

import org.genyris.interp.Environment;
import org.genyris.interp.UnboundException;

public interface Classifiable {

    public Exp getClasses(Environment env) throws UnboundException ;
    public void addClass(Exp klass);
    public void removeClass(Exp klass);
    public boolean isTaggedWith(Lobject klass);
    public void setClasses(Exp classList, Exp NIL) throws AccessException;
    public String getBuiltinClassName();

}