//
//  QTMovieView.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//  
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//  
//  You should have received a copy of the GNU General Public License along
//  with this program; if not, write to the Free Software Foundation, Inc.,
//  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//  
//  
//  This is also dual licensed as proprietary software, and may be used in
//  commercial/proprietary software products by obtaining a license from:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed;

import java.awt.Component;

public interface QTMovieView {
	
	Component getComponent();

	public void setMovie(QTMovie movie);
	
	public void play();
	
	public void pause();
	
	public boolean isControllerVisible();
	
	public void setControllerVisisble(boolean visible);
	
	public boolean preservesAspectRatio();
	
	public void setPreservesAspectRatio(boolean preserves);
	
	public java.awt.Color getFillColor();
	
	public void setFillColor(java.awt.Color fillColor);
	
	public void gotoBeginning();
	
	public void gotoEnd();
	
	public void gotoNextSelectionPoint();
	
	public void gotoPreviousSelectionPoint();
	
	public void gotoPosterFrame();
	
	public void stepForward();
	
	public void stepBackward();

}