// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.interp;

import org.genyris.core.Constants;
import org.genyris.core.Exp;
import org.genyris.core.ExpWithEmbeddedClasses;
import org.genyris.core.Lcons;
import org.genyris.core.SimpleSymbol;
import org.genyris.exception.AccessException;
import org.genyris.exception.GenyrisException;

public abstract class AbstractClosure extends ExpWithEmbeddedClasses implements Closure {

    Environment _env;
    Exp _lambdaExpression;
    final ApplicableFunction _functionToApply;
    protected int _numberOfRequiredArguments;
    SimpleSymbol NIL, REST;
    Exp _returnClass;

    public AbstractClosure(Environment environment, Exp expression, ApplicableFunction appl)
            throws GenyrisException {
        _env = environment;
        _lambdaExpression = expression;
        _functionToApply = appl;
        _numberOfRequiredArguments = -1;
        NIL = environment.getNil();
        _returnClass = null;
        REST = environment.getInterpreter().getSymbolTable().internString(Constants.REST); // TOD
        // performance
    }

    private int countFormalArguments(Exp exp) throws AccessException {
        int count = 0;
        while (exp != NIL) {
            if (!(exp instanceof Lcons)) { // ignore trailing type specification
                break;
            }
            if (((Lcons) exp).car() == REST) {
                // count += 1;
                break;
            }
            count += 1;
            exp = exp.cdr();
        }
        return count;
    }

    public Exp getArgumentOrNIL(int index) throws GenyrisException  {
        try {
            return _lambdaExpression.cdr().car().nth(index, NIL);
        }
        catch( AccessException e) {
            throw new GenyrisException("Additional argument to function " + _lambdaExpression);
        }
    }

    public Object getJavaValue() {
        return "<" + this._functionToApply.toString() + ">";
    }

    public Exp getBody() throws AccessException {
        return _lambdaExpression.cdr().cdr();
    }

    public abstract Exp[] computeArguments(Environment env, Exp exp) throws GenyrisException;

    public Exp applyFunction(Environment environment, Exp[] arguments) throws GenyrisException {
        return _functionToApply.bindAndExecute(this, arguments, environment); // double
        // dispatch
    }

    public Environment getEnv() {
        return _env;
    }

    public int getNumberOfRequiredArguments() throws AccessException {
        if (_numberOfRequiredArguments < 0) {
            _numberOfRequiredArguments = countFormalArguments(_lambdaExpression.cdr().car());
        }
        return _numberOfRequiredArguments;
    }

    public String getName() {
        return _functionToApply.getName();
    }

    public Exp lastArgument(Exp args) throws AccessException {
        if (args == NIL)
            return NIL;
        Exp tmp = args;
        while (tmp.cdr() != NIL) {
            if (!(tmp.cdr() instanceof Lcons)) {
                break;
            }
            tmp = tmp.cdr();
        }
        return tmp.car();
    }

    public Exp getLastArgumentOrNIL() throws AccessException {
        Exp args = _lambdaExpression.cdr().car();
        // TODO - clean up
        return lastArgument(args);

    }

    public Exp getReturnClassOrNIL() throws GenyrisException {
        if (_returnClass != null) {
            return _returnClass;
        }
        Exp args = _lambdaExpression.cdr().car();
        Exp returnTypeSymbol = NIL;
        _returnClass = NIL;
        if (args != NIL) {
            Exp tmp = args;
            while (tmp.cdr() != NIL) { // TODO refactor this loop into
                // constructor for better performance?
                if (!(tmp.cdr() instanceof Lcons)) {
                    returnTypeSymbol = tmp.cdr();
                    _returnClass = _env.lookupVariableValue(returnTypeSymbol);
                    break;
                }
                tmp = tmp.cdr();
            }
        }
        return _returnClass;
    }

}