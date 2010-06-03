//
//  QTCubedAudioCapture.java
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

import javax.media.Format;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.Control;
import javax.media.control.FormatControl;
import javax.media.format.AudioFormat;
import net.mc_cubed.qtcubed.QTKitCaptureDecompressedAudioOutput;
import net.mc_cubed.qtcubed.QTKitSampleBuffer;
import net.mc_cubed.qtcubed.QTKitFormatUtils;

/**
 *
 * @author shadow
 */
public class QTKitAudioCapture extends QTKitOutputBufferStream {
	DataSource source;
	final Control[] controls;
	AudioFormat lastFormat = new AudioFormat(AudioFormat.LINEAR);
	
    public QTKitAudioCapture(DataSource source,QTKitCaptureDecompressedAudioOutput output) {
        super(output);
		this.source = source;
		controls = new Control[]{ new QTKitAudioFormatControl() };
        // TODO: Do something with the output object

    }

    public Format getFormat() {
		return ((FormatControl)controls[0]).getFormat();
    }

    public ContentDescriptor getContentDescriptor() {
        return new ContentDescriptor(ContentDescriptor.RAW);
    }

    public boolean endOfStream() {
		return false;
    }

    public Object[] getControls() {
        return controls;
    }

    public Object getControl(String string) {
		Class clazz;
		try {
			clazz = Class.forName(string);
		} catch (ClassNotFoundException cnfe) {
			return null;
		}
		for (Control control : controls) {
			if (clazz.isInstance(control)) {
				return control;
			}
		}
		return null;
    }

	private class QTKitAudioFormatControl implements FormatControl {
		
		public void setEnabled(boolean enabled) {
			// Does nothing
		}
		
		public boolean isEnabled() {
			return true;
		}
		
		public Format setFormat(Format format) {
			// Does nothing
			return lastFormat;
		}
		
		public Format getFormat() {
			return lastFormat;
		}
		
		public Format[] getSupportedFormats() {
			return new Format[] {lastFormat};
		}
		
		public java.awt.Component getControlComponent() {
			// No component
			return null;
		}

	}

	@Override
	public void nextSample(QTKitSampleBuffer sampleData) {
		super.nextSample(sampleData);
		lastFormat = QTKitFormatUtils.AudioFormatToJMF(sampleData.getCompressionFormat(),sampleData.getSampleRate(),sampleData.getBitsPerSample(),sampleData.getChannels(),false,true);
	}


}
