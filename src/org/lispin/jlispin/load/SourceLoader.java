package org.lispin.jlispin.load;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.genyris.core.Constants;
import org.genyris.core.Exp;
import org.genyris.format.IndentedFormatter;
import org.genyris.interp.Interpreter;
import org.genyris.interp.LispinException;
import org.genyris.io.ConvertEofInStream;
import org.genyris.io.InStream;
import org.genyris.io.IndentStream;
import org.genyris.io.Parser;
import org.genyris.io.ReaderInStream;
import org.genyris.io.UngettableInStream;

public class SourceLoader {


	public static Exp loadScriptFromClasspath(Interpreter _interp, String filename, Writer writer) throws LispinException {

		InputStream in  = SourceLoader.class.getResourceAsStream(filename);
		// this use of getResourceAsStream() means paths are relative to this class 
		// unless preceded by a '/'
		if( in == null ) {
			throw new LispinException("loadScriptFromClasspath: null pointer from getResourceAsStream.");
		}
		return executeScript(_interp, new InputStreamReader(in), writer);
	}
	
    public static Exp executeScript(Interpreter interp, Reader reader, Writer output) throws LispinException {
        InStream input = new UngettableInStream(new ConvertEofInStream(new IndentStream(new UngettableInStream(new ReaderInStream(reader)),
                false)));
        Parser parser = interp.newParser(input);
        IndentedFormatter formatter = new IndentedFormatter(output, 3, interp);
        Exp expression = null;
        Exp result = null;
        do {
            expression = parser.read();
            if (expression.equals(interp.getSymbolTable().internString(Constants.EOF))) {
                break;
            }
            result = interp.evalInGlobalEnvironment(expression);
            result.acceptVisitor(formatter);
            try {
            	output.write('\n');
                output.flush();
            } catch (IOException ignore) {}
        } while (true);
        return result;
    }


}
