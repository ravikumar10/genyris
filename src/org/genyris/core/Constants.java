// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;


public class Constants {

    // Lexical marks
        public static final char COMMENTCHAR = '#';
        public static final char LISPCOMMENTCHAR = ';';
        public static final char BQUOTECHAR = '`';
        public static final char QUOTECHAR = '^';
        public static final char COMMACHAR = ',';
        public static final char ATCHAR = '@';
        public static final char CDRCHAR = '=';
        public static final char LISPCDRCHAR = '.';
        public static final char DYNAMICSCOPECHAR2 = '.';
        public static final char SYMBOLESCAPE = '|';
    // Language Keywords
        public static final String NIL = "nil";
        public static final String CLASSNAME = "classname";
        public static final String CLASSES = "classes";
        public static final String SUPERCLASSES = "superclasses";
        public static final String SUBCLASSES = "subclasses";
        public static final String SELF = "self";
        public static final String VALIDATE = "valid?";
        public static final String LAMBDA = "lambda";
        public static final String LAMBDAQ = "lambdaq";
        public static final String LAMBDAM = "lambdam";
        public static final String DICT = "dict";
        public static final String DICTIONARY = "Dictionary";
        public static final String COMMA = "comma";
        public static final String COMMA_AT = "comma-at";
        public static final String REST = "&rest";
        public static final String PRINTWITHEQ = "PRINTWITHEQ";
        public static final String STANDARDCLASS = "StandardClass";
        public static final String EOF = "EOF";
        public static final String TEMPLATE = "template";
        public static final String SQUARE = "squareBracket";
        public static final String CURLY = "curlyBracket";

        public static final String THING = "Thing";
        public static final String SIMPLESYMBOL = "SimpleSymbol";
        public static final String URISYMBOL = "URISymbol";
        public static final String LAZYPROCEDURE = "LazyProcedure";
        public static final String EAGERPROCEDURE = "EagerProcedure";
        public static final String BIGNUM = "Bignum";
        public static final String PAIR = "Pair";
        public static final String PAIREQUAL = "PairEqual";
        public static final String STRING = "String";
        public static final String JAVAOBJECT = "JavaObject";
        public static final String JAVAMETHOD = "JavaMethod";
        public static final String VARS = "vars";
        public static final String TRUE = "true";
        public static final String CLOSURE = "Closure";
        public static final String LEFT = "left";
        public static final String RIGHT = "right";

        public static final String SPLIT = "split";
        public static final String TOLOWERCASE = "toLowerCase";
        public static final String CONCAT = "+";
        public static final String MATCH = "match";
        public static final String LENGTH = "length";
        public static final String FILE = "File";
        public static final String CSV = "CSV";
        public static final String WRITER = "Writer";
        public static final String SYSTEM = "System";
        public static final String EXEC = "exec";
        public static final String LISTOFLINES = "ListOfLines";
        public static final String READER = "Reader";
        public static final String INDENTEDPARSER = "Parser";
        public static final String STRINGFORMATSTREAM = "StringFormatStream";
        public static final String STDOUT = "stdout";
        public static final String PARENPARSER = "ParenParser";
        public static final String STDIN = "stdin";
        public static final String SOUND = "Sound";
        public static final String PREFIX = "@prefix";
        public static final String DYNAMIC_SYMBOL = "dynamic-symbol-value";
        public static final String GENYRIS = "http://www.genyris.org/lang/";
        public static final String ARGS = "argv";
        public static final String WEB = GENYRIS + "web#";
        public static final String EMAIL = GENYRIS + "mail#";
        public static final String SYMBOL = "Symbol";
        public static final String FALSE = "false";
        public static final String QUOTE = "quote";
        public static final String BACKQUOTE = "backquote";
        public static final String TRIPLE = "Triple";
        public static final String TRIPLESTORE = "Triplestore";
        public static final String TYPE = "type";

        public static final String PREFIX_UTIL = "http://www.genyris.org/lang/utilities#";
        public static final String PREFIX_WEB = "http://www.genyris.org/lang/web#";
        public static final String PREFIX_EMAIL = "http://www.genyris.org/lang/email#";
        public static final String PREFIX_SYNTAX = "http://www.genyris.org/lang/syntax#";
        public static final String PREFIX_SYSTEM = "http://www.genyris.org/lang/system#";
        public static final String PREFIX_VERSION = "http://www.genyris.org/lang/version#";
        public static final String PREFIX_TYPES = "http://www.genyris.org/lang/types#";
        public static final String PREFIX_TASK = "http://www.genyris.org/lang/task#";

        public static final String DESCRIPTIONS = PREFIX_TYPES + "Descriptions";
		public static final String SUBCLASSOF = "subClassOf";
		public static final String DYNAMICSYMBOLREF = "DynamicSymbolRef";
		public static final String FORMAT = "format";
		public static final String NAME = "name";
		public static final String SOURCE = "source";
		public static final String SUBJECT = "subject";
		public static final String PREDICATE = "predicate";
		public static final String OBJECT = "object";
		public static final String TOBASE64 = "toBase64";
		public static final String FROMBASE64 = "fromBase64";

}
