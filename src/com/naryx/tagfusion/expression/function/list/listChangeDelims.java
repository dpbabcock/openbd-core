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

package com.naryx.tagfusion.expression.function.list;

import java.util.List;

import com.nary.util.string;
import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfStringData;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.expression.function.functionBase;

public class listChangeDelims extends functionBase {
	private static final long serialVersionUID = 1L;

	public listChangeDelims() {
		min = 2;
		max = 3;
		setNamedParams( new String[]{ "list","newDel","oldDel"} );
	}

	public String[] getParamInfo(){
		return new String[]{
			"list",
			"new delimiter",
			"old delimiter - default comma (,)"
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"list", 
				"Converts the given list delimitor to the new one", 
				ReturnType.STRING );
	}
	
	public cfData execute(cfSession _session, cfArgStructData argStruct ) throws cfmRunTimeException {

		String list = getNamedStringParam( argStruct, "list" ,"" );
		String oldDelimiters = getNamedStringParam( argStruct, "oldDel" ,"," );
		String newDelimiters = getNamedStringParam( argStruct, "newDel" ,"," );

		List<String> listArr = string.split(list, oldDelimiters);
		if (listArr.size() == 0) {
			return new cfStringData("");
		} else if (listArr.size() == 1) {
			return new cfStringData((String) listArr.get(0));
		} else {
			StringBuilder newList = new StringBuilder((String) listArr.get(0));
			for (int i = 1; i < listArr.size(); i++) {
				newList.append(newDelimiters);
				newList.append((String) listArr.get(i));
			}
			return new cfStringData(newList.toString());
		}

	}
}
