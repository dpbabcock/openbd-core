/* 
 *  Copyright (C) 2000 - 2010 TagServlet Ltd
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

package com.naryx.tagfusion.expression.function.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.naryx.tagfusion.cfm.engine.cfArgStructData;
import com.naryx.tagfusion.cfm.engine.cfBooleanData;
import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfSession;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;

public class ImageDrawArc extends ImageInfo {
	private static final long serialVersionUID = 1L;


	public ImageDrawArc() {
		min = 7; max = 8;
		setNamedParams( new String[]{ "name", "x", "y", "width", "height", "startangle", "arcangle", "filled" } );
	}
	
	
	public String[] getParamInfo() {
		return new String[] { 
				"the image object", 
				"the x coordinate of the upper-left corner of the arc to be drawn",
				"the y coordinate of the upper-left corner of the arc to be drawn",
				"the width of the arc to be drawn", 
				"the height of the arc to be drawn",
				"the beginning angle",
				"the angular extent of the arc, relative to the start angle",
				"a flag to determine if the rectangle is filled with the current color; default to false"};
	}

	
	public java.util.Map getInfo(){
		return makeInfo(
				"image", 
				"Draws the outline of a circular or elliptical arc covering the specified rectangle.  Optionally filling it in.", 
				ReturnType.BOOLEAN );
	}
	
	public cfData execute( cfSession _session, cfArgStructData argStruct ) throws cfmRunTimeException{
		cfImageData im	= getImage( _session, argStruct );
		
		int x	= getNamedIntParam(argStruct, "x", -1 );
		int y	= getNamedIntParam(argStruct, "y", -1 );
		int w	= getNamedIntParam(argStruct, "width", -1 );
		int h	= getNamedIntParam(argStruct, "height", -1 );

		int sa	= getNamedIntParam(argStruct, "startangle", Integer.MIN_VALUE );
		int ar	= getNamedIntParam(argStruct, "arcangle", Integer.MIN_VALUE );
		
		boolean bFilled	= getNamedBooleanParam(argStruct, "filled", false );
		
		//Check boundaries
		BufferedImage	bim	= im.getImage();
		
		if ( x < 0 || x > bim.getWidth() )
			throwException(_session, "x (" + x + ") is outside the image" );
		
		if ( y < 0 || y > bim.getHeight() )
			throwException(_session, "y (" + y + ") is outside the image" );

		if ( (x+w) < 0 || (x+w) > bim.getWidth() )
			throwException(_session, "w (" + w + ") is outside the image" );

		if ( (y+w) < 0 || (y+w) > bim.getHeight() )
			throwException(_session, "w (" + w + ") is outside the image" );

		if ( sa == Integer.MIN_VALUE )
			throwException(_session, "startangle was not specified" );
		
		if ( ar == Integer.MIN_VALUE )
			throwException(_session, "arcangle  was not specified" );


		// Perform the operation
		Graphics2D g2 = im.createGraphics();
		
		if ( bFilled ){
			g2.fillArc( x, y, w, h, sa, ar );	
		}else{
			g2.drawArc(x, y, w, h, sa, ar );	
		}
		
		im.dispose(g2);
		return cfBooleanData.TRUE;
	}
}