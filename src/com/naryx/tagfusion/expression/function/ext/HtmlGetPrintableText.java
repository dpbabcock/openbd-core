/* 
 *  Copyright (C) 2000 - 2008 TagServlet Ltd
 *
 *  This file is part of Open BlueDragon (OpenBD) CFML Server Engine.
 *  
 *  OpenBD is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  Free Software Foundation,version 3.
 *  
 *  OpenBD is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with OpenBD.  If not, see http://www.gnu.org/licenses/
 *  
 *  Additional permission under GNU GPL version 3 section 7
 *  
 *  If you modify this Program, or any covered work, by linking or combining 
 *  it with any of the JARS listed in the README.txt (or a modified version of 
 *  (that library), containing parts covered by the terms of that JAR, the 
 *  licensors of this Program grant you additional permission to convey the 
 *  resulting work. 
 *  README.txt @ http://www.openbluedragon.org/license/README.txt
 *  
 *  http://www.openbluedragon.org/
 */

/*
 * Author: Alan Williamson
 * Created on 02-Sep-2004
 * 
 * BlueDragon only Expression
 * 
 * Returns the number of Bytes Sent by the session to the client
 */
package com.naryx.tagfusion.expression.function.ext;

import java.io.StringReader;

import net.htmlparser.jericho.Source;

import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfStringData;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.expression.function.functionBase;

/*
 * Removes all the tags from a given HTML block
 * 
 * Uses the Jericho-HTML library http://jerichohtml.sourceforge.net/doc/index.html
 */

public class HtmlGetPrintableText extends functionBase {
  
  private static final long serialVersionUID = 1L;
	
  public HtmlGetPrintableText(){ 
  	min = 1; max = 1;
  	setNamedParams( new String[]{ "html"} );
  }
  
	public String[] getParamInfo(){
		return new String[]{
			"html block",
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"string", 
				"Returns all the printable text from the HTML, devoid of all HTML tags, but maintaining any blank lines", 
				ReturnType.STRING );
	} 
  
	public cfData execute(cfSession _session, cfArgStructData argStruct ) throws cfmRunTimeException {
		String str = getNamedStringParam(argStruct, "html", "");
  	try{
  		Source source	=	new Source( new StringReader( str ) );
  		return new cfStringData( source.getRenderer().toString() );
  	}catch( Exception e ){
  		throwException( _session, e.getMessage() );
  		return null;
  	}
  }
}
