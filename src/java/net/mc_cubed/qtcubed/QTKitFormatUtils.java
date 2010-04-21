//
//  QTKitFormatUtils.java
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

import java.awt.Dimension;
import java.util.Collection;
import java.util.LinkedList;
import javax.media.Format;
import javax.media.format.VideoFormat;
import javax.media.format.IndexedColorFormat;
import javax.media.format.*;
import javax.media.protocol.ContentDescriptor;
import java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author shadow
 */
public class QTKitFormatUtils {

	public static final Map<QTKitPixelFormat,VideoFormat> pixelFormatMap;
	
	public static final RGBFormat rgb24;
	static {
		pixelFormatMap = new HashMap<QTKitPixelFormat,VideoFormat>();
		pixelFormatMap.put(QTKitPixelFormat.e16BE555,new RGBFormat(null, Format.NOT_SPECIFIED, Format.shortArray, -1.0f, 16, 0x7c00, 0x3e0, 0x1f, 2, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e16LE555,new RGBFormat(null, Format.NOT_SPECIFIED, Format.shortArray, -1.0f, 16, 0x7c00, 0x3e0, 0x1f, 2, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e16LE5551,new RGBFormat(null, Format.NOT_SPECIFIED, Format.shortArray, -1.0f, 16, 0xf800, 0x7c0, 0x3e, 2, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e16BE565,new RGBFormat(null, Format.NOT_SPECIFIED, Format.shortArray, -1.0f, 16, 0xf800, 0x7e0, 0x1f, 2, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e16LE565,new RGBFormat(null, Format.NOT_SPECIFIED, Format.shortArray, -1.0f, 16, 0xf800, 0x7e0, 0x1f, 2, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN));
		rgb24 = new RGBFormat(null, Format.NOT_SPECIFIED, Format.byteArray, -1.0f, 24, 1, 2, 3, 3, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
		pixelFormatMap.put(QTKitPixelFormat.e24RGB,rgb24);
		pixelFormatMap.put(QTKitPixelFormat.e24BGR,new RGBFormat(null, Format.NOT_SPECIFIED, Format.byteArray, -1.0f, 24, 3, 2, 1, 3, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e32ARGB,new RGBFormat(null, Format.NOT_SPECIFIED, Format.intArray, -1.0f, 32, 0xff00, 0xff0000, 0xff000000, 1, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e32BGRA,new RGBFormat(null, Format.NOT_SPECIFIED, Format.intArray, -1.0f, 32, 0xff0000, 0xff00, 0xff, 1, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e32ABGR,new RGBFormat(null, Format.NOT_SPECIFIED, Format.intArray, -1.0f, 32, 0xff, 0xff00, 0xff0000, 1, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e32RGBA,new RGBFormat(null, Format.NOT_SPECIFIED, Format.intArray, -1.0f, 32, 0xff000000, 0xff0000, 0xff00, 1, Format.NOT_SPECIFIED, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN));
		pixelFormatMap.put(QTKitPixelFormat.e422YpCbCr8,new YUVFormat(null, Format.NOT_SPECIFIED, Format.byteArray, -1.0f, YUVFormat.YUV_YUYV | YUVFormat.YUV_SIGNED, Format.NOT_SPECIFIED, 1, 0, 4, 6));
		pixelFormatMap.put(QTKitPixelFormat.e420YpCbCr8Planar,new YUVFormat(null,Format.NOT_SPECIFIED,Format.byteArray, -1.0f,YUVFormat.YUV_420,Format.NOT_SPECIFIED, 8, 0, 4, 6));
		pixelFormatMap.put(QTKitPixelFormat.e1Monochrome,new IndexedColorFormat(null,Format.NOT_SPECIFIED,Format.byteArray,-1.0f,Format.NOT_SPECIFIED,1,spacedByteValues(1),spacedByteValues(1),spacedByteValues(1)));
		pixelFormatMap.put(QTKitPixelFormat.e2Indexed,new IndexedColorFormat(null,Format.NOT_SPECIFIED,Format.byteArray,-1.0f,Format.NOT_SPECIFIED,2,spacedByteValues(2),spacedByteValues(2),spacedByteValues(2)));
		pixelFormatMap.put(QTKitPixelFormat.e4Indexed,new IndexedColorFormat(null,Format.NOT_SPECIFIED,Format.byteArray,-1.0f,Format.NOT_SPECIFIED,2,spacedByteValues(4),spacedByteValues(4),spacedByteValues(4)));
		pixelFormatMap.put(QTKitPixelFormat.e8Indexed,new IndexedColorFormat(null,Format.NOT_SPECIFIED,Format.byteArray,-1.0f,Format.NOT_SPECIFIED,2,spacedByteValues(8),spacedByteValues(8),spacedByteValues(8)));
	}
	
	public static byte[] spacedByteValues(int bits) {
		int numValues = (int)Math.pow(2, bits);
		byte[] retval = new byte[numValues];
		for (int i = 0;i<numValues;i++) {
			retval[0] = (byte)(((float)i/(numValues-1)) * 255);
		}
		return retval;
	}
    public static Collection<Format> QTKitToJMF(Collection<QTKitFormatDescription> formatDescriptions) {
        Collection<Format> retval = new LinkedList<Format>();
        for (QTKitFormatDescription formatDescription : formatDescriptions) {
            retval.add(FormatDescriptionToFormat(formatDescription));
        }
        return retval;
    }

    public static Format FormatDescriptionToFormat(QTKitFormatDescription formatDescription) {
        float frameRate = 30;
        Dimension size = null;
        if (formatDescription.width != null && formatDescription.height != null) {
            size = new Dimension(formatDescription.width, formatDescription.height);
        }
        int dataSize;
        if (formatDescription.formatType == null) {
            return null;
        }
        switch (formatDescription.formatType) {
            case Raw:
                return (new ContentDescriptor(ContentDescriptor.RAW));
            case JPEG:
                return (new JPEGFormat());
            case PlanarRGB:
                return (new RGBFormat());
            case H261:
                return (new H261Format());
            case H263:
                return (new H263Format());
            case ComponentVideoSigned:
                dataSize = (int) size.getWidth() * (int) size.getHeight() * 2;
                return (new YUVFormat(size, dataSize, Format.shortArray, frameRate, YUVFormat.YUV_SIGNED, (int) size.getWidth() * 2, 8, 0, 4, 6));
            case ComponentVideoUnsigned:
                dataSize = (int) size.getWidth() * (int) size.getHeight() * 2;
                return (new YUVFormat(size, dataSize, Format.shortArray, frameRate, YUVFormat.YUV_YUYV, (int) size.getWidth() * 2, 8, 0, 4, 6));
            case MPEG4Visual:
                return (new VideoFormat("mp4v"));
            case YUV420:
                dataSize = (int) size.getWidth() * (int) size.getHeight();
                return (new YUVFormat(size, dataSize, Format.byteArray, frameRate, YUVFormat.YUV_420, (int) size.getWidth(), Format.NOT_SPECIFIED, 0, 4, 6));
//            case SorensonYUV9:
//                return (new YUVFormat(YUVFormat.YUV_YVU9));
            case LinearPCM:
                return (new AudioFormat(AudioFormat.LINEAR));
            case AC3:
                return (new AudioFormat(AudioFormat.DOLBYAC3));
            case AppleIMA4:
                return (new AudioFormat(AudioFormat.IMA4));
            case ULaw:
                return (new AudioFormat(AudioFormat.ULAW));
            case ALaw:
                return (new AudioFormat(AudioFormat.ALAW));
            case MPEGLayer2:
                return (new AudioFormat(AudioFormat.MPEG));
            case MPEGLayer3:
                return (new AudioFormat(AudioFormat.MPEGLAYER3));
            case MPEG4AAC_HE:
                return (new AudioFormat("aach"));
            case MPEG4AAC_LD:
                return (new AudioFormat("aacl"));
            case MPEG4AAC_HE_V2:
                return (new AudioFormat("aacp"));
            case MPEG4AAC_Spatial:
                return (new AudioFormat("aacs"));
            case DVIIntelIMA:
                return (new AudioFormat(AudioFormat.DVI));
            case MicrosoftGSM:
                return (new AudioFormat(AudioFormat.GSM_MS));
            case AES3:
                return (new AudioFormat("aes3"));
            default:
                // Nothing we can do
                return null;
        }
    }

	public static VideoFormat CompleteFormat(VideoFormat videoFormat,Dimension size, float frameRate) {
		VideoFormat format;
		int lineSize, dataSize,pixelStride;

		if (videoFormat instanceof RGBFormat) {
			RGBFormat baseFormat = (RGBFormat)videoFormat;
			pixelStride = baseFormat.getPixelStride();
			lineSize = (size != null) ? (int) size.getWidth() * pixelStride : Format.NOT_SPECIFIED;
			dataSize = (size != null) ? lineSize * (int) size.getHeight() : Format.NOT_SPECIFIED;
			format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
		} else if (videoFormat instanceof YUVFormat) {
			YUVFormat baseFormat = (YUVFormat)videoFormat;
			dataSize = (size != null) ? (int) size.getWidth() * (int) size.getHeight() : Format.NOT_SPECIFIED;
			format = new YUVFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getYuvType(), (int) size.getWidth(), 1, baseFormat.getOffsetY(), baseFormat.getOffsetU(), baseFormat.getOffsetV());			
		} else if (videoFormat instanceof IndexedColorFormat) {
			IndexedColorFormat baseFormat = (IndexedColorFormat)videoFormat;
			int bpp = baseFormat.getMapSize();
			lineSize = (size != null) ? (int) size.getWidth() * bpp / 8 : Format.NOT_SPECIFIED;
			dataSize = (size != null) ? (int) size.getHeight() * lineSize : Format.NOT_SPECIFIED;
			format = new IndexedColorFormat(size,dataSize,baseFormat.getDataType(),frameRate,lineSize,baseFormat.getMapSize(),baseFormat.getRedValues(),baseFormat.getGreenValues(),baseFormat.getBlueValues());
		} else {
			format = null;
		}
		return format;
	}
    public static Format PixelFormatToJMF(QTKitPixelFormat pixelFormat, Dimension size, float frameRate) {
        Format format;
        if (pixelFormat == null) {
            return null;
        }

		VideoFormat videoFormat = pixelFormatMap.get(pixelFormat);
		if (videoFormat == null) {
			return null;
		}
		
		return CompleteFormat(videoFormat,size,frameRate);
					   
		/*
        switch (pixelFormat) {
            // RGB Formats
            case e16BE555:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e16LE555:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e16LE5551:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e16BE565:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e16LE565:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = baseFormat.getPixelStride();
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e24RGB:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = baseFormat.getPixelStride();
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e24BGR:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = 3;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e32ARGB:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = baseFormat.getPixelStride();
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e32BGRA:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = baseFormat.getPixelStride();
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e32ABGR:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = 1;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e32RGBA:
			{
				RGBFormat baseFormat = (RGBFormat)pixelFormatMap.get(pixelFormat);
                pixelStride = baseFormat.getPixelStride();
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getBitsPerPixel(), baseFormat.getRedMask(), baseFormat.getGreenMask(), baseFormat.getBlueMask(), baseFormat.getPixelStride(), lineSize, baseFormat.getFlipped(), baseFormat.getEndian());
                break;
			}
            case e422YpCbCr8:
			{
				YUVFormat baseFormat = (YUVFormat)pixelFormatMap.get(pixelFormat);
                dataSize = (int) size.getWidth() * (int) size.getHeight();
                format = new YUVFormat(size, dataSize, baseFormat.getDataType(), frameRate, baseFormat.getYuvType(), (int) size.getWidth(), 1, baseFormat.getOffsetY(), baseFormat.getOffsetU(), baseFormat.getOffsetV());
//NS, Format.byteArray, NS,
//			  YUVFormat.YUV_YUYV | YUVFormat.YUV_SIGNED,
//			  NS, NS, 0, 1, 3)
                break;
			}
            case e420YpCbCr8Planar:
			{
				YUVFormat baseFormat = (YUVFormat)pixelFormatMap.get(pixelFormat);
                dataSize = (int) size.getWidth() * (int) size.getHeight();
                format = new YUVFormat(size,dataSize,baseFormat.getDataType(), frameRate,baseFormat.getYuvType(),(int) size.getWidth()*2, 8, baseFormat.getOffsetY(), baseFormat.getOffsetU(), baseFormat.getOffsetV());
                break;
			}
            // Something else we can't represent
            default:
                format = null;
                break;
        }
        return format; */
    }

    public static Collection<QTKitFormatDescription> JMFToQTKit(Collection<Format> formats) {
		// Create a list to hold the result
		Collection<QTKitFormatDescription> retval = new LinkedList<QTKitFormatDescription>();

		// Do format conversions one by one
		for (Format format : formats) {
			retval.add(JMFToQTKit(format));
		}
			
		// return the result
		return retval;
    }
	
	public static QTKitFormatDescription JMFToQTKit(Format format) {
		throw new java.lang.UnsupportedOperationException("Not implemented");
	}
	
	public static Collection<QTKitPixelFormat> JMFToPixelFormat(Collection<Format> formats) {
		// Create a list to hold the result
		Collection<QTKitPixelFormat> retval = new LinkedList<QTKitPixelFormat>();
		
		// Do format conversions one by one
		for (Format format : formats) {
			retval.add(JMFToPixelFormat(format));
		}
		
		// return the result
		return retval;
		
	}

	public static QTKitPixelFormat JMFToPixelFormat(Format format) {
		for (Entry<QTKitPixelFormat,VideoFormat> entry : pixelFormatMap.entrySet()) {
			VideoFormat videoFormat = entry.getValue();
			if (videoFormat.isSameEncoding(format) && format.relax().matches(videoFormat.relax())) {
				// TODO: Do some other checks
				return entry.getKey();
			}
		}
		return null;
	}
}
