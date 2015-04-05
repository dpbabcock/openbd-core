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

package com.naryx.tagfusion.cfx;

import com.allaire.cfx.Query;
import com.allaire.cfx.Response;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfQueryResultData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfStringData;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.cfm.parser.cfLData;
import com.naryx.tagfusion.cfm.parser.runTime;

public class sessionResponseBase extends Object implements Response {

	private cfSession session;
	private boolean debugOn;

	public sessionResponseBase( cfSession _session, boolean _debugOn ) {
		session	= _session;
		debugOn = _debugOn;
	}

	public void write(String output){
		session.write( output );
	}
	
	public void 	setVariable(String name, String value) throws IllegalArgumentException{
		try{
			cfData temp = runTime.runExpression( session, name, false ); 
			if ( temp.getDataType() == cfData.CFLDATA ){
				( (cfLData) temp ).Set( new cfStringData( value ), session.getCFContext() );
			}
		}catch(cfmRunTimeException E){}
	}
	
	public Query 	addQuery( String name, String[] columns ) throws IllegalArgumentException{
		cfQueryResultData newQuery = new cfQueryResultData( columns, "CFX" );
		try{
  		session.setData( name, newQuery );
  	}catch( cfmRunTimeException E){}
		
		return new sessionQuery( name, newQuery );
	}
	
	public void writeDebug( String output ) {
		if ( debugOn ) write( output );	
	}
}
