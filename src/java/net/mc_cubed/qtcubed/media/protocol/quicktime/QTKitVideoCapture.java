/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mc_cubed.qtcubed.media.protocol.quicktime;

import net.mc_cubed.qtcubed.QTKitCaptureDecompressedVideoOutput;
import javax.media.Format;
import javax.media.protocol.ContentDescriptor;
import javax.media.control.FormatControl;
import javax.media.control.FrameRateControl;
import javax.media.Format;
import java.awt.Component;

/**
 *
 * @author shadow
 */
public class QTKitVideoCapture extends QTKitOutputBufferStream {

	QTKitCaptureDecompressedVideoOutput delegator;
	
    public QTKitVideoCapture(QTKitCaptureDecompressedVideoOutput delegator) {
        super(delegator);
		this.delegator = delegator;
    }

    public Format getFormat() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ContentDescriptor getContentDescriptor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean endOfStream() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] getControls() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getControl(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
	private class QTKitVideoFormatControl implements FormatControl {
		/**
		 *		Get the Component associated with this Control object.
		 *
		 * From the javadocs:
		 * Get the Component associated with this Control object. For example, this method might return a slider for
		 * volume control or a panel containing radio buttons for CODEC control. The getControlComponent method can 
		 * return null if there is no GUI control for this Control.
		 */
		public Component	getControlComponent() {
			return null;
		}

		/**
		 *		Obtain the format that this object is set to.
		 */
		public Format	getFormat()  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
		/**
		 *		Lists the possible input formats supported by this plug-in.
		 */
		public Format[]	getSupportedFormats()  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
		/**
		 *		Return the state of the track.
		 */
		public boolean	isEnabled()  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
		/**
		 *		Enable or disable the track.
		 */
		public void	setEnabled(boolean enabled)  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
		/**
		 *		Sets the data format.
		 */
		public Format	setFormat(Format format)  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
	}
	
	private class QTKitVideoFrameRateControl implements FrameRateControl {
		/**
		 *		Get the Component associated with this Control object.
		 *
		 * From the javadocs:
		 * Get the Component associated with this Control object. For example, this method might return a slider for
		 * volume control or a panel containing radio buttons for CODEC control. The getControlComponent method can 
		 * return null if there is no GUI control for this Control.
		 */
		public Component	getControlComponent() {
			return null;
		}
		/**
		 * 		Returns the current output frame rate.
		 */
		public float	getFrameRate() {
			return delegator.getFrameRate();
		}
		/**
		 * 		Returns the maximum output frame rate.
		 */
		public float	getMaxSupportedFrameRate()  {
			return 60.0f;
		}
		/**
		 * 		Returns the default output frame rate.
		 */
		public float	getPreferredFrameRate()  {
			return 30.0f;
		}
		/**
		 * 		Sets the frame rate.
		 */
		public float	setFrameRate(float newFrameRate)  {
			delegator.setFrameRate(newFrameRate);
			return delegator.getFrameRate();
		}
	}
	
}
