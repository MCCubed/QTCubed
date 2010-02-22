//
//  QTCubed.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
//
//  This is proprietary software, and may not be used without the express written consent of:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  このソフトウェアは専売権付ソフトウェアです。株式会社MCキューブの許可なく複製、転用、販売等の二次利用をすることを固く禁じます。
//  株式会社エムシーキューブ
//  埼玉県さいたま市浦和区上木崎１−３−４

package net.mc_cubed;

import javax.swing.SwingUtilities;
import net.mc_cubed.qtcubed.QTMovie;
import net.mc_cubed.qtcubed.QTMovieView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Button;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Starting point for the application. General initialization should be done inside
 * the ApplicationController's init() method. If certain kinds of non-Swing initialization
 * takes too long, it should happen in a new Thread and off the Swing event dispatch thread (EDT).
 * 
 * @author shadow
 */
public class QTCubed extends Frame implements ActionListener {
	static {
		// Ensure native JNI library is loaded
		System.loadLibrary("QTCubed");
	}

	QTMovieView qtmv;

	
	public static void main(final String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new QTCubed().setVisible(true);
				}
			});
	}
	
	// No argument main constructor
	public QTCubed() {
		setLayout (new BorderLayout());
        Button b = new Button ("Select Movie...");
        b.addActionListener (this);
        qtmv = new QTMovieView();
        add (b, BorderLayout.SOUTH);
        add (qtmv, BorderLayout.CENTER);
		pack();
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
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream inStream = new FileInputStream(f);
			int inChars;
			byte[] buffer = new byte[10240]; // Read up to 10k bytes at a time
			while ((inChars = inStream.read(buffer,0,buffer.length)) != -1) {
				baos.write(buffer,0,inChars);
			}			

			// Create a QTMovie from the memory buffer
			QTMovie movie = new QTMovie (baos.toByteArray());
			
            movie = new QTMovie (f);
            System.out.println ("Created movie");
			
            // set movie on the view
            qtmv.setMovie (movie);
            System.out.println ("Set movie on view");
			
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
