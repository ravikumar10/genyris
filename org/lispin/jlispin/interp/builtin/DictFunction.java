package org.lispin.jlispin.interp.builtin;

import org.lispin.jlispin.core.Exp;
import org.lispin.jlispin.core.Dict;
import org.lispin.jlispin.core.Lsymbol;
import org.lispin.jlispin.core.SymbolTable;
import org.lispin.jlispin.interp.ApplicableFunction;
import org.lispin.jlispin.interp.Environment;
import org.lispin.jlispin.interp.LispinException;
import org.lispin.jlispin.interp.AbstractClosure;

public class DictFunction extends ApplicableFunction {

	public Exp bindAndExecute(AbstractClosure proc, Exp[] arguments, Environment env) throws LispinException {
		Dict f = new Dict();
		for(int i= 0; i < arguments.length; i++) {
			if( !arguments[i].listp())
				throw new LispinException("argument to new not a list");
			if( arguments[i].car().getClass() != Lsymbol.class )   
					throw new LispinException("key argument to new not a symbol");
			if(arguments[i].cdr().listp())
				f.add(arguments[i].car(), env.eval(arguments[i].cdr().car())); 
			else
				f.add(arguments[i].car(), SymbolTable.NIL);
		}
		return f;
	}
}