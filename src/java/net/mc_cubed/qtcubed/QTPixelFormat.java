//
//  QTPixelFormat.java
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

public enum QTPixelFormat {
	e1Monochrome(0x00000001), /* 1 bit indexed */
	e2Indexed(0x00000002), /* 2 bit indexed */
	e4Indexed(0x00000004), /* 4 bit indexed */
	e8Indexed(0x00000008), /* 8 bit indexed */
	e1IndexedGray_WhiteIsZero(0x00000021), /* 1 bit indexed gray, white is zero */
	e2IndexedGray_WhiteIsZero(0x00000022), /* 2 bit indexed gray, white is zero */
	e4IndexedGray_WhiteIsZero(0x00000024), /* 4 bit indexed gray, white is zero */
	e8IndexedGray_WhiteIsZero(0x00000028), /* 8 bit indexed gray, white is zero */
	e16BE555 (0x00000010), /* 16 bit BE RGB 555 */
	e16LE555 (0x4c353535),     /* 16 bit LE RGB 555 */
	e16LE5551(0x35353531),     /* 16 bit LE RGB 5551 */
	e16BE565 (0x42353635),     /* 16 bit BE RGB 565 */
	e16LE565 (0x4c353635),     /* 16 bit LE RGB 565 */
	e24RGB   (0x00000018), /* 24 bit RGB */
	e24BGR   (0x32344247),     /* 24 bit BGR */
	e32ARGB  (0x00000020), /* 32 bit ARGB */
	e32BGRA  (0x42475241),     /* 32 bit BGRA */
	e32ABGR  (0x41424752),     /* 32 bit ABGR */
	e32RGBA  (0x52474241),     /* 32 bit RGBA */
	e64ARGB  (0x62363461),     /* 64 bit ARGB, 16-bit big-endian samples */
	e48RGB   (0x62343872),     /* 48 bit RGB, 16-bit big-endian samples */
	e32AlphaGray(0x62333261),     /* 32 bit AlphaGray, 16-bit big-endian samples, black is zero */
	e16Gray  (0x62313667),     /* 16 bit Grayscale, 16-bit big-endian samples, black is zero */
	e422YpCbCr8(0x32767579),     /* Component Y'CbCr 8-bit 4:2:2, ordered Cb Y'0 Cr Y'1 */
	e4444YpCbCrA8(0x76343038),     /* Component Y'CbCrA 8-bit 4:4:4:4, ordered Cb Y' Cr A */
	e4444YpCbCrA8R(0x72343038),     /* Component Y'CbCrA 8-bit 4:4:4:4, rendering format. full range alpha, zero biased YUV, ordered A Y' Cb Cr */
	e444YpCbCr8(0x76333038),     /* Component Y'CbCr 8-bit 4:4:4 */
	e422YpCbCr16(0x76323136),     /* Component Y'CbCr 10,12,14,16-bit 4:2:2 */
	e422YpCbCr10(0x76323130),     /* Component Y'CbCr 10-bit 4:2:2 */
	e444YpCbCr10(0x76343130),     /* Component Y'CbCr 10-bit 4:4:4 */
	e420YpCbCr8Planar(0x79343230),   /* Planar Component Y'CbCr 8-bit 4:2:0.  baseAddr points to a big-endian CVPlanarPixelBufferInfo_YCbCrPlanar struct */
	e422YpCbCr_4A_8BiPlanar(0x61327679); /* First plane: Video-range Component Y'CbCr 8-bit 4:2:2, ordered Cb Y'0 Cr Y'1; second plane: alpha 8-bit 0-255 */

	final int nativeValue;

	QTPixelFormat(int nativeValue) {
		this.nativeValue = nativeValue;
	}

	public int getNativeValue() {
		return nativeValue;
	}

        static public QTPixelFormat forNative(int nativeValue) {
            for (QTPixelFormat format : QTPixelFormat.values()) {
                if (nativeValue == format.getNativeValue()) {
                    return format;
                }
            }
            return null;
        }

}