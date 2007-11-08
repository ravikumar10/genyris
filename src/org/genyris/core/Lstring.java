// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;


public class Lstring extends ExpWithEmbeddedClasses {

    String _value;

    public Lstring(String str) {
        _value = str;
    }

    public Object getJavaValue() {
        return _value;
    }

    public String toString() {
        return _value;
    }
    public void acceptVisitor(Visitor guest) {
        guest.visitLstring(this);
    }
    public String getBuiltinClassName() {
        return Constants.STRING;
    }

}