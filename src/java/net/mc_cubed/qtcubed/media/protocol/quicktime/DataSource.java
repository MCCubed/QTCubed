/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    QTKitCaptureSession session;
    QTKitOutputBufferStream[] streams;
    private String captureDeviceName;
    private Object captureParameters;

    
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

        QTKitCaptureDevice selectedDevice = null;
        Collection<QTKitCaptureDevice> cds = QTKitCaptureDevice.inputDevices();
        for (QTKitCaptureDevice device : cds) {
            if (device.localizedDisplayName().equalsIgnoreCase(captureDeviceName)) {
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
                    hasAudio = true; break;
                case VIDEO:
                    hasVideo = true; break;
                case MUXED:
                    hasAudio = true; hasVideo = true; break;
            }
        }

        if (!hasAudio && !hasVideo) {
            throw new IOException("Selected capture device does not supply a supported media type");
        }

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
            QTKitVideoCapture videoCaptureStream = new QTKitVideoCapture(videoOut);
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
        int p1,p2;
        p1 = rest.indexOf("//");
        p2 = rest.indexOf("/", p1+2);
        captureDeviceName = rest.substring(p1 + 2, p2);
        if (p2 != -1) {
            captureParameters = rest.substring(p2);
        } else {
            captureParameters = null;
        }

    }




}
