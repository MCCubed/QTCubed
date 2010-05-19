//
//  QTKitCompressionFormat.java
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

/**
 *
 * @author shadow
 */
public enum QTKitCompressionFormat {
    Raw(0x72617720),
    Cinepak(0x63766964),
    Graphics(0x736d6320),
    Animation(0x726c6520),
    Video(0x72707a61),
    ComponentVideo(0x79757632),
    JPEG(0x6a706567),
    MotionJPEGA(0x6d6a7061),
    MotionJPEGB(0x6d6a7062),
    SGI(0x2e534749),
    PlanarRGB(0x38425053),
    MacPaint(0x504e5447),
    GIF(0x67696620),
    PhotoCD(0x6b706364),
    QuickDrawGX(0x71646778),
    AVRJPEG(0x61767220),
    OpenDMLJPEG(0x646d6231),
    BMP(0x57524c45),
    WindowsRaw(0x57524157),
    Vector(0x70617468),
    QuickDraw(0x71647277),
    WaterRipple(0x7269706c),
    Fire(0x66697265),
    Cloud(0x636c6f75),
    H261(0x68323631),
    H263(0x68323633),
    DVCPAL(0x64766370),
    DVCPro50NTSC(0x6476356e),
    DVCPro50PAL(0x64763570),
    DVCPro100NTSC(0x6476316e),
    DVCPro100PAL(0x64763170),
    DVCPROHD720p(0x64766870),
    DVCPROHD1080i60(0x64766836),
    DVCPROHD1080i50(0x64766835),
    Base(0x62617365),
    FLC(0x666c6963),
    Targa(0x74676120),
    PNG(0x706e6720),
    ComponentVideoSigned(0x79757675),
    ComponentVideoUnsigned(0x79757673),
    CMYK(0x636d796b),
    MicrosoftVideo1(0x6d737663),
    Sorenson(0x53565131),
    Indeo4(0x49563431),
    MPEG4Visual(0x6d703476),
    e64ARGB(0x62363461),
    e48RGB(0x62343872),
    e32AlphaGray(0x62333261),
    e16Gray(0x62313667),
    MpegYUV420(0x6d797576),
    YUV420(0x79343230),
    SorensonYUV9(0x73797639),
    JPEG2000(0x6d6a7032),
    Pixlet(0x70786c74),
    LinearPCM(0x6c70636d),
    AC3(0x61632d33),
    e60958AC3(0x63616333),
    AppleIMA4(0x696d6134),
    MPEG4AAC(0x61616320),
    MPEG4CELP(0x63656c70),
    MPEG4HVXC(0x68767863),
    MPEG4TwinVQ(0x74777671),
    MACE3(0x4d414333),
    MACE6(0x4d414336),
    ULaw(0x756c6177),
    ALaw(0x616c6177),
    QDesign(0x51444d43),
    QDesign2(0x51444d32),
    QUALCOMM(0x51636c70),
    MPEGLayer1(0x2e6d7031),
    MPEGLayer2(0x2e6d7032),
    MPEGLayer3(0x2e6d7033),
    TimeCode(0x74696d65),
    MIDIStream(0x6d696469),
    ParameterValueStream(0x61707673),
    AppleLossless(0x616c6163),
    MPEG4AAC_HE(0x61616368),
    MPEG4AAC_LD(0x6161636c),
    MPEG4AAC_HE_V2(0x61616370),
    MPEG4AAC_Spatial(0x61616373),
    AMR(0x73616d72),
    Audible(0x41554442),
    iLBC(0x696c6263),
    DVIIntelIMA(0x6D730011),
    MicrosoftGSM(0x6D730031),
    AES3(0x61657333);

	final int nativeValue;

    QTKitCompressionFormat(int nativeValue) {
        this.nativeValue = nativeValue;
    }

    public int getNativeValue() {
        return nativeValue;
    }

    static public QTKitCompressionFormat forNative(int nativeValue) {
//        Logger.getAnonymousLogger().info("Looking for: "+ Integer.toHexString(nativeValue));
        for (QTKitCompressionFormat format : values()) {
            if (nativeValue == format.getNativeValue()) {
//                Logger.getAnonymousLogger().info("Translated " + Integer.toHexString(nativeValue) + " to " + format);
                return format;
            }
        }
        return null;
    }

}
/**
 * This is based on the following enums from Quicktime and CoreAudio:
 *
 * For Video:

enum {
kRawCodecType                 = 'raw ',
kCinepakCodecType             = 'cvid',
kGraphicsCodecType            = 'smc ',
kAnimationCodecType           = 'rle ',
kVideoCodecType               = 'rpza',
kComponentVideoCodecType      = 'yuv2',
kJPEGCodecType                = 'jpeg',
kMotionJPEGACodecType         = 'mjpa',
kMotionJPEGBCodecType         = 'mjpb',
kSGICodecType                 = '.SGI',
kPlanarRGBCodecType           = '8BPS',
kMacPaintCodecType            = 'PNTG',
kGIFCodecType                 = 'gif ',
kPhotoCDCodecType             = 'kpcd',
kQuickDrawGXCodecType         = 'qdgx',
kAVRJPEGCodecType             = 'avr ',
kOpenDMLJPEGCodecType         = 'dmb1',
kBMPCodecType                 = 'WRLE',
kWindowsRawCodecType          = 'WRAW',
kVectorCodecType              = 'path',
kQuickDrawCodecType           = 'qdrw',
kWaterRippleCodecType         = 'ripl',
kFireCodecType                = 'fire',
kCloudCodecType               = 'clou',
kH261CodecType                = 'h261',
kH263CodecType                = 'h263',
kDVCNTSCCodecType             = 'dvc ', // DV - NTSC and DVCPRO NTSC (available in QuickTime 6.0 or later)
// NOTE: kDVCProNTSCCodecType is deprecated.
// Use kDVCNTSCCodecType instead -- as far as the codecs are concerned,
// the two data formats are identical.
kDVCPALCodecType              = 'dvcp',
kDVCProPALCodecType           = 'dvpp', // available in QuickTime 6.0 or later
kDVCPro50NTSCCodecType        = 'dv5n',
kDVCPro50PALCodecType         = 'dv5p',
kDVCPro100NTSCCodecType       = 'dv1n',
kDVCPro100PALCodecType        = 'dv1p',
kDVCPROHD720pCodecType        = 'dvhp',
kDVCPROHD1080i60CodecType     = 'dvh6',
kDVCPROHD1080i50CodecType     = 'dvh5',
kBaseCodecType                = 'base',
kFLCCodecType                 = 'flic',
kTargaCodecType               = 'tga ',
kPNGCodecType                 = 'png ',
kTIFFCodecType                = 'tiff', // NOTE: despite what might seem obvious from the two constants
// below and their names, they really are correct. 'yuvu' really
// does mean signed, and 'yuvs' really does mean unsigned. Really.
kComponentVideoSigned         = 'yuvu',
kComponentVideoUnsigned       = 'yuvs',
kCMYKCodecType                = 'cmyk',
kMicrosoftVideo1CodecType     = 'msvc',
kSorensonCodecType            = 'SVQ1',
kSorenson3CodecType           = 'SVQ3', // available in QuickTime 5 and later
kIndeo4CodecType              = 'IV41',
kMPEG4VisualCodecType         = 'mp4v',
k64ARGBCodecType              = 'b64a',
k48RGBCodecType               = 'b48r',
k32AlphaGrayCodecType         = 'b32a',
k16GrayCodecType              = 'b16g',
kMpegYUV420CodecType          = 'myuv',
kYUV420CodecType              = 'y420',
kSorensonYUV9CodecType        = 'syv9',
k422YpCbCr8CodecType          = '2vuy', // Component Y'CbCr 8-bit 4:2:2
k444YpCbCr8CodecType          = 'v308', // Component Y'CbCr 8-bit 4:4:4
k4444YpCbCrA8CodecType        = 'v408', // Component Y'CbCrA 8-bit 4:4:4:4
k422YpCbCr16CodecType         = 'v216', // Component Y'CbCr 10,12,14,16-bit 4:2:2
k422YpCbCr10CodecType         = 'v210', // Component Y'CbCr 10-bit 4:2:2
k444YpCbCr10CodecType         = 'v410', // Component Y'CbCr 10-bit 4:4:4
k4444YpCbCrA8RCodecType       = 'r408', // Component Y'CbCrA 8-bit 4:4:4:4, rendering format. full range alpha, zero biased yuv
kJPEG2000CodecType            = 'mjp2',
kPixletCodecType              = 'pxlt',
kH264CodecType                = 'avc1'
};

 * And for audio:

enum
{
kAudioFormatLinearPCM               = 'lpcm',
kAudioFormatAC3                     = 'ac-3',
kAudioFormat60958AC3                = 'cac3',
kAudioFormatAppleIMA4               = 'ima4',
kAudioFormatMPEG4AAC                = 'aac ',
kAudioFormatMPEG4CELP               = 'celp',
kAudioFormatMPEG4HVXC               = 'hvxc',
kAudioFormatMPEG4TwinVQ             = 'twvq',
kAudioFormatMACE3                   = 'MAC3',
kAudioFormatMACE6                   = 'MAC6',
kAudioFormatULaw                    = 'ulaw',
kAudioFormatALaw                    = 'alaw',
kAudioFormatQDesign                 = 'QDMC',
kAudioFormatQDesign2                = 'QDM2',
kAudioFormatQUALCOMM                = 'Qclp',
kAudioFormatMPEGLayer1              = '.mp1',
kAudioFormatMPEGLayer2              = '.mp2',
kAudioFormatMPEGLayer3              = '.mp3',
kAudioFormatTimeCode                = 'time',
kAudioFormatMIDIStream              = 'midi',
kAudioFormatParameterValueStream    = 'apvs',
kAudioFormatAppleLossless           = 'alac',
kAudioFormatMPEG4AAC_HE             = 'aach',
kAudioFormatMPEG4AAC_LD             = 'aacl',
kAudioFormatMPEG4AAC_HE_V2          = 'aacp',
kAudioFormatMPEG4AAC_Spatial        = 'aacs',
kAudioFormatAMR                     = 'samr',
kAudioFormatAudible                 = 'AUDB',
kAudioFormatiLBC                    = 'ilbc',
kAudioFormatDVIIntelIMA             = 0x6D730011,
kAudioFormatMicrosoftGSM            = 0x6D730031,
kAudioFormatAES3                    = 'aes3'
};

 */
