package org.lispin.jlispin.test.core;

import junit.framework.TestCase;

import org.lispin.jlispin.core.Exp;
import org.lispin.jlispin.core.Lobject;
import org.lispin.jlispin.core.Linteger;
import org.lispin.jlispin.core.SymbolTable;

public class DictTest extends TestCase {
	
	private SymbolTable _table;
	private Lobject _frame;
	
	protected void setUp() throws Exception {
		super.setUp();
		_table = new SymbolTable();
        _table.init();  
		_frame = new Lobject();
	}
	public void test1() throws Exception {

		Exp a = _table.internString("a");
		assertEquals(false, _frame.hasKey(a));
		_frame.defineVariable(a, new Linteger(12));
		assertEquals(true, _frame.hasKey(a));
		
	}

}
