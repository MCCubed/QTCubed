//
//  QTCubedFactory.java
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
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed;

import net.mc_cubed.QTCubed;
import java.net.URL;
import java.io.File;
import java.util.Properties;
import java.io.IOException;

public class QTCubedFactory {

	public static QTMovieView initQTMovieView() throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieView();
		} else {
			return new QTJavaMovieView();
		}
	}
	
	public static QTMovie initQTMovie() throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl();
		} else {
			return new QTJavaMovie();
		}
	}
	
	public static QTMovie initQTMovie(URL url) throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl(url);
		} else {
			return new QTJavaMovie(url);
		}
	}
	
	public static QTMovie initQTMovie(File file) throws InstantiationException,IOException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl(file);
		} else {
			return new QTJavaMovie(file);
		}
	}
	
	public static QTMovie initQTMovie(byte[] bytes) throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl(bytes);
		} else {
			return new QTJavaMovie(bytes);
		}
	}
	
	public static QTMovie initQTMovie(Properties attributes) throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl(attributes);
		} else {
			return new QTJavaMovie(attributes);
		}
	}
	
	public static QTMovie initQTMovie(String name) throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			return new QTKitMovieImpl(name);
		} else {
			return new QTJavaMovie(name);
		}
	}
	
	
	public QTCubedFactory() {
		super();
	}

}