//
//  QTKitVideoCapture.java
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

import net.mc_cubed.qtcubed.QTCaptureDecompressedVideoOutput;
import net.mc_cubed.qtcubed.QTPixelFormat;
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
import java.util.logging.Logger;
import net.mc_cubed.qtcubed.QTFormatDescription;
import net.mc_cubed.qtcubed.QTFormatUtils;
import java.util.List;
import java.util.LinkedList;
import java.awt.Dimension;

/**
 *
 * @author shadow
 */
public class QTKitVideoCapture extends QTKitOutputBufferStream {

    QTCaptureDecompressedVideoOutput delegator;
    final DataSource dataSource;
    ContentDescriptor myContentDescriptor = new ContentDescriptor(ContentDescriptor.RAW);
    final Control[] controls;

    public QTKitVideoCapture(DataSource dataSource, QTCaptureDecompressedVideoOutput delegator) {
        super(delegator);
        this.delegator = delegator;
        this.dataSource = dataSource;
		controls = new Control[]{new QTKitVideoFormatControl(), new QTKitVideoFrameRateControl()};
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
            if (clazz.isInstance(control)) {
                return control;
            }
        }

        return null;
    }

    private class QTKitVideoFormatControl implements FormatControl {

        final JComponent component;
        final Format[] supportedFormats;

        public QTKitVideoFormatControl() {
            String[] frameSizes = new String[]{"160x120", "320x240", "640x480"};
            component = new JSpinner(new SpinnerListModel(frameSizes));
            component.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent e) {
                    String newFrameSize = (String) ((JSpinner) component).getValue();
                    int xIndex = newFrameSize.indexOf("x");
                    int width = Integer.valueOf(newFrameSize.substring(0, xIndex));
                    int height = Integer.valueOf(newFrameSize.substring(xIndex + 1));
                    VideoFormat format = (VideoFormat) getFormat();
                    VideoFormat newVideoFormat = new VideoFormat(format.getEncoding(), new Dimension(width, height), Format.NOT_SPECIFIED, format.getDataType(), format.getFrameRate());
                }
            });

			// Completed list of formats
			List<VideoFormat> formatList = new LinkedList<VideoFormat>();

			// Get the dimension of the video from the capture device
			QTFormatDescription desc = dataSource.selectedDevice.getFormatDescriptions().iterator().next();
			Dimension size = new Dimension(desc.getWidth(),desc.getHeight());

			// Loop through the formats we can assign and fill in the dimension and data size values
			for (VideoFormat format : QTFormatUtils.pixelFormatMap.values().toArray(new VideoFormat[0])) {
				formatList.add(QTFormatUtils.CompleteFormat(format,size,-1.0f));
			}

			// This is our supported format list
            supportedFormats = formatList.toArray(new Format[0]);
			
			try {
				// If we don't have a format set yet, pick the simplest, most likely to succeed format
				if (getFormat() == null) {
					setFormat(QTFormatUtils.CompleteFormat(QTFormatUtils.rgb24,size,-1.0f));
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
         *		Obtain the format that this object is set to.
         */
        public Format getFormat() {
            Dimension size = delegator.getSize();
			if (size == null) {
				QTFormatDescription desc = dataSource.selectedDevice.getFormatDescriptions().iterator().next();
				size = new Dimension(desc.getWidth(),desc.getHeight());
			}
/*            if (size.height == 0 || size.width == 0) {
                size = new Dimension(640, 480);
            }
*/
            float frameRate = delegator.getFrameRate();

            QTPixelFormat pixelFormat = delegator.getPixelFormat();

            if (pixelFormat == null) {
				return null;
            } else {
				return QTFormatUtils.PixelFormatToJMF(pixelFormat, size, frameRate);
			}
        }

        /**
         *		Lists the possible input formats supported by this plug-in.
         */
        public Format[] getSupportedFormats() {
            return supportedFormats;
        }

        /**
         *		Return the state of the track.
         */
        public boolean isEnabled() {
            return true;
        }

        /**
         *		Enable or disable the track.
         */
        public void setEnabled(boolean enabled) {
            // Do nothing
        }

        /**
         *		Sets the data format.
         */
        public Format setFormat(Format format) {			
			Logger.getAnonymousLogger().info("Setting format: " + format);
			
			// Get the pixel format information
			QTPixelFormat pixelFormat = QTFormatUtils.JMFToPixelFormat(format);
			Dimension size = ((VideoFormat)format).getSize();
			float frameRate = ((VideoFormat)format).getFrameRate();

			// Change the parameters that need changing
			if (delegator.getPixelFormat() != pixelFormat) {
				delegator.setPixelFormat(pixelFormat);
			}
			if (size != null && !delegator.getSize().equals(size)) {
				delegator.setSize(size);
			}
			if (frameRate > 0 && delegator.getFrameRate() != frameRate) {
				delegator.setFrameRate(frameRate);
			}
			
			// Return a pixel format made up of the parameters we just used to set up the output
			return QTFormatUtils.PixelFormatToJMF(pixelFormat, size, frameRate);
        }
    }

    private class QTKitVideoFrameRateControl implements FrameRateControl {

        final JComponent component;

        public QTKitVideoFrameRateControl() {
            Float[] frameRates = new Float[]{1f, 5f, 10f, 15f, 25f, 29.95f, 30f};
            component = new JSpinner(new SpinnerListModel(frameRates));
            component.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent e) {
                    float newFrameRate = (Float) ((JSpinner) component).getValue();
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
        public float getFrameRate() {
            return delegator.getFrameRate();
        }

        /**
         * 		Returns the maximum output frame rate.
         */
        public float getMaxSupportedFrameRate() {
            return 60.0f;
        }

        /**
         * 		Returns the default output frame rate.
         */
        public float getPreferredFrameRate() {
            return 30.0f;
        }

        /**
         * 		Sets the frame rate.
         */
        public float setFrameRate(float newFrameRate) {
            delegator.setFrameRate(newFrameRate);
            return delegator.getFrameRate();
        }
    }
}
