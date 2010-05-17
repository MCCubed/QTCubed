//
//  QTCodec.h
//  QTCubed
//
//  Created by Chappell Charles on 10/04/15.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
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

#import <QuickTime/QuickTime.h>
#import <QuickTime/ImageCompression.h>
#import "QTCodecProtocol.h"

#define kTimeScale 100000

@interface QTCodec : NSObject <QTCodecProtocol> {
#if !__LP64__
	// Do the work locally, we're 32 bit
@private
	ICMCompressionSessionRef    compressionSession;
	ICMDecompressionSessionRef  decompressionSession;
#else
	// Create an IPC proxy to the 32 bit process
	id proxy;
#endif
	
}
#if !__LP64__
- (void) doneCompressingFrame:(ICMEncodedFrameRef)frame;
- (void) doneDecompressingFrame:(CVPixelBufferRef)buffer;
+ (ICMCompressionSessionOptionsRef) defaultOptions;
- (BOOL) startCompressionSession:(CodecType)codec pixelsWide:(unsigned)width pixelsHigh:(unsigned)height options:(ICMCompressionSessionOptionsRef)options;

#endif

/*************************************************************** 
 * These functions are common to both the proxy and the worker *
 ***************************************************************/
- (id) init;
- (BOOL) compressFrame:(CVPixelBufferRef)frame timeStamp:(NSTimeInterval)timestamp duration:(NSTimeInterval)duration;
- (BOOL) flushFrames;
- (void) shutdownServer;
@end
