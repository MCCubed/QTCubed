//
//  QTJavaMovieView.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
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

import quicktime.QTSession;
import quicktime.QTException;
import quicktime.app.view.MoviePlayer;
import quicktime.app.view.QTFactory;
import quicktime.app.view.QTJComponent;
import quicktime.std.StdQTConstants;
import quicktime.std.movies.Movie;
import quicktime.std.movies.MovieController;
import java.net.URL;
import java.io.File;
import java.util.Properties;
import java.lang.InstantiationException;
import java.io.IOException;
import java.awt.Component;

class QTJavaMovieView implements QTMovieView {

	QTJComponent component;
	
	public QTJavaMovieView() throws InstantiationException {
		try {
			Movie newMovie = Movie.fromScrap(0);
			MoviePlayer newMoviePlayer = new MoviePlayer(newMovie);
			component = QTFactory.makeQTJComponent(newMoviePlayer);		
		} catch (QTException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
	public Component getComponent() {
		return component.asJComponent();
	}
	
	public void setMovie(QTMovie movie) {
		QTJavaMovie javaMovie = (QTJavaMovie)movie;
		try {
			MoviePlayer mp = new MoviePlayer(javaMovie.movie);
			component.setMoviePlayer(mp);
		} catch (QTException ex) {
			throw new RuntimeException(ex);
		}
	}
}