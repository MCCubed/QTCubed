//
//  QTKitMovieView.java
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

import net.mc_cubed.QTCubed;
import java.awt.Color;
import java.awt.Dimension;
import com.apple.eawt.CocoaComponent;
import java.awt.Component;
import javax.swing.SwingUtilities;

class QTKitMovieView extends CocoaComponent implements QTMovieView {
		
	// Initialize the QTCubed Library
	static boolean usingQTKit = QTCubed.usesQTKit();
	protected boolean controllerVisible = false,preservesAspectRatio = true;
	protected Color fillColor = Color.black;

	protected static final int SET_MOVIE = 1;
	protected static final int PLAY_MOVIE = 2;
	protected static final int PAUSE_MOVIE = 3;
	protected static final int SET_PRESERVES_ASPECT = 4;
	protected static final int SET_CONTROLLER_VISIBILITY = 5;
	protected static final int SET_FILL_COLOR = 6;
	protected static final int GOTO_BEGINNING = 7;
	protected static final int GOTO_END = 8;
	protected static final int GOTO_NEXT_SELECTION_POINT = 9;
	protected static final int GOTO_PREVIOUS_SELECTION_POINT = 10;
	protected static final int STEP_FORWARD = 11;
	protected static final int STEP_BACKWARD = 12;
	protected static final int GOTO_POSTER_FRAME = 13;
	
	protected Dimension preferredSize = new Dimension(320,240);
	
	protected QTKitMovieImpl movie;
	
	@Override
	public Component getComponent() {
		return this;
	}
	
	@Deprecated
	public int createNSView() {
		return (int)createNSViewLong();
	}
	
	// Instantiate the QTMovieView on the native side and return it as a long
	public native long createNSViewLong();
		
	public Dimension getMaximumSize() {
		// TODO: Get the maximum size from the native code
		return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
	}
	
	public Dimension getMinimumSize() {
		// TODO: Get the minimum size from the native code
		return new Dimension(2,2);
	}
	
	public Dimension getPreferredSize() {
		// TODO: Get the preferred size from the native code			
		return preferredSize;
	}
	
	public void setMovie(QTMovie newmovie) {
		movie = (QTKitMovieImpl)newmovie;
		if (getPeer() != null)
			sendMessage(SET_MOVIE, movie.getMovieRef());
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if (movie != null) {
			sendMessage(SET_MOVIE, movie.getMovieRef());
		}
		sendMessage(SET_PRESERVES_ASPECT,this.preservesAspectRatio);
		sendMessage(SET_CONTROLLER_VISIBILITY,this.controllerVisible);

	}
		
	public QTKitMovieView() {
		super();
	}
	
	public void play() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(PLAY_MOVIE, movie.getMovieRef());
	}

	public void pause() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		this.sendMessage(PAUSE_MOVIE, this.movie.getMovieRef());
	}
	
	public boolean isControllerVisible() {
		return this.controllerVisible;
	}
	
	public void setControllerVisisble(boolean visible) {
		this.controllerVisible = visible;
		if (getPeer() != null) {
			sendMessage(SET_CONTROLLER_VISIBILITY,visible);
		}
	}
		
	public boolean preservesAspectRatio() {
		return preservesAspectRatio;
	}
		
	public void setPreservesAspectRatio(boolean preserves) {
		if (getPeer() != null) {
			sendMessage(SET_PRESERVES_ASPECT,preserves);
		}
	}
		
	public java.awt.Color getFillColor() {
		return fillColor;
	}
		
	public void setFillColor(java.awt.Color fillColor) {
		sendMessage(SET_FILL_COLOR, fillColor.getRGB());
	}
		
	public void gotoBeginning() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(GOTO_BEGINNING, null);
	}
	
	public void gotoEnd() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(GOTO_END,null);
	}
	
	public void gotoNextSelectionPoint() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(GOTO_NEXT_SELECTION_POINT,null);
	}
	
	public void gotoPreviousSelectionPoint() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(GOTO_PREVIOUS_SELECTION_POINT,null);
	}
	
	public void gotoPosterFrame() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(GOTO_POSTER_FRAME,null);
	}

	public void stepForward() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(STEP_FORWARD,null);
	}

	public void stepBackward() {
		if (getPeer() == null) {
			throw new java.lang.IllegalStateException("Component MUST be made visible BEFORE calling operations which affect playback!");
		}
		sendMessage(STEP_BACKWARD,null);
	}
}