// Copyright 2008 Peter William Birch <birchb@genyis.org>
//
// This software may be used and distributed according to the terms
// of the Genyris License, in the file "LICENSE", incorporated herein by reference.
//
package org.genyris.core;

import org.genyris.classification.ClassWrapper;
import org.genyris.dl.Triple;
import org.genyris.dl.TripleSet;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.EagerProcedure;
import org.genyris.interp.LazyProcedure;

public interface Visitor {
	public void visitPair(Pair cons) throws GenyrisException;

	public void visitStrinG(StrinG lst) throws GenyrisException;

	public void visitLazyProc(LazyProcedure lproc) throws GenyrisException;

	public void visitEagerProc(EagerProcedure eproc) throws GenyrisException;

	public void visitDictionary(Dictionary frame) throws GenyrisException;

	public void visitBignum(Bignum bignum) throws GenyrisException;

	public void visitClassWrapper(ClassWrapper klass) throws GenyrisException;

	public void visitExpWithEmbeddedClasses(ExpWithEmbeddedClasses exp)
			throws GenyrisException;

	public void visitSimpleSymbol(SimpleSymbol simpleSymbol)
			throws GenyrisException;

	public void visitFullyQualifiedSymbol(URISymbol sym)
			throws GenyrisException;

	public void visitTriple(Triple triple) throws GenyrisException;

	public void visitTripleSet(TripleSet store) throws GenyrisException;
}
