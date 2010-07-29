//
//  QTCubedTest.java
//  QTCubed
//
//  Created by Chappell Charles on 10/07/29.
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



import org.junit.*;
import net.mc_cubed.*;
import java.io.*;
import java.util.Collection;


public class QTCubedTest {
	
	@Test
	public void QTCubedInitTest() throws InstantiationException {
		if (QTCubed.usesQTKit()) {
			QTMovieView qtmovieView = QTCubedFactory.initQTMovieView();

			Assert.assertNotNull("Got a null QTMovieView, expected not null!", qtmovieView);

			Assert.assertTrue("QTKitMovieView expected since QTKit is enabled, but not returned!",qtmovieView instanceof QTKitMovieView);
			
		} else {
			QTMovieView qtmovieView = QTCubedFactory.initQTMovieView();

			Assert.assertNotNull("Got a null QTMovieView, expected not null!", qtmovieView);
			
			Assert.assertTrue("QTKitMovieView expected since QTKit is enabled, but not returned!",qtmovieView instanceof QTJavaMovieView);
		}
	}
	
	@Test
	public void LargeMovieTest() throws InstantiationException,IOException {
		String LARGEMOVIEPATH = "LARGE_MOVIE_PATH";

		String largeMoviePath = System.getenv(LARGEMOVIEPATH);
		Assert.assertNotNull("Please set the environmental variable " + LARGEMOVIEPATH + " to a large quicktime capable movie before running this test",largeMoviePath);
		
		File largeMovieFile = new File(largeMoviePath);
		
		Assert.assertTrue("File at " + largeMoviePath + " must exist!",largeMovieFile.exists());
		
		// Test a plain quicktime handoff first
		QTMovie fileMovie = QTCubedFactory.initQTMovie(largeMovieFile);
		
		Assert.assertNotNull("Movie creation from a java.io.File failed!", fileMovie);
		
		// Read all the data into a memory buffer
		FileInputStream fis = new FileInputStream(largeMovieFile);
		byte[] fileBuffer = new byte[fis.available()];
		DataInputStream dis = new DataInputStream(fis);
		
		dis.readFully(fileBuffer);
		
		QTMovie bufferMovie = QTCubedFactory.initQTMovie(fileBuffer);
		
		Assert.assertNotNull("Movie creation from a byte array failed!", bufferMovie);
		
	}
	
	@Test
	public void CaptureDeviceTest() throws InstantiationException {
		Collection<QTCaptureDevice> captureDevices = QTCubedFactory.captureDevices();
		
		Assert.assertNotNull("Capture Device list was null!",captureDevices);

		Assert.assertTrue("Capture Device list was empty!",captureDevices.size() > 0);
	}
}