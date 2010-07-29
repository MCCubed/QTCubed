//
//  QTSampleBuffer.java
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

/**
 *
 * @author shadow
 */
public class QTSampleBuffer {
    private final Class dataClass;
	private final QTCompressionFormat audioFormat;
	private final Integer sampleRate;
	private final Integer bitsPerSample;
	private final Integer channels;
	private final Boolean signed;
	
	public QTCompressionFormat getCompressionFormat() {
		return audioFormat;
	}
	
	public Integer getSampleRate() {
		return sampleRate;
	}
	
	public Integer getBitsPerSample() {
		return bitsPerSample;
	}
	
	public Integer getChannels() {
		return channels;
	}
		
	public Boolean isSigned() {
		return signed;
	}
	
    public Class getDataClass() {
        return dataClass;
    }
	public QTSampleBuffer(QTCompressionFormat audioFormat,Integer sampleRate, Integer bitsPerSample, Integer channels, Boolean isSigned, byte[] rawData) {
        this.dataClass = rawData.getClass();
		this.audioFormat = audioFormat;
		this.sampleRate = sampleRate;
		this.bitsPerSample = bitsPerSample;
		this.channels = channels;
		this.signed = isSigned;
		pixelFormat = null;
		width = -1;
		height = -1;
		this.rawData = rawData;
	}
	public QTSampleBuffer(QTCompressionFormat audioFormat,Integer sampleRate, Integer bitsPerSample, Integer channels, Boolean isSigned, short[] rawData) {
        this.dataClass = rawData.getClass();
		this.audioFormat = audioFormat;
		this.sampleRate = sampleRate;
		this.bitsPerSample = bitsPerSample;
		this.channels = channels;
		this.signed = isSigned;
		pixelFormat = null;
		width = -1;
		height = -1;
		this.rawData = rawData;
	}
	public QTSampleBuffer(QTCompressionFormat audioFormat,Integer sampleRate, Integer bitsPerSample, Integer channels, Boolean isSigned, int[] rawData) {
        this.dataClass = rawData.getClass();
		this.audioFormat = audioFormat;
		this.sampleRate = sampleRate;
		this.bitsPerSample = bitsPerSample;
		this.channels = channels;
		this.signed = isSigned;
		pixelFormat = null;
		width = -1;
		height = -1;
		this.rawData = rawData;
	}
	public QTSampleBuffer(QTCompressionFormat audioFormat,Integer sampleRate, Integer bitsPerSample, Integer channels, float[] rawData) {
		dataClass = new float[0].getClass();
		this.audioFormat = audioFormat;
		this.sampleRate = sampleRate;
		this.bitsPerSample = bitsPerSample;
		this.channels = channels;
		this.signed = true;
		pixelFormat = null;
		width = -1;
		height = -1;
		this.rawData = rawData;
	}

    public QTSampleBuffer(QTPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, byte[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = rawData.getClass();
		this.audioFormat = null;
		this.sampleRate = null;
		this.bitsPerSample = null;
		this.channels = null;
		this.signed = null;		
    }

    public QTSampleBuffer(QTPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, short[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = rawData.getClass();
		this.audioFormat = null;
		this.sampleRate = null;
		this.bitsPerSample = null;
		this.channels = null;
		this.signed = null;
    }

    public QTSampleBuffer(QTPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, int[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = rawData.getClass();
		this.audioFormat = null;
		this.sampleRate = null;
		this.bitsPerSample = null;
		this.channels = null;
		this.signed = null;
    }

    final QTPixelFormat pixelFormat;

    final Integer width;

    final Integer height;

    final Object rawData;

    protected Float frameRate;

    /**
     * Get the value of frameRate
     *
     * @return the value of frameRate
     */
    public Float getFrameRate() {
        return frameRate;
    }

    /**
     * Set the value of frameRate
     *
     * @param frameRate new value of frameRate
     */
    public void setFrameRate(Float frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getHeight() {
        return height;
    }


    public QTPixelFormat getPixelFormat() {
        return pixelFormat;
    }

    public Object getRawData() {
        return rawData;
    }

    public Integer getWidth() {
        return width;
    }
    
}
