/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mc_cubed.qtcubed.media.protocol.quicktime;

import net.mc_cubed.qtcubed.QTKitCaptureDecompressedVideoOutput;
import net.mc_cubed.qtcubed.QTKitPixelFormat;
import javax.media.Format;
import javax.media.protocol.ContentDescriptor;
import javax.media.control.FormatControl;
import javax.media.control.FrameRateControl;
import javax.media.Control;
import javax.media.Format;
import javax.media.format.VideoFormat;
import javax.media.format.RGBFormat;
import javax.media.format.YUVFormat;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author shadow
 */
public class QTKitVideoCapture extends QTKitOutputBufferStream {

	QTKitCaptureDecompressedVideoOutput delegator;
	
	final DataSource dataSource;
	
	ContentDescriptor myContentDescriptor = new ContentDescriptor(ContentDescriptor.RAW);
	
	Control[] controls = new Control[] { new QTKitVideoFormatControl(), new QTKitVideoFrameRateControl() };
	
    public QTKitVideoCapture(DataSource dataSource, QTKitCaptureDecompressedVideoOutput delegator) {
        super(delegator);
		this.delegator = delegator;
		this.dataSource = dataSource;
    }

    public Format getFormat() {
		return ((FormatControl) getControl("javax.media.control.FormatControl")).getFormat();
    }

    public ContentDescriptor getContentDescriptor() {
        return myContentDescriptor;
    }

    public boolean endOfStream() {
        return !dataSource.session.isRunning();
    }

    public Object[] getControls() {
        return controls;
    }

    public Object getControl(String string) {
		
		Class clazz;

		try {
			clazz = Class.forName(string);		
		} catch (ClassNotFoundException cnfe) {
			// Nothing spectacular, just return null
			return null;
		}
		
		for (Control control : controls) {
			if (clazz.isInstance(controls)) {
				return control;
			}
		}
		
		return null;
    }
	
	private class QTKitVideoFormatControl implements FormatControl {
		final JComponent component;
		
		final Format[] supportedFormats;
		
		public QTKitVideoFormatControl() {
			String[] frameSizes = new String[] { "160x120","320x240","640x480" };
			component = new JSpinner(new SpinnerListModel(frameSizes));
			component.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					String newFrameSize = (String)((JSpinner)component).getValue();
					int xIndex = newFrameSize.indexOf("x");
					int width  = Integer.valueOf(newFrameSize.substring(0,xIndex));
					int height = Integer.valueOf(newFrameSize.substring(xIndex+1));
					VideoFormat format = (VideoFormat)getFormat();
					VideoFormat newVideoFormat = new VideoFormat(format.getEncoding(),new Dimension(width,height),format.getMaxDataLength(),format.getDataType(),format.getFrameRate());
				}
			});
			
			supportedFormats = new Format[] {
				new RGBFormat(),
				new YUVFormat(YUVFormat.YUV_422)
			};
		}
		
		/**
		 *		Get the Component associated with this Control object.
		 *
		 * From the javadocs:
		 * Get the Component associated with this Control object. For example, this method might return a slider for
		 * volume control or a panel containing radio buttons for CODEC control. The getControlComponent method can 
		 * return null if there is no GUI control for this Control.
		 */
		public Component	getControlComponent() {
			return component;
		}

		/**
		 *		Obtain the format that this object is set to.
		 */
		public Format	getFormat()  {
			Dimension size = delegator.getSize();
			float frameRate = delegator.getFrameRate();

			QTKitPixelFormat pixelFormat = delegator.getPixelFormat();
			
			VideoFormat format;
			
			int dataSize,lineSize;
			
			switch (pixelFormat) {
					// RGB Formats
				case e16BE555:
					lineSize = (int)size.getWidth() * 2;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,5,0x7c00,0x3e0,0x1f,2,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e16LE555:
					lineSize = (int)size.getWidth() * 2;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,5,0x7c00,0x3e0,0x1f,2,lineSize,RGBFormat.FALSE,RGBFormat.LITTLE_ENDIAN);
					break;
				case e16LE5551:
					lineSize = (int)size.getWidth() * 2;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,5,0xf800,0x7c0,0x3e,2,lineSize,RGBFormat.FALSE,RGBFormat.LITTLE_ENDIAN);
					break;
				case e16BE565:
					lineSize = (int)size.getWidth() * 2;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,6,0xf800,0x7e0,0x1f,2,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e16LE565:
					lineSize = (int)size.getWidth() * 2;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,6,0xf800,0x7e0,0x1f,2,lineSize,RGBFormat.FALSE,RGBFormat.LITTLE_ENDIAN);
					break;
				case e24RGB:
					lineSize = (int)size.getWidth() * 3;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff0000,0xff00,0xff,3,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e24BGR:
					lineSize = (int)size.getWidth() * 3;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff,0xff00,0xff0000,3,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e32ARGB:
					lineSize = (int)size.getWidth() * 4;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff0000,0xff00,0xff,4,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e32BGRA:
					lineSize = (int)size.getWidth() * 4;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff00,0xff0000,0xff000000,4,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e32ABGR:
					lineSize = (int)size.getWidth() * 4;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff,0xff00,0xff0000,4,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				case e32RGBA:
					lineSize = (int)size.getWidth() * 4;
					dataSize = lineSize * (int)size.getHeight();
					format = new RGBFormat(size,dataSize,Format.byteArray,frameRate,8,0xff000000,0xff0000,0xff00,4,lineSize,RGBFormat.FALSE,RGBFormat.BIG_ENDIAN);
					break;
				
				case e422YpCbCr8:
					dataSize = (int)size.getWidth() * (int)size.getHeight();
					format = new YUVFormat(size,dataSize,Format.byteArray,frameRate,YUVFormat.YUV_YUYV,(int)size.getWidth(),Format.NOT_SPECIFIED,0,4,6);
/*				case e420YpCbCr8Planar:
					format = new YUVFormat(size,dataSize,Format.byteArray,frameRate,YUVFormat.YUV_420,(int)size.getWidth(),Format.NOT_SPECIFIED,0,4,6);
				case e422YpCbCr_4A_8BiPlanar:
					format = new YUVFormat(YUVFormat.YUV_422);
*/					break;

					// Something else we can't represent
				default:
					format = null;
					break;
			}
			
			
			return format;
		}
		/**
		 *		Lists the possible input formats supported by this plug-in.
		 */
		public Format[]	getSupportedFormats()  {
			return supportedFormats;
		}
		/**
		 *		Return the state of the track.
		 */
		public boolean	isEnabled()  {
			return true;
		}
		/**
		 *		Enable or disable the track.
		 */
		public void	setEnabled(boolean enabled)  {
			// Do nothing
		}
		/**
		 *		Sets the data format.
		 */
		public Format	setFormat(Format format)  {
			throw new java.lang.UnsupportedOperationException("Not yet implemented");
		}
	}
	
	private class QTKitVideoFrameRateControl implements FrameRateControl {
		final JComponent component;
		
		public QTKitVideoFrameRateControl() {
			Float[] frameRates = new Float[] { 1f,5f,10f,15f,25f,29.95f,30f };
			component = new JSpinner(new SpinnerListModel(frameRates));
			component.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					float newFrameRate = (Float)((JSpinner)component).getValue();
					setFrameRate(newFrameRate);
				}
			});
		}
		/**
		 *		Get the Component associated with this Control object.
		 *
		 * From the javadocs:
		 * Get the Component associated with this Control object. For example, this method might return a slider for
		 * volume control or a panel containing radio buttons for CODEC control. The getControlComponent method can 
		 * return null if there is no GUI control for this Control.
		 */
		public Component getControlComponent() {
			return component;
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
