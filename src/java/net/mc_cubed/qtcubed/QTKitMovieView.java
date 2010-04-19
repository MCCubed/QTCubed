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
import java.awt.Dimension;
import com.apple.eawt.CocoaComponent;
import java.awt.Component;

public class QTKitMovieView extends CocoaComponent implements QTMovieView {

	// Initialize the QTCubed Library
	static boolean usingQTKit = QTCubed.usesQTKit();

	protected static final int SET_MOVIE = 1;
	protected static final int PLAY_MOVIE = 2;
	protected static final int PAUSE_MOVIE = 3;
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
	
	public void setMovie(QTMovie movie) {
		this.movie = (QTKitMovieImpl)movie;
		this.sendMessage(SET_MOVIE, this.movie.getMovieRef());
	}
	
	public QTKitMovieView() {
		super();
	}
	
	public void play() {
		this.movie = (QTKitMovieImpl)movie;
		this.sendMessage(PLAY_MOVIE, this.movie.getMovieRef());
	}

	public void pause() {
		this.movie = (QTKitMovieImpl)movie;
		this.sendMessage(PAUSE_MOVIE, this.movie.getMovieRef());
	}		
}