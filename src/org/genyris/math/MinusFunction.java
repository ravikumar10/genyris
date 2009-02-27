// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.math;

import java.math.BigDecimal;

import org.genyris.core.Bignum;
import org.genyris.core.Exp;
import org.genyris.core.Lsymbol;
import org.genyris.interp.Interpreter;

public class MinusFunction extends AbstractMathFunction {

    public MinusFunction(Interpreter interp, Lsymbol name) {
        super(interp, name, 1);
    }

    protected Exp mathOperation(Exp a) {
        return new Bignum(((BigDecimal)a.getJavaValue()).negate());
    }

    protected Exp mathOperation(Exp a, Exp b) {
        return new Bignum(((BigDecimal) a.getJavaValue()).subtract((BigDecimal) b.getJavaValue()));
    }
}
