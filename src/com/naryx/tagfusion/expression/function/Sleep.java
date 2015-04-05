/* 
 *  Copyright (C) 2000 - 2013 TagServlet Ltd
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
 *  http://openbd.org/
 *  $Id: Sleep.java 2387 2013-06-17 02:07:50Z alan $
 */


package com.naryx.tagfusion.expression.function;

import java.util.List;

import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;

public class Sleep extends functionBase{
	
  private static final long serialVersionUID = 1L;
	
  public Sleep(){
     min = max = 1;
  }
  
	public String[] getParamInfo(){
		return new String[]{
			"milliseconds"
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"engine", 
				"pauses the current request for the amount of time specified (in milliseconds)", 
				ReturnType.BOOLEAN );
	}

  public cfData execute( cfSession _session, List<cfData> parameters )throws cfmRunTimeException{
		int duration = parameters.get(0).getInt();
		if ( duration < 0 ){
			return cfBooleanData.TRUE;
		}

		try{
			Thread.sleep( duration );
		}catch( InterruptedException ignore ){}

		return cfBooleanData.TRUE;
  } 


}
