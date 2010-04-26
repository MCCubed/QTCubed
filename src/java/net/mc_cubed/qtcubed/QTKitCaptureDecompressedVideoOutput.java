//
//  QTKitCaptureDecompressedVideoOutput.java
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

import java.util.logging.Logger;
import java.awt.Dimension;
import net.mc_cubed.qtcubed.media.protocol.quicktime.QTCubedDelegator;

/**
 *
 * @author shadow
 */
public class QTKitCaptureDecompressedVideoOutput extends QTKitCaptureOutput implements QTCubedDelegator {

    QTKitCaptureDataDelegate dataDelegate;

    public QTKitCaptureDecompressedVideoOutput() {
        captureOutputRef = _allocInit();
    }

    native protected long _allocInit();

    protected void pushFrame(byte[] frameData,int formatInt, int width, int height, float frameRate) {
        if (dataDelegate != null) {
            QTKitSampleBuffer buffer = new QTKitSampleBuffer(QTKitPixelFormat.forNative(formatInt),width,height,frameRate,frameData);
            dataDelegate.nextSample(buffer);
        } else {
            Logger.getAnonymousLogger().info("Got " + frameData.length + " bytes of data");
        }
		
    }

	protected void pushFrame(short[] frameData,int formatInt, int width, int height, float frameRate) {
        if (dataDelegate != null) {
            QTKitSampleBuffer buffer = new QTKitSampleBuffer(QTKitPixelFormat.forNative(formatInt),width,height,frameRate,frameData);
            dataDelegate.nextSample(buffer);
        } else {
            Logger.getAnonymousLogger().info("Got " + frameData.length + " bytes of data");
        }
		
    }

	protected void pushFrame(int[] frameData,int formatInt, int width, int height, float frameRate) {
        if (dataDelegate != null) {
            QTKitSampleBuffer buffer = new QTKitSampleBuffer(QTKitPixelFormat.forNative(formatInt),width,height,frameRate,frameData);
            dataDelegate.nextSample(buffer);
        } else {
            Logger.getAnonymousLogger().info("Got " + frameData.length + " bytes of data");
        }
		
    }
	
    public void setDataDelegate(QTKitCaptureDataDelegate dataDelegate) {
        this.dataDelegate = dataDelegate;
    }
	
	public void setFrameRate(float newFrameRate) {
		_setFrameRate(captureOutputRef,newFrameRate);
	}

	protected native void _setFrameRate(long captureOutputRef,float newFrameRate);
	
	public float getFrameRate() {
		return _getFrameRate(captureOutputRef);
	}
	
	protected native float _getFrameRate(long captureOutputRef);
	
	public void setSize(int width, int height) {
		_setSize(captureOutputRef,width,height);
	}
	
	public void setSize(Dimension size) {
		_setSize(captureOutputRef,(int)size.getWidth(),(int)size.getHeight());
	}		
	
	protected native void _setSize(long captureOutputRef,int width,int height);
	
	public int getWidth() {
		return _getWidth(captureOutputRef); 
	}
	
	public int getHeight() {
		return _getHeight(captureOutputRef);
	}
	
	public Dimension getSize() {
		return new Dimension(_getWidth(captureOutputRef),_getHeight(captureOutputRef));
	}
	
	protected native int _getWidth(long captureOutputRef);

	protected native int _getHeight(long captureOutputRef);
	
	public QTKitPixelFormat getPixelFormat() {
		return _getPixelFormat(captureOutputRef);
	}
	
	protected native QTKitPixelFormat _getPixelFormat(long captureOutputRef);
	
	public void setPixelFormat(QTKitPixelFormat format) {
		if (format != null) {
			_setPixelFormat(captureOutputRef, format.getNativeValue());
		} else {
			throw new IllegalArgumentException("Format must not be null");
		}
	}
	
	protected native QTKitPixelFormat _setPixelFormat(long captureOutputRef,long nativePixelFormat);
	
	protected void finalize() {
		_release(captureOutputRef);
	}
	
	protected native void _release(long captureOutputRef);
	
}
