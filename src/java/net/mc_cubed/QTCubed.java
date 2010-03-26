//
//  QTCubed.java
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

package net.mc_cubed;

import javax.swing.SwingUtilities;
import net.mc_cubed.qtcubed.QTMovie;
import net.mc_cubed.qtcubed.QTMovieView;
import net.mc_cubed.qtcubed.QTCubedFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Button;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Method;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Starting point for the application. General initialization should be done inside
 * the ApplicationController's init() method. If certain kinds of non-Swing initialization
 * takes too long, it should happen in a new Thread and off the Swing event dispatch thread (EDT).
 * 
 * @author shadow
 */
public class QTCubed extends Frame implements ActionListener {
	static final boolean hasQTKit;
	
	static {
		boolean qtKitLoaded = false;
		try {
			// Disable Cocoa Compatibility Mode
			System.setProperty("com.apple.eawt.CocoaComponent.CompatibilityMode", "false");
			
			Logger.getAnonymousLogger().log(Level.INFO,"Loading QTCubed Library");
			// Ensure native JNI library is loaded
			AccessController.doPrivileged(new PrivilegedAction() {
				public Object run() {
					// privileged code goes here
					System.loadLibrary("QTCubed");
					return null; // nothing to return
				}
			});

			Logger.getAnonymousLogger().log(Level.INFO,"Successfully Loaded QTCubed Library!");
			qtKitLoaded = true;
		} catch (Throwable t1) {
			t1.printStackTrace();
			Logger.getAnonymousLogger().log(Level.INFO,"Cannot load QTCubed Library!");
			// Couldn't load the library, try QTJava instead
			try {
				Logger.getAnonymousLogger().log(Level.INFO,"Trying to load QTJava Library.");
				// Invoke QTSession.open() using reflection to avoid any class dependancies.
				AccessController.doPrivileged(new PrivilegedAction() {
					public Object run() {
						// privileged code goes here
						//				quicktime.QTSession.open();
						try {
							Class clazz = Class.forName("quicktime.QTSession");
							Method m = clazz.getMethod("open");
							m.invoke(null);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null; // nothing to return
					}
				});
				Logger.getAnonymousLogger().log(Level.INFO,"Successfully loaded QTJava Library!");
			} catch (Throwable t2) {
				t2.printStackTrace();
				Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot load either QTCubed or QTJava libraries!  Quicktime Services will not be available!");
			}
			
		}
		
		hasQTKit = qtKitLoaded;
	}

	public static boolean usesQTKit() {
		return hasQTKit;
	}
	
	QTMovieView qtmv;

	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new QTCubed().setVisible(true);
			}
		});
		
		System.out.println("Reached end of main");
	}
		
	// No argument main constructor
	public QTCubed() {
		setLayout (new BorderLayout());
        Button b = new Button ("Select Movie...");
        b.addActionListener (this);
		try {
			qtmv = QTCubedFactory.initQTMovieView();
			add (b, BorderLayout.SOUTH);
			add (qtmv.getComponent(), BorderLayout.CENTER);
			pack();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		}
	}
	
    public void actionPerformed (ActionEvent e) {
        try {
            System.out.println ("action performed");
			
            FileDialog fd =
			new FileDialog (this, "Select Movie...", FileDialog.LOAD);
            fd.setVisible(true);
            String fileName = fd.getFile();
            if (fileName == null)
                return;
            File f = new File (fd.getDirectory(), fd.getFile());
			
			//             old - when I was creating url's from file paths (works, tho)
			//             URL u = f.toURL();
			//             System.out.println ("Creating movie for URL: " + u);
			//             QTMovie movie = new QTMovie (u);

			// Copy the contents of the entire file into a memory buffer
/*			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream inStream = new FileInputStream(f);
			int inChars;
			byte[] buffer = new byte[10240]; // Read up to 10k bytes at a time
			while ((inChars = inStream.read(buffer,0,buffer.length)) != -1) {
				baos.write(buffer,0,inChars);
			}			

			// Create a QTMovie from the memory buffer
			QTMovie movie = new QTMovie (baos.toByteArray());
*/			
            QTMovie movie = QTCubedFactory.initQTMovie(f);
            System.out.println ("Created movie");
			
            // set movie on the view
            qtmv.setMovie (movie);
            System.out.println ("Set movie on view");
			
			qtmv.play();
            // check to see if it's editable
//            System.out.println ("Movie is " + 
//                                (qtmv.isEditable() ? "" : "not ") +
//                                "editable");
			
        } catch (Exception ex) {
            // TODO- maybe a dialog
            ex.printStackTrace();
        }
    }
}
