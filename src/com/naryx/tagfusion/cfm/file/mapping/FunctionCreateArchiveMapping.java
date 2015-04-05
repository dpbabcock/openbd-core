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
 *  $Id: FunctionCreateArchiveMapping.java 2151 2012-07-04 13:46:43Z alan $
 */
package com.naryx.tagfusion.cfm.file.mapping;

import java.io.File;

import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.expression.function.functionBase;

public class FunctionCreateArchiveMapping extends functionBase {
	private static final long serialVersionUID = 1L;
	
	
	public FunctionCreateArchiveMapping(){
		min = 2; max = 3;
		setNamedParams( new String[] { "archivefile", "directory", "overwrite" } );
	}
	

	public String[] getParamInfo(){
		return new String[]{
			"the full path of the archive file",
			"the directory to create an archive from. All subdirectories will be added. Directories beginning with [.] will not be added",
			"if the archivefile already exists, overwrite it. defaults to false"
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"engine", 
				"Creates an OpenBD archive for use with the mappings", 
				ReturnType.BOOLEAN );
	}
	
	public cfData execute(cfSession _session, cfArgStructData argStruct ) throws cfmRunTimeException {

		String	sArchiveFile	= getNamedStringParam(argStruct, "archivefile", null );
		if ( sArchiveFile == null )
			throwException(_session, "missing the 'archivefile' parameter");

		String	sDirectory	= getNamedStringParam(argStruct, "directory", null );
		if ( sDirectory == null )
			throwException(_session, "missing the 'directory' parameter");
		
		// Get the archive file
		File	archiveFile	= new File( sArchiveFile );
		if ( archiveFile.exists() ){
			if ( getNamedBooleanParam(argStruct, "overwrite", false) )
				archiveFile.delete();
			else
				throwException(_session, "ArchiveFile already exists: " + archiveFile.getAbsolutePath() );
		}

		File directory	= new File(sDirectory);
		if ( !directory.isDirectory() )
			throwException(_session, "directory does not exist: " + directory );
		
		try {
			OpenBDArchive.createArchive(archiveFile, directory);
		} catch (Exception e) {
			throwException( _session, e.getMessage() );
		}
		
		return cfBooleanData.TRUE;
	}
}