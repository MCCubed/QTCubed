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
import javax.media.MediaLocator;
import javax.media.control.FormatControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.CaptureDeviceInfo;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.media.CachingControl;
import javax.media.control.FormatControl;
import javax.media.Time;
import javax.media.Format;
import java.util.Collection;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferStream;
import javax.swing.JLabel;
import net.mc_cubed.qtcubed.*;

/**
 *
 * @author shadow
 */
public class DataSource extends BasicPushBufferDataSource implements CaptureDevice {

    QTCaptureDevice selectedDevice;
    QTCaptureSession session;
    QTKitOutputBufferStream[] streams;
    private String captureDeviceName;
    private String captureParameters;
	private CaptureDeviceInfo myCdi;
	
	public DataSource() {
		this.controls = new Object[] { new QTCubedDSCachingControl(), new QTCubedDSFormatControl() };
	}

    @Override
    public PushBufferStream[] getStreams() {
		Logger.getAnonymousLogger().info("Getting Streams");
        return streams;
    }

    @Override
    public String getContentType() {
        return ContentDescriptor.RAW;
//        return super.getContentType();
    }

    @Override
    public Object getControl(String controlType) {
		Logger.getAnonymousLogger().info("Getting control: " + controlType);
		Object retval = super.getControl(controlType);
		Logger.getAnonymousLogger().info("Found: " + retval);
        return retval;
    }

    @Override
    public Object[] getControls() {
        return super.getControls();
    }
	
	

    @Override
    public Time getDuration() {
        return super.getDuration();
    }

	/**
	 * Return the CaptureDeviceInfo object that describes this device.
	 * Returns:
	 * The CaptureDeviceInfo object that describes this device.	 
	 */
	public CaptureDeviceInfo getCaptureDeviceInfo() {
		return myCdi;
	}
	
	/**
	 * Returns an array of FormatControl objects. Each of them can be used to set and get the format of each capture stream. This method can be used before connect to set and get the capture formats.
	 * Returns:
	 * an array for FormatControls.
	 */
	public FormatControl[] getFormatControls() {
		return new FormatControl[] { (FormatControl)controls[1] };
	}
	
    @Override
    public void connect() throws IOException {
        initCheck();
		
        if (streams != null) {
            disconnect();
        }

        parseLocator();

        selectedDevice = null;
        Collection<QTCaptureDevice> cds = QTCubedFactory.captureDevices();
        for (QTCaptureDevice device : cds) {
            if (device.localizedDisplayName().equalsIgnoreCase(captureDeviceName) || device.uniqueId().equalsIgnoreCase(captureDeviceName)) {
                selectedDevice = device;
                break;
            }
        }

        if (selectedDevice == null) {
            throw new IOException("Cannot find capture device named " + captureDeviceName);
        } else {
			// Do Capture Device Info generation
			String name = selectedDevice.localizedDisplayName();
            MediaLocator ml = new MediaLocator("quicktime://" + selectedDevice.uniqueId());
            Collection<Format> formats = QTFormatUtils.QTKitToJMF(selectedDevice.getFormatDescriptions());
            Format[] formatsArray = formats.toArray(new Format[0]);
            myCdi = new CaptureDeviceInfo(name,ml,formatsArray);
		}

        // Determine roughly the capabilities of the input source
        boolean hasAudio = false, hasVideo = false;
        for (QTFormatDescription desc : selectedDevice.getFormatDescriptions()) {
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
        QTCaptureDeviceInput devInput = QTCubedFactory.initQTCaptureDeviceInput(selectedDevice);
        //QTKitCaptureDeviceInput devInput = new QTKitCaptureDeviceInput(selectedDevice);

        // Connect the input to a session
        session = QTCubedFactory.initQTCaptureSession();
        session.addInput(devInput);

        // Start creating output streams
        List<QTKitOutputBufferStream> outStreams = new ArrayList<QTKitOutputBufferStream>();
        if (hasAudio) {
            QTCaptureDecompressedAudioOutput audioOut = QTCubedFactory.initQTCaptureDecompressedAudioOutput();
            session.addOutput(audioOut);
            QTKitAudioCapture audioCaptureStream = new QTKitAudioCapture(this, audioOut);
            outStreams.add(audioCaptureStream);
        }
        if (hasVideo) {
            QTCaptureDecompressedVideoOutput videoOut = QTCubedFactory.initQTCaptureDecompressedVideoOutput();
            session.addOutput(videoOut);
            QTKitVideoCapture videoCaptureStream = new QTKitVideoCapture(this, videoOut);
            outStreams.add(videoCaptureStream);
        }

        streams = outStreams.toArray(new QTKitOutputBufferStream[0]);
		super.connect();
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
		
		final QTCaptureSession finalSession = session;
		// Remove all the outputs from the stream
		for (QTCaptureOutput output : finalSession.getOutputList()) {
			finalSession.removeOutput(output);
		}
		super.disconnect();
    }

    @Override
    public void start() throws IOException {
        session.startRunning();
		super.start();
    }

    @Override
    public void stop() throws IOException {
        session.stopRunning();
		super.stop();
    }

    private void parseLocator() {
        String rest = getLocator().getRemainder();
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
	
	@Override
	public String toString() {
		return getClass().getName() + "[Locator=" + getLocator() + "]";
	}
	
	protected class QTCubedDSCachingControl implements javax.media.CachingControl {
		public long getContentLength() {
			return CachingControl.LENGTH_UNKNOWN;
		}
		
		public long getContentProgress() {
			return 0;
		}
		
		public boolean isDownloading() {
			return true;
		}
		
		public Component getProgressBarComponent() {
			return new JLabel("Live Capture");
		}
		
		public Component getControlComponent() {
			return null;
		}
	}
	protected class QTCubedDSFormatControl implements javax.media.control.FormatControl {
		public Component getControlComponent() {
			return null;
		}

		public javax.media.Format getFormat() {
			return ((FormatControl)streams[0].getControl(FormatControl.class.getName())).getFormat();
		}
		
		public javax.media.Format setFormat(javax.media.Format format) {
			return ((FormatControl)streams[0].getControl(FormatControl.class.getName())).setFormat(format);
		}
		
		public javax.media.Format[] getSupportedFormats() {
			return ((FormatControl)streams[0].getControl(FormatControl.class.getName())).getSupportedFormats();
		}				
		
		public boolean isEnabled() {
			return true;
		}
		
		public void setEnabled(boolean enabled) {
		}
		 
	}
}
