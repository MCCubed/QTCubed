//
//  QTKitSampleBuffer.java
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

import javax.media.Format;

/**
 *
 * @author shadow
 */
public class QTKitSampleBuffer {
    private final Class dataClass;

    public Class getDataClass() {
        return dataClass;
    }

    public QTKitSampleBuffer(QTKitPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, byte[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = Format.byteArray;
    }

    public QTKitSampleBuffer(QTKitPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, short[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = Format.shortArray;
    }

    public QTKitSampleBuffer(QTKitPixelFormat pixelFormat, Integer width, Integer height, Float frameRate, int[] rawData) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.rawData = rawData;
        this.dataClass = Format.intArray;
    }

    final QTKitPixelFormat pixelFormat;

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


    public QTKitPixelFormat getPixelFormat() {
        return pixelFormat;
    }

    public Object getRawData() {
        return rawData;
    }

    public Integer getWidth() {
        return width;
    }
    
}
