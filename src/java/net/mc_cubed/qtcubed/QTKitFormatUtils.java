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
import javax.media.format.*;
import javax.media.protocol.ContentDescriptor;

/**
 *
 * @author shadow
 */
public class QTKitFormatUtils {

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

    public static Format PixelFormatToJMF(QTKitPixelFormat pixelFormat, Dimension size, float frameRate) {
        Format format;
        int lineSize, dataSize,pixelStride;
        if (pixelFormat == null) {
            return null;
        }

        switch (pixelFormat) {
            // RGB Formats
            case e16BE555:
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.shortArray, frameRate, 16, 0x7c00, 0x3e0, 0x1f, 2, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e16LE555:
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.shortArray, frameRate, 16, 0x7c00, 0x3e0, 0x1f, 2, lineSize, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN);
                break;
            case e16LE5551:
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.shortArray, frameRate, 16, 0xf800, 0x7c0, 0x3e, 2, lineSize, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN);
                break;
            case e16BE565:
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.shortArray, frameRate, 16, 0xf800, 0x7e0, 0x1f, 2, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e16LE565:
                lineSize = (int) size.getWidth() * 2;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.shortArray, frameRate, 16, 0xf800, 0x7e0, 0x1f, 2, lineSize, RGBFormat.FALSE, RGBFormat.LITTLE_ENDIAN);
                break;
            case e24RGB:
                pixelStride = 3;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.byteArray, frameRate, 24, 1, 2, 3, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e24BGR:
                pixelStride = 3;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.byteArray, frameRate, 24, 3, 2, 1, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e32ARGB:
                pixelStride = 1;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.intArray, frameRate, 32, 0xff00, 0xff0000, 0xff000000, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e32BGRA:
                pixelStride = 1;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.intArray, frameRate, 32, 0xff0000, 0xff00, 0xff, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e32ABGR:
                pixelStride = 1;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.intArray, frameRate, 32, 0xff, 0xff00, 0xff0000, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;
            case e32RGBA:
                pixelStride = 1;
                lineSize = (int) size.getWidth() * pixelStride;
                dataSize = lineSize * (int) size.getHeight();
                format = new RGBFormat(size, dataSize, Format.intArray, frameRate, 32, 0xff000000, 0xff0000, 0xff00, pixelStride, lineSize, RGBFormat.FALSE, RGBFormat.BIG_ENDIAN);
                break;

            case e422YpCbCr8:
                dataSize = (int) size.getWidth() * (int) size.getHeight();
                format = new YUVFormat(size, dataSize, Format.byteArray, frameRate, YUVFormat.YUV_YUYV | YUVFormat.YUV_SIGNED, (int) size.getWidth(), 1, 0, 4, 6);
//NS, Format.byteArray, NS,
//			  YUVFormat.YUV_YUYV | YUVFormat.YUV_SIGNED,
//			  NS, NS, 0, 1, 3)
                break;
            case e420YpCbCr8Planar:
                dataSize = (int) size.getWidth() * (int) size.getHeight();
                format = new YUVFormat(size,dataSize,Format.byteArray, frameRate,YUVFormat.YUV_420,(int) size.getWidth()*2, 8, 0, 4, 6);
                break;
            // Something else we can't represent
            default:
                format = null;
                break;
        }
        return format;
    }

    public static Collection<QTKitFormatDescription> JMFToQTKit(Collection<Format> formats) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
