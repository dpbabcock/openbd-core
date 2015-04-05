/* 
 *  Copyright (C) 2012 TagServlet Ltd
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
 *  $Id: FtpCreateDir.java 2275 2012-09-04 00:20:26Z alan $
 */
package com.naryx.tagfusion.cfm.tag.net.ftp;

import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;

public class FtpCreateDir extends FtpClose {
	private static final long serialVersionUID = 1L;

	public FtpCreateDir() {
		min = 2; max = 3;
		setNamedParams( new String[]{ "ftpdata", "directory", "stoponerror" } );
	}
	

	public String[] getParamInfo(){
		return new String[]{
			"the connection object as defined from FtpOpen()",
			"the remote directory to create",
			"if something goes wrong, throw an exception. defaults to true"
		};
	}
	
	
	public java.util.Map getInfo(){
		return makeInfo(
				"ftp", 
				"Creates the directory on the remote server", 
				ReturnType.BOOLEAN );
	}
	
	
	public cfData execute(cfSession _session, cfArgStructData argStruct) throws cfmRunTimeException {
		cfFTPData	ftpdata	= getFTPData(_session, argStruct);

		boolean soe		= getNamedBooleanParam(argStruct, "stoponerror", true);
		String	file	= getNamedStringParam(argStruct, "directory", null );
		if ( file == null )
			throwException(_session, "missing 'directory' parameter");

		try{
			ftpdata.lock();
			
			boolean bSuccess = ftpdata.makeDirectory(file);
			if ( soe && !ftpdata.isSucceeded() )
				throwException( _session, ftpdata.getErrorText() );

			return cfBooleanData.getcfBooleanData( bSuccess );

		}finally{
			ftpdata.unlock();
		}
	}
}