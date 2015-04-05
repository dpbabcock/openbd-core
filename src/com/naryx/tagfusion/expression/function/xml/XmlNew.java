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

package com.naryx.tagfusion.expression.function.xml;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.naryx.tagfusion.cfm.engine.catchDataFactory;
import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.cfm.xml.cfXmlData;
import com.naryx.tagfusion.expression.function.functionBase;

public class XmlNew extends functionBase {
	private static final long serialVersionUID = 1L;

	public XmlNew() {
		super.min = 0;
		super.max = 1;
	}

  public String[] getParamInfo(){
		return new String[]{
			"case sensitive - default false"
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"xml", 
				"Creates a new XML document with optional case-sensitivity elements", 
				ReturnType.XML );
	}
  	
	public cfData execute(cfSession _session, List<cfData> parameters) throws cfmRunTimeException {
		boolean caseSensitive = false;
		if (parameters.size() == 1)
			caseSensitive = cfBooleanData.getcfBooleanData(parameters.get(0).getString()).getBoolean();
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder parser = fact.newDocumentBuilder();

			Document doc = parser.newDocument();
			doc.normalize();
			return new cfXmlData(doc, caseSensitive);
		} catch (ParserConfigurationException ex) {
			throw new cfmRunTimeException(catchDataFactory.javaMethodException("errorCode.javaException", ex.getClass().getName(), ex.getMessage(), ex));
		}
	}
}
