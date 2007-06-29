package org.lispin.jlispin.interp.builtin;

import org.lispin.jlispin.core.Exp;
import org.lispin.jlispin.core.SymbolTable;
import org.lispin.jlispin.interp.ApplicableFunction;
import org.lispin.jlispin.interp.Environment;
import org.lispin.jlispin.interp.LispinException;
import org.lispin.jlispin.interp.AbstractClosure;

public class EqFunction extends ApplicableFunction {

	public Exp bindAndExecute(AbstractClosure proc, Exp[] arguments, Environment envForBindOperations) throws LispinException {
		if( arguments.length != 2)
			throw new LispinException("Too few arguments to EqualsFunction: " + arguments.length);
		if( arguments[0] == arguments[1] )
			return SymbolTable.T;
		else
			return SymbolTable.NIL;			
	}

}