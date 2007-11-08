// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.classes;

import org.genyris.classification.ClassWrapper;
import org.genyris.core.Constants;
import org.genyris.core.Exp;
import org.genyris.core.Lobject;
import org.genyris.core.Lsymbol;
import org.genyris.interp.Environment;
import org.genyris.interp.GenyrisException;

public class BuiltinClasses {

    private static Lobject mkClass(Lsymbol classname, String name, Environment env, Exp STANDARDCLASS, Lobject superClass) throws GenyrisException {
        Exp symbolicName = env.internString(name);
        Lobject newClass = new Lobject(classname, symbolicName, env );
        newClass.addClass(STANDARDCLASS);
        if(superClass != null)
            new ClassWrapper(newClass).addSuperClass(superClass);
        env.defineVariable(symbolicName, newClass);
        return newClass;
    }
    public static void init(Environment env) throws GenyrisException {
        Lobject STANDARDCLASS;
        Lsymbol classname = (Lsymbol) env.internString(Constants.CLASSNAME);
        {
            // Bootstrap the meta-class
            STANDARDCLASS = new Lobject(classname, env.internString(Constants.STANDARDCLASS), env );
            STANDARDCLASS.addClass(STANDARDCLASS);
            env.defineVariable(env.internString(Constants.STANDARDCLASS), STANDARDCLASS);
        }

        Lobject THING = mkClass(classname, "Thing", env, STANDARDCLASS, null);
        mkClass(classname, "Pair", env, STANDARDCLASS, THING);
        mkClass(classname, "Object", env, STANDARDCLASS, THING);
        mkClass(classname, "Integer", env, STANDARDCLASS, THING);
        mkClass(classname, "Bignum", env, STANDARDCLASS, THING);
        mkClass(classname, "String", env, STANDARDCLASS, THING);
        mkClass(classname, "Double", env, STANDARDCLASS, THING);
        mkClass(classname, "Symbol", env, STANDARDCLASS, THING);
        mkClass(classname, "EagerProcedure", env, STANDARDCLASS, THING);
        mkClass(classname, "LazyProcedure", env, STANDARDCLASS, THING);
        mkClass(classname, Constants.PRINTWITHCOLON, env, STANDARDCLASS, THING);
    }
}