//
//  QTCubedJMF.java
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

package net.mc_cubed;

import net.mc_cubed.qtcubed.QTKitFormatUtils;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.io.File;
import java.util.logging.Logger;
import java.util.Vector;
import javax.swing.JPanel;
import javax.media.bean.playerbean.MediaPlayer;
import javax.media.CaptureDeviceManager;
import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.control.FormatControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;

/**
 * Starting point for the application. General initialization should be done inside
 * the ApplicationController's init() method. If certain kinds of non-Swing initialization
 * takes too long, it should happen in a new Thread and off the Swing event dispatch thread (EDT).
 * 
 * @author shadow
 */
public class QTCubedJMF extends Frame implements ActionListener {

    static final boolean hasQTKit;

    static {
        hasQTKit = QTCubed.usesQTKit();
    }

    public static boolean usesQTKit() {
        return hasQTKit;
    }
    MediaPlayer mp = new MediaPlayer();

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new QTCubedJMF().setVisible(true);
            }
        });

        System.out.println("Reached end of main");
    }

    // No argument main constructor
    public QTCubedJMF() {
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        Button b = new Button("Select Movie...");
        b.setActionCommand("MOVIE");
        b.addActionListener(this);
        buttonPanel.add(b);

        b = new Button("View Capture Device");
        b.setActionCommand("CAPTURE");
        b.addActionListener(this);
        buttonPanel.add(b);

        add(buttonPanel, BorderLayout.SOUTH);
        add(mp, BorderLayout.CENTER);
        pack();

        Logger.getAnonymousLogger().info("************** Attached capture device info follows **************");
        Vector<CaptureDeviceInfo> captureDevices = CaptureDeviceManager.getDeviceList(null);
        for (CaptureDeviceInfo device : captureDevices) {
            Logger.getAnonymousLogger().info(" ----- " + device.getLocator() + " (" + device.getName() + ") ----- ");
            for (Format format : device.getFormats()) {
                Logger.getAnonymousLogger().info("Format Type: " + format);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            System.out.println("action performed");

            if (e.getActionCommand().equalsIgnoreCase("MOVIE")) {
                FileDialog fd =
                        new FileDialog(this, "Select Movie...", FileDialog.LOAD);
                fd.setVisible(true);
                String fileName = fd.getFile();
                if (fileName == null) {
                    return;
                }
                File f = new File(fd.getDirectory(), fd.getFile());

                MediaLocator l = new MediaLocator(f.toURL());
                mp.setMediaLocator(l);
                mp.start();
            }

            // Copying this from the apple developer docs and switching to Java syntax
            if (e.getActionCommand().equalsIgnoreCase("CAPTURE")) {
/*                for (CaptureDeviceInfo deviceInfo : (Vector<CaptureDeviceInfo>) CaptureDeviceManager.getDeviceList(null)) {
                    System.out.println(deviceInfo);
                }
*/
                CaptureDeviceInfo videoDevice = null;
                for (CaptureDeviceInfo device : (Vector<CaptureDeviceInfo>) CaptureDeviceManager.getDeviceList(null)) {
                    for (Format format : device.getFormats()) {
                        if (format instanceof VideoFormat) {
                            videoDevice = device;
                            break;
                        }
                    }
                }
                if (videoDevice == null) {
                    return;
                }

                try {
					
                    DataSource videoSource = Manager.createDataSource(videoDevice.getLocator());

//					videoSource.connect();
//					videoSource.start();

                    FormatControl formatControl = (FormatControl) videoSource.getControl("javax.media.control.FormatControl");
					if (formatControl != null) {
						formatControl.setFormat(QTKitFormatUtils.CompleteFormat(QTKitFormatUtils.rgb24,((VideoFormat) formatControl.getFormat()).getSize(),30.0f));
						System.out.println("Video Format: " + formatControl.getFormat());
						System.out.println("Video Size: " + ((VideoFormat) formatControl.getFormat()).getSize());
					}

					mp.setDataSource(videoSource);
					javax.media.Manager.createPlayer(videoSource);
//					*/
//					javax.media.Manager.createPlayer(videoDevice.getLocator());
//					mp.setMediaLocator(videoDevice.getLocator());
                    mp.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            // TODO- maybe a dialog
            ex.printStackTrace();
        }
    }
}
