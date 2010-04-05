package org.genyris.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.genyris.core.Exp;
import org.genyris.core.Internable;
import org.genyris.core.Symbol;
import org.genyris.exception.GenyrisException;
import org.genyris.interp.Closure;
import org.genyris.interp.Environment;
import org.genyris.interp.Interpreter;


public class JavaMethod extends AbstractJavaMethod {

	protected Method method;
	protected Class[] params;

	public JavaMethod(Interpreter interp, String name, Method method,
			Class[] params) throws GenyrisException {
		super(interp, name);
		this.method = method;
		this.params = params;
	}

	public Symbol getBuiltinClassSymbol(Internable table) {
		return table.JAVAMETHOD();
	}

	public Exp bindAndExecute(Closure proc, Exp[] arguments,
			Environment envForBindOperations) throws GenyrisException {
		try {
			method.setAccessible(true);
			Object rawResult = method.invoke(getSelfJava(envForBindOperations)
					.getValue(), JavaUtils.toJavaArray(params, arguments, NIL));
			return JavaUtils.javaToGenyris(envForBindOperations, rawResult);

		} catch (IllegalArgumentException e) {

			throw new GenyrisException("Java IllegalArgumentException "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new GenyrisException("Java IllegalAccessException "
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			throw new GenyrisException("Java InvocationTargetException "
					+ e.getMessage());
		}
	}


}