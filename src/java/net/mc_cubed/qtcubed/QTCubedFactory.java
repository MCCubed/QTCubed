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

import java.util.logging.Level;
import java.util.logging.Logger;
import net.mc_cubed.QTCubed;
import java.net.URL;
import java.io.File;
import java.util.Properties;
import java.util.Collection;
import java.util.LinkedList;
import java.io.IOException;

public class QTCubedFactory {

    public static QTMovieView initQTMovieView() throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieView();
        } else {
            throw new InstantiationException("QTKit not present or could not be initialized");
        }
    }

    public static QTMovie initQTMovie() throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl();
        } else {
            throw new InstantiationException("QTKit not present or could not be initialized");
        }
    }

    public static QTMovie initQTMovie(URL url) throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl(url);
        } else {
            throw new InstantiationException("QTKit not present or could not be initialized");
        }
    }

    public static QTMovie initQTMovie(File file) throws InstantiationException, IOException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl(file);
        } else {
            throw new InstantiationException("QTKit not present or could not be initialized");
        }
    }

    public static QTMovie initQTMovie(byte[] bytes) throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl(bytes);
        } else {
            throw new InstantiationException("QTKit not present or could not be initialized");
        }
    }

    public static QTMovie initQTMovie(Properties attributes) throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl(attributes);
        } else {
            throw new InstantiationException("Couldn't init movie");
        }
    }

    public static QTMovie initQTMovie(String name) throws InstantiationException {
        if (QTCubed.usesQTKit()) {
            return new QTKitMovieImpl(name);
        } else {
                throw new InstantiationException("Couldn't init movie");
        }
    }

    public static QTCaptureView initQTCaptureView() {
        if (QTCubed.usesQTKit()) {
            return new QTKitCaptureView();
        } else {
            // TODO: return new QTJavaCaptureView();
            return null;
        }
    }

    public static QTCaptureSession initQTCaptureSession() {
        if (QTCubed.usesQTKit()) {
            return new QTKitCaptureSession();
        } else {
            // TODO: return new QTJavaCaptureSession();
            return null;
        }
    }

    public QTCubedFactory() {
        super();
    }

    public static Collection<QTCaptureDevice> captureDevices() {
        if (QTCubed.usesQTKit()) {
            Collection<QTCaptureDevice> retval = new LinkedList<QTCaptureDevice>();
            retval.addAll(QTKitCaptureDevice.inputDevices());
            return retval;
        } else {
            Collection<QTCaptureDevice> retval = new LinkedList<QTCaptureDevice>();
            //	retval.addAll(QTJavaCaptureDevice.inputDevices());
            return retval;
        }
    }

    public static QTCaptureDevice defaultCaptureDevice(QTMediaType mediaType) {
        if (QTCubed.usesQTKit()) {
            return QTKitCaptureDevice.defaultInputDevice(mediaType);
        } else {
            // return QTJavaCaptureDevice.defaultInputDevice(mediaType);
            return null;
        }
    }

    public static QTCaptureDeviceInput initQTCaptureDeviceInput(QTCaptureDevice captureDevice) {
        if (captureDevice instanceof QTKitCaptureDevice) {
            return new QTKitCaptureDeviceInput((QTKitCaptureDevice) captureDevice);
        } else {
            // TODO: return new QTJavaCaptureDeviceInput((QTJavaCaptureDevice)captureDevice));
            return null;
        }
    }

    public static QTCaptureDecompressedAudioOutput initQTCaptureDecompressedAudioOutput() {
        if (QTCubed.usesQTKit()) {
            return new QTKitCaptureDecompressedAudioOutput();
        } else {
            // TODO: return new QTJavaCaptureDecompressedAudioOutput();
            return null;
        }
    }

    public static QTCaptureDecompressedVideoOutput initQTCaptureDecompressedVideoOutput() {
        if (QTCubed.usesQTKit()) {
            return new QTKitCaptureDecompressedVideoOutput();
        } else {
            // TODO: return new QTJavaCaptureDecompressedVideoOutput();
            return null;
        }
    }
}
