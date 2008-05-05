// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;

import java.util.HashMap;
import java.util.Map;

import org.genyris.exception.GenyrisException;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;

public class SymbolTable {

    private Map _table;
    private Lsymbol NIL;
    private Interpreter _interp;
    private Environment _globalEnv;

    public SymbolTable(Interpreter interp) {
        _table = new HashMap();
        _interp = interp;
        if(_interp != null ) {
            _globalEnv = _interp.getGlobalEnv();
        }
    }

    public void init(Lsymbol nil) throws GenyrisException {
        NIL = nil;
        _table.put("nil", NIL);
        _table.put(Constants.SELF, new Lsymbol(Constants.SELF));
        _table.put(Constants.CLASSES, new Lsymbol(Constants.CLASSES));
        _table.put(Constants.SUPERCLASSES, new Lsymbol(Constants.SUPERCLASSES));
        _table.put(Constants.CLASSNAME, new Lsymbol(Constants.CLASSNAME));
        _table.put(Constants.VARS, new Lsymbol(Constants.VARS));
        ((Lsymbol)_table.get(Constants.SELF)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.CLASSES)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.SUPERCLASSES)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.CLASSNAME)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.VARS)).initFromTable(this);
    }

    public Lsymbol lookupString(String newSym) throws GenyrisException {
        if (_table.containsKey(newSym)) {
            return (Lsymbol)_table.get(newSym);
        } else {
            throw new GenyrisException("symbol not found in symbol table " + newSym);
        }
    }

    public Lsymbol internString(String newSym) {
        if (_table.containsKey(newSym)) {
            return (Lsymbol)_table.get(newSym);
        } else {
            Lsymbol sym = new Lsymbol(newSym);
            try {
                sym.initFromTable(this);
                sym.setParent(_globalEnv);
            }
            catch (GenyrisException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(-1);
            }
            _table.put(newSym, sym);
            return sym;
        }
    }

    public void intern(Exp newSym) throws Exception {
        if (_table.containsKey(((Lsymbol)newSym).getPrintName())) {
            throw new GenyrisException("Can't intern symbol - already exists.");
        } else {
            _table.put(((Lsymbol)newSym).getPrintName(), newSym);
        }
    }

    public Exp getNil() {
        return NIL;
    }
}
