/* 
 *  Copyright (C) 2000 - 2009 TagServlet Ltd
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
package org.alanwilliamson.openbd.plugin.spreadsheet.functions;

import java.util.List;

import org.alanwilliamson.openbd.plugin.spreadsheet.cfSpreadSheetData;
import org.apache.poi.ss.usermodel.Sheet;

import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;
import com.naryx.tagfusion.expression.function.functionBase;

public class SpreadsheetAddSplitPane extends functionBase {
	private static final long serialVersionUID = 1L;

	public SpreadsheetAddSplitPane(){  min = 5;  max = 6; }
  
	public String[] getParamInfo(){
		return new String[]{
			"spreadsheet object",
			"xSplitPos",
			"ySplitPos",
			"leftmostColumn",
			"topRow",
			"activePane - (PANE_LOWER_RIGHT, PANE_LOWER_LEFT (default), PANE_UPPER_RIGHT, PANE_UPPER_LEFT)"
		};
	}
	
	public java.util.Map getInfo(){
		return makeInfo(
				"spreadsheet-plugin", 
				"Creates a new split pane with the details given on the active sheet", 
				ReturnType.BOOLEAN );
	}
	
  public cfData execute( cfSession _session, List<cfData> parameters ) throws cfmRunTimeException {
  	cfSpreadSheetData	spreadsheet = null;
  	
  	int xSplitPos = 0;
    int ySplitPos = 0;
    int leftmostColumn = 0;
    int topRow = 0;
    int activePane	= Sheet.PANE_LOWER_LEFT;
  	
  	/*
  	 * Collect up the parameters
  	 */
  	if ( parameters.size() == 5 ){
  		spreadsheet			= (cfSpreadSheetData)parameters.get(4);
  		xSplitPos				= parameters.get(3).getInt();
  		ySplitPos				= parameters.get(2).getInt();
  		leftmostColumn	= parameters.get(1).getInt()-1;
  		topRow					= parameters.get(0).getInt()-1;
  	}else if ( parameters.size() == 6 ){
  		spreadsheet		= (cfSpreadSheetData)parameters.get(5);
  		xSplitPos				= parameters.get(4).getInt();
  		ySplitPos				= parameters.get(3).getInt();
  		leftmostColumn	= parameters.get(2).getInt()-1;
  		topRow					= parameters.get(1).getInt()-1;
  		activePane			= getActivePane( parameters.get(0).getString() );
  	}
  	
		if ( leftmostColumn < 0 )
  		throwException(_session, "leftmostColumn must be 1 or greater (" + leftmostColumn + ")");
  	if ( topRow < 0 )
  		throwException(_session, "topRow must be 1 or greater (" + topRow + ")");
  	
  	//Perform the split operation
  	spreadsheet.getActiveSheet().createSplitPane( xSplitPos, ySplitPos, leftmostColumn, topRow, activePane );
  	
  	return cfBooleanData.TRUE;
  }
  
  
  private int	getActivePane( String pane ){
  	pane	= pane.toUpperCase();
  	if ( pane.equals("PANE_LOWER_RIGHT") )
  		return Sheet.PANE_LOWER_RIGHT;
 		else if ( pane.equals("PANE_LOWER_LEFT") )
   		return Sheet.PANE_LOWER_LEFT;
 		else if ( pane.equals("PANE_UPPER_RIGHT") )
    	return Sheet.PANE_UPPER_RIGHT;
 		else if ( pane.equals("PANE_LOWER_RIGHT") )
       return Sheet.PANE_LOWER_RIGHT;
 		else
 			return Sheet.PANE_LOWER_LEFT;
  }
}
