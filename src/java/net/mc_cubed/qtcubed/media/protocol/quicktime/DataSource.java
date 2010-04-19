//
//  DataSource.java
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

package net.mc_cubed.qtcubed.media.protocol.quicktime;

import com.sun.media.protocol.BasicPushBufferDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.media.Time;
import javax.media.protocol.PushBufferStream;
import net.mc_cubed.qtcubed.QTKitCaptureDecompressedAudioOutput;
import net.mc_cubed.qtcubed.QTKitCaptureDecompressedVideoOutput;
import net.mc_cubed.qtcubed.QTKitCaptureDevice;
import net.mc_cubed.qtcubed.QTKitCaptureDeviceInput;
import net.mc_cubed.qtcubed.QTKitCaptureSession;
import net.mc_cubed.qtcubed.QTKitFormatDescription;

/**
 *
 * @author shadow
 */
public class DataSource extends BasicPushBufferDataSource {

    QTKitCaptureDevice selectedDevice;
    QTKitCaptureSession session;
    QTKitOutputBufferStream[] streams;
    private String captureDeviceName;
    private String captureParameters;

    @Override
    public PushBufferStream[] getStreams() {
        return streams;
    }

    @Override
    public String getContentType() {
        return super.getContentType();
    }

    @Override
    public Object getControl(String controlType) {
        return super.getControl(controlType);
    }

    @Override
    public Object[] getControls() {
        return super.getControls();
    }

    @Override
    public Time getDuration() {
        return super.getDuration();
    }

    @Override
    public void connect() throws IOException {
        initCheck();

        if (streams != null) {
            disconnect();
        }

        parseLocator();

        selectedDevice = null;
        Collection<QTKitCaptureDevice> cds = QTKitCaptureDevice.inputDevices();
        for (QTKitCaptureDevice device : cds) {
            if (device.localizedDisplayName().equalsIgnoreCase(captureDeviceName) || device.uniqueId().equalsIgnoreCase(captureDeviceName)) {
                selectedDevice = device;
                break;
            }
        }

        if (selectedDevice == null) {
            throw new IOException("Cannot find capture device named " + captureDeviceName);
        }

        // Determine roughly the capabilities of the input source
        boolean hasAudio = false, hasVideo = false;
        for (QTKitFormatDescription desc : selectedDevice.getFormatDescriptions()) {
            switch (desc.getMediaType()) {
                case SOUND:
                    hasAudio = true;
                    break;
                case VIDEO:
                    hasVideo = true;
                    break;
                case MUXED:
                    hasAudio = true;
                    hasVideo = true;
                    break;
            }
        }

        if (!hasAudio && !hasVideo) {
            throw new IOException("Selected capture device does not supply a supported media type");
        }

        // Get the default format
        

        // Open the device
        selectedDevice.open();
        // Get the input
        QTKitCaptureDeviceInput devInput = new QTKitCaptureDeviceInput(selectedDevice);

        // Connect the input to a session
        session = new QTKitCaptureSession();
        session.addInput(devInput);

        // Start creating output streams
        List<QTKitOutputBufferStream> outStreams = new ArrayList<QTKitOutputBufferStream>();
        if (hasAudio) {
            QTKitCaptureDecompressedAudioOutput audioOut = new QTKitCaptureDecompressedAudioOutput();
            session.addOutput(audioOut);
            QTKitAudioCapture audioCaptureStream = new QTKitAudioCapture(audioOut);
            outStreams.add(audioCaptureStream);
        }
        if (hasVideo) {
            QTKitCaptureDecompressedVideoOutput videoOut = new QTKitCaptureDecompressedVideoOutput();
            session.addOutput(videoOut);
            QTKitVideoCapture videoCaptureStream = new QTKitVideoCapture(this, videoOut);
            outStreams.add(videoCaptureStream);
        }

        streams = outStreams.toArray(new QTKitOutputBufferStream[0]);
    }

    @Override
    public void disconnect() {
        if (session != null) {
            if (session.isRunning()) {
                try {
                    stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        }
    }

    @Override
    public void start() throws IOException {
        session.startRunning();
    }

    @Override
    public void stop() throws IOException {
        session.stopRunning();
    }

    private void parseLocator() {
        String rest = getLocator().getRemainder();
        System.out.println("Remainder of locator: " + rest);
        int p1, p2;
        p1 = rest.indexOf("//");
        if (p1 < 0) {
            return;
        } else {
            p2 = rest.indexOf("/", p1 + 2);
            if (p2 != -1) {
                captureDeviceName = rest.substring(p1 + 2, p2);
                captureParameters = rest.substring(p2);
            } else {
                captureDeviceName = rest.substring(p1 + 2);
                captureParameters = null;
            }
        }
    }
}
