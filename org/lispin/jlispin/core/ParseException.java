package org.lispin.jlispin.core;

import org.lispin.jlispin.interp.LispinException;

public class ParseException extends LispinException {

	private static final long serialVersionUID = 3268672144858986389L;

		public ParseException(String string) {
			super(string);
		}

	}
