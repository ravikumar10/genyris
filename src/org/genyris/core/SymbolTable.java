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
    private Map         _table;
    private Lsymbol     NIL;
    private Lsymbol     _resource;
    private Interpreter _interp;
    private Environment _globalEnv;
    private Map         _prefixes;

    public SymbolTable(Interpreter interp) {
        _table = new HashMap();
        _prefixes = new HashMap();
        _interp = interp;
        if (_interp != null) {
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
        _table.put(Constants.DYNAMIC_SYMBOL, new Lsymbol(Constants.DYNAMIC_SYMBOL));
        _resource = new Lsymbol(Constants.RESOURCE);
        _table.put(Constants.RESOURCE, _resource);
        ((Lsymbol)_table.get(Constants.SELF)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.CLASSES)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.SUPERCLASSES)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.CLASSNAME)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.RESOURCE)).initFromTable(this);
        ((Lsymbol)_table.get(Constants.DYNAMIC_SYMBOL)).initFromTable(this);
    }

    public Lsymbol lookupString(String news) throws GenyrisException {
        String newSym = getCannonicalSymbol(news);
        return lookupPlainString(newSym);
    }

    public Lsymbol lookupPlainString(String newSym) throws GenyrisException {
        if (_table.containsKey(newSym)) {
            return (Lsymbol)_table.get(newSym);
        } else {
            throw new GenyrisException("symbol not found in symbol table " + newSym);
        }
    }

    public Lsymbol internString(String news) throws GenyrisException {
        String newSym = getCannonicalSymbol(news);
        return internPlainString(newSym);
    }

    public Lsymbol internPlainString(String newSym) {
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
            Lsymbol canonicalSymbol = sym; 
            if(newSym.startsWith("_")) {
                canonicalSymbol = internPlainString(newSym.substring(1));
            }
            try {
                sym.defineVariable(_resource, canonicalSymbol);
            }
            catch (GenyrisException e) {
                throw new RuntimeException("Internal error - internPlainString unable to define _resource in a symbol.");
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

    public void addprefix(String prefix, String uri) throws GenyrisException {
        if(prefix.startsWith("_")) {
            throw new GenyrisException("cannot start a prefix with underscore in parse: " + prefix);            
        }
        if (_prefixes.containsKey(prefix)) {
            if(!_prefixes.get(prefix).equals(uri)) {
                throw new GenyrisException("conflicting prefix in parse: " + prefix + " " + uri);
            }
        } else {
            _prefixes.put(prefix, uri);
        }
    }

    private static boolean hasPrefix(String symbol) {
        return symbol.contains(".");
    }

    private static String getPrefix(String symbol) {
        return symbol.substring(0, symbol.indexOf("."));
    }

    private static String getSuffix(String symbol) {
        return symbol.substring(symbol.indexOf(".") + 1);
    }

    private String getCannonicalSymbol(String news) throws GenyrisException {
        String prefix;
        String underscore = "";
        if(news.startsWith("_")) { // TODO need a Constant for "_"
            underscore = "_";
            news = news.substring(1);
        }
        if(news.equals(".") || !hasPrefix(news) ) {
            return underscore + news;
        }
        else {
            prefix = getPrefix(news);
            if (!_prefixes.containsKey(prefix)) {
                throw new GenyrisException("Unknown prefix: " + prefix);
            } else {
                return underscore + _prefixes.get(prefix) + getSuffix(news);
            }
        }
    }

    public void initEnvironment(Environment environment) {
        this._globalEnv = environment;
        
    }
}
