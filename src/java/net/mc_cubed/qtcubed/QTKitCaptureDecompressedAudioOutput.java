//
//  QTKitCaptureDecompressedAudioOutput.java
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

import net.mc_cubed.qtcubed.media.protocol.quicktime.QTCubedDelegator;

/**
 *
 * @author shadow
 */
public class QTKitCaptureDecompressedAudioOutput extends QTKitCaptureOutput implements QTCubedDelegator {

    protected QTKitCaptureDataDelegate dataDelegate;

    public QTKitCaptureDecompressedAudioOutput() {
		this.captureOutputRef = _allocInit();
    }

    native protected long _allocInit();

    public void setDataDelegate(QTKitCaptureDataDelegate dataDelegate) {
        this.dataDelegate = dataDelegate;
    }
	
	void pushBuffer(byte[] buffer, int length, int formatNum, int sampleRate, int bitsPerSample, int channels, boolean isSigned) {	
		
	}
	void pushBuffer(short[] buffer, int length, int formatNum, int sampleRate, int bitsPerSample, int channels, boolean isSigned) {
		
	}
	void pushBuffer(int[] buffer, int length, int formatNum, int sampleRate, int bitsPerSample, int channels, boolean isSigned) {
		
	}
	void pushBuffer(float[] buffer, int length, int formatNum, int sampleRate, int bitsPerSample, int channels) {
		
	}

	protected void finalize() {
		_release(captureOutputRef);
	}
	
	protected native void _release(long captureOutputRef);
	
}
