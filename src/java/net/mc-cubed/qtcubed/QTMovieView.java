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

package net.mc_cubed.qtcubed;

import net.mc_cubed.QTCubed;
import java.awt.Dimension;
import com.apple.eawt.CocoaComponent;

public class QTMovieView extends CocoaComponent {

	// Initialize the QTCubed Library
	static final QTCubed cubed = new QTCubed();

	protected static final int SET_MOVIE = 1;
	protected Dimension size = new Dimension(320,240);
	protected Dimension preferredSize = new Dimension(320,240);
	
	protected QTMovie movie;
	
	@Deprecated
	public int createNSView() {
		return (int)createNSViewLong();
	}
	
	// Instantiate the QTMovieView on the native side and return it as a long
	public native long createNSViewLong();
	
	public Dimension getMaximumSize() {
		// TODO: Get the maximum size from the native code
		return size;
	}
	
	public Dimension getMinimumSize() {
		// TODO: Get the minimum size from the native code
		return size;
	}
	
	public Dimension getPreferredSize() {
		// TODO: Get the preferred size from the native code			
		return preferredSize;
	}
	
	public void setMovie(QTMovie movie) {
		this.movie = movie;
		this.sendMessage(SET_MOVIE, movie.getMovieRef());
	}
	
	public QTMovieView() {
		super();
	}
}