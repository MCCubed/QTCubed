package net.mc_cubed.qtcubed;

import com.sun.media.protocol.BasicPushBufferDataSource;
import javax.media.CaptureDeviceInfo;
import javax.media.control.FormatControl;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.PushBufferStream;

public class QTCaptureDevice extends BasicPushBufferDataSource implements CaptureDevice {

	public CaptureDeviceInfo getCaptureDeviceInfo() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public PushBufferStream[] getStreams() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public FormatControl[] getFormatControls() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
/*	public void connect()
    throws IOException;
	
	public void disconnect();
	
	public void start() throws IOException {
		
	}
	
	public void stop() throws IOException {
		
	}*/
}