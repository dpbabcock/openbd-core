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

package com.naryx.tagfusion.expression.function;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfDateData;
import com.naryx.tagfusion.cfm.engine.cfNumberData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;

public class daysInYear extends functionBase {

	private static final long serialVersionUID = 1L;

	public daysInYear() {
		min = max = 1;
		setNamedParams( new String[]{ "date" } );
	}

	public String[] getParamInfo(){
		return new String[]{
			"date"	
		};
	}
  
	public java.util.Map getInfo(){
		return makeInfo(
				"date", 
				"Returns the number of days in the year of the given date object (taking into account of leap years)", 
				ReturnType.NUMERIC );
	}
	
	public cfData execute(cfSession _session, cfArgStructData argStruct ) throws cfmRunTimeException {

		cfData d1 = getNamedParam( argStruct, "date" );

		if (d1.getDataType() == cfData.CFSTRINGDATA) {
			java.util.Date date = ParseDateTime.parseDateString(d1.getString());
			if (date == null)
				throwException(_session, "invalid date/time string:" + getNamedStringParam( argStruct, "date", null ));
			d1 = new cfDateData(date);
		}

		if (d1.getDataType() == cfData.CFDATEDATA) {

			Calendar c = ((cfDateData) d1).getCalendar();
			if (new GregorianCalendar().isLeapYear(c.get(Calendar.YEAR)))
				return new cfNumberData(366);
			else
				return new cfNumberData(365);
		} else
			throwException(_session, "invalid date/time string:" + getNamedStringParam( argStruct, "date", null ));

		return null;
	}
}