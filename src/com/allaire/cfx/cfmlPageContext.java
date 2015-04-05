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

package com.allaire.cfx;

/**
 * This is the interface that will facilitate the communication between a java object
 * and a CFML page.
 */

public interface cfmlPageContext {
	
	//--[ Methods for reading variables from the CFML page
	public boolean 				variableExists(String name) 						throws cfmlInvalidContextException, cfmlInvalidDataException;
	public String 				getVariable(String name) 								throws cfmlInvalidContextException, cfmlInvalidDataException;
	public int 						getIntVariable(String name) 						throws cfmlInvalidContextException, cfmlInvalidDataException;
	public double					getDoubleVariable(String name)					throws cfmlInvalidContextException, cfmlInvalidDataException;
	public boolean				getBooleanVariable(String name)					throws cfmlInvalidContextException, cfmlInvalidDataException;
	public java.util.Date	getDateVariable(String name)						throws cfmlInvalidContextException, cfmlInvalidDataException;
	public Object					getObjectVariable(String name)					throws cfmlInvalidContextException, cfmlInvalidDataException;
	

	//--[ Methods for setting values back into the CFML page	
	public void 	setVariable(String name, Object value) 					throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, String value) 					throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, boolean value)				  throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, int value) 						throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, double value) 					throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, java.util.Date value) 	throws cfmlInvalidContextException,IllegalArgumentException;
	public void 	setVariable(String name, java.util.Map value) 	throws cfmlInvalidContextException,IllegalArgumentException;


	public Query 	addQuery(String name,String[] columns) 					throws cfmlInvalidContextException,IllegalArgumentException;
	public Query 	getQuery() 																		throws cfmlInvalidContextException;
	
	//--[ House Keeping methods
	public boolean bSessionValid();
}
