//
//  QTKitPixelFormat.java
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

package net.mc_cubed.qtcubed;

public enum QTKitPixelFormat {
	e1Monochrome, /* 1 bit indexed */
	e2Indexed, /* 2 bit indexed */
	e4Indexed, /* 4 bit indexed */
	e8Indexed, /* 8 bit indexed */
	e1IndexedGray_WhiteIsZero, /* 1 bit indexed gray, white is zero */
	e2IndexedGray_WhiteIsZero, /* 2 bit indexed gray, white is zero */
	e4IndexedGray_WhiteIsZero, /* 4 bit indexed gray, white is zero */
	e8IndexedGray_WhiteIsZero, /* 8 bit indexed gray, white is zero */
	e16BE555, /* 16 bit BE RGB 555 */
	e16LE555,     /* 16 bit LE RGB 555 */
	e16LE5551,     /* 16 bit LE RGB 5551 */
	e16BE565,     /* 16 bit BE RGB 565 */
	e16LE565,     /* 16 bit LE RGB 565 */
	e24RGB, /* 24 bit RGB */
	e24BGR,     /* 24 bit BGR */
	e32ARGB, /* 32 bit ARGB */
	e32BGRA,     /* 32 bit BGRA */
	e32ABGR,     /* 32 bit ABGR */
	e32RGBA,     /* 32 bit RGBA */
	e64ARGB,     /* 64 bit ARGB, 16-bit big-endian samples */
	e48RGB,     /* 48 bit RGB, 16-bit big-endian samples */
	e32AlphaGray,     /* 32 bit AlphaGray, 16-bit big-endian samples, black is zero */
	e16Gray,     /* 16 bit Grayscale, 16-bit big-endian samples, black is zero */
	e422YpCbCr8,     /* Component Y'CbCr 8-bit 4:2:2, ordered Cb Y'0 Cr Y'1 */
	e4444YpCbCrA8,     /* Component Y'CbCrA 8-bit 4:4:4:4, ordered Cb Y' Cr A */
	e4444YpCbCrA8R,     /* Component Y'CbCrA 8-bit 4:4:4:4, rendering format. full range alpha, zero biased YUV, ordered A Y' Cb Cr */
	e444YpCbCr8,     /* Component Y'CbCr 8-bit 4:4:4 */
	e422YpCbCr16,     /* Component Y'CbCr 10,12,14,16-bit 4:2:2 */
	e422YpCbCr10,     /* Component Y'CbCr 10-bit 4:2:2 */
	e444YpCbCr10,     /* Component Y'CbCr 10-bit 4:4:4 */
	e420YpCbCr8Planar,   /* Planar Component Y'CbCr 8-bit 4:2:0.  baseAddr points to a big-endian CVPlanarPixelBufferInfo_YCbCrPlanar struct */
	e422YpCbCr_4A_8BiPlanar; /* First plane: Video-range Component Y'CbCr 8-bit 4:2:2, ordered Cb Y'0 Cr Y'1; second plane: alpha 8-bit 0-255 */

}