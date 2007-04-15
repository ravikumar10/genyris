package org.lispin.jlispin.test.core;

import org.lispin.jlispin.core.LexException;
import org.lispin.jlispin.core.StringInStream;
import org.lispin.jlispin.core.UngettableInStream;

import junit.framework.TestCase;

public class UngettableInStreamTest extends TestCase {

	
	public void testUngettable() {
		UngettableInStream ung  = new UngettableInStream(new StringInStream("123"));
		
		try {
			ung.unGet('a');
			assertEquals('a', ung.lgetc());
			assertEquals('1', ung.lgetc());	
			ung.unGet('b');
			ung.unGet('c');
			ung.unGet('d');
			assertEquals('d', ung.lgetc());	
			assertEquals('c', ung.lgetc());	
			assertEquals('b', ung.lgetc());	
			assertEquals('2', ung.lgetc());	
			assertEquals('3', ung.lgetc());	
		}
		catch (LexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}