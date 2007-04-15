package org.lispin.jlispin.interp;

import org.lispin.jlispin.core.Exp;
import org.lispin.jlispin.core.SymbolTable;

public class EagerProcedure extends Procedure  {
	// I DO evaluate my arguments before being applied.

	public EagerProcedure(Environment environment, Exp expression, ApplicableFunction appl) {
		super( environment,  expression,  appl);
	}
		
	public Exp[] computeArguments(Environment env, Exp exp) throws LispinException {
		int i = 0;
		Exp[] result = new Exp[exp.length()];
		while( exp != SymbolTable.NIL) {
			result[i] = env.eval(exp.car());
			exp = exp.cdr();
			i++;
		}
		return result;
	}

}