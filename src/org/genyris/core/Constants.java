// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;

public class Constants {

    // Lexical marks
        public static final char COMMENTCHAR = ';';
        public static final char BQUOTECHAR = '`';
        public static final char QUOTECHAR = '\'';
        public static final char COMMACHAR = ',';
        public static final char ATCHAR = '@';
        public static final char CDRCHAR = ':';
        public static final String DYNAMICSCOPECHAR = "_";
    // Language Keywords
        public static final String NIL = "nil";
        public static final String CLASSNAME = DYNAMICSCOPECHAR + "classname";
        public static final String CLASSES = DYNAMICSCOPECHAR + "classes";
        public static final String SUPERCLASSES = DYNAMICSCOPECHAR + "superclasses";
        public static final String SUBCLASSES = DYNAMICSCOPECHAR + "subclasses";
        public static final String SELF = "self";
        public static final String _SELF = DYNAMICSCOPECHAR + SELF;
        public static final String VALIDATE = DYNAMICSCOPECHAR + "valid?";
        public static final String LAMBDA = "lambda";
        public static final String LAMBDAQ = "lambdaq";
        public static final String LAMBDAM = "lambdam";
        public static final String DICT = "dict";
        public static final String COMMA = "comma";
        public static final String COMMA_AT = "comma-at";
        public static final String REST = "&rest";
        public static final String PRINTWITHCOLON = "PRINTWITHCOLON";
        public static final String STANDARDCLASS = "StandardClass";
        public static final String EOF = "EOF";
        public static final String BACKQUOTE = "backquote";

        public static final String THING = "Thing";
        public static final String SYMBOL = "Symbol";
        public static final String LAZYPROCEDURE = "LazyProcedure";
        public static final String EAGERPROCEDURE = "EagerProcedure";
        public static final String BIGNUM = "Bignum";
        public static final String PAIR = "Pair";
        public static final String DOUBLE = "Double";
        public static final String INTEGER = "Integer";
        public static final String STRING = "String";
        public static final String OBJECT = "Object";




}