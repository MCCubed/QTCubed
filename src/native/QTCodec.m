//
//  QTCodec.m
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

#import "QTCodec.h"

#if !__LP64__
/***********************************************
 * Required C Callback functions for Quicktime *
 ***********************************************/
static OSStatus _CompressionFrameOutputCallback(void* qtCodecRefCon, ICMCompressionSessionRef session, OSStatus error, ICMEncodedFrameRef frame, void* reserved)
{
	if (error == noErr)
		[(QTCodec *)qtCodecRefCon doneCompressingFrame: frame];
	
	return error;
}

static OSStatus _DecompressionFrameOutputCallback(void *qtCodecRefCon, OSStatus result, ICMDecompressionTrackingFlags decompressionTrackingFlags, CVPixelBufferRef pixelBuffer, TimeValue64 displayTime, TimeValue64 displayDuration, ICMValidTimeFlags validTimeFlags, void *reserved, void *sourceFrameRefCon) {
	if (result == noErr)
		[(QTCodec *)qtCodecRefCon doneDecompressingFrame:pixelBuffer];
	
	return result;
}
#endif

@implementation QTCodec

#if !__LP64__
/****************************
 * Begin actual worker code *
 ****************************/
- (id) init {
	[super init];
	
	EnterMovies();
	// TODO: Initialize the codec
	
	return self;
}

- (NSString *) isAlive {
	return @"Alive and well!";
}

- (void) doneCompressingFrame:(ICMEncodedFrameRef)frame {
	NSLog(@"Got compressed frame");
	// TODO: grab compressed frame and hand it over to java
}

- (void) doneDecompressingFrame:(CVPixelBufferRef)buffer {
	NSLog(@"Got decompressed frame");
	// TODO: grab decompressed frame and hand it over to java
}

+ (ICMCompressionSessionOptionsRef) defaultOptions
{
	ICMCompressionSessionOptionsRef options;
	OSStatus						theError;
	
	// Create the default compression options
	theError = ICMCompressionSessionOptionsCreate(kCFAllocatorDefault, &options);
	if (theError) {
		NSLog(@"ICMCompressionSessionOptionsCreate failed with error %i",theError);
		return NULL;
	}
	
	return (ICMCompressionSessionOptionsRef)[(id)options autorelease];
}
- (BOOL) startCompressionSession:(CodecType)codec pixelsWide:(unsigned)width pixelsHigh:(unsigned)height options:(ICMCompressionSessionOptionsRef)options
{
	ICMEncodedFrameOutputRecord record = {_CompressionFrameOutputCallback,self,NULL};
	OSStatus					theError;
	
	// Check parameters
	if ((codec == 0) || (width == 0) || (height == 0) || (options == NULL)) {
		return NO;
	}
	
	theError = ICMCompressionSessionCreate(kCFAllocatorDefault, width, height, codec, kTimeScale, options, NULL, &record, &compressionSession);
	
	if (theError) {
		NSLog(@"ICMCompressionSessionCreate() failed with error %i",theError);
		return NO;
	}
	
	return YES;
}

- (BOOL) startDecompressingSession:(ImageDescriptionHandle)imageDescription
{
	ICMDecompressionTrackingCallbackRecord  record = {_DecompressionFrameOutputCallback,self};
	OSStatus								theError;

	theError = ICMDecompressionSessionCreate(NULL,imageDescription,NULL,NULL,&record,&decompressionSession);
	
	if (theError) {
		NSLog(@"ICMDecompressionSessionCreate() failed with error %i",theError);
		return NO;
	}
	
	return YES;	
}

- (BOOL) decompressFrame:(void *)data length:(int)length timeStamp:(NSTimeInterval)timestamp duration:(NSTimeInterval)duration
{
	OSStatus theError;
	
	ICMFrameTimeRecord timeRecord;
	UInt64 time64 = timestamp * kTimeScale;
	timeRecord.value.lo = (int) time64 & 0xFFFFFFFF;
	timeRecord.value.hi = time64 >>32;
	timeRecord.scale = kTimeScale;
	timeRecord.duration = (int)duration * kTimeScale;
	timeRecord.recordSize = sizeof(ICMFrameTimeRecord);
	timeRecord.frameNumber = 0;
	timeRecord.flags = icmFrameTimeDecodeImmediately;
	
	
	theError = ICMDecompressionSessionDecodeFrame(decompressionSession,
												  data,length,
												  NULL,&timeRecord,NULL);	

	if (theError)
		NSLog(@"ICMDecompressionSessionDecodeFrame() failed with error %i",theError);
	
	return (theError == noErr ? YES : NO);
}

- (BOOL) decompressFrame:(void *)data length:(int)length
{
	OSStatus theError;
	
	ICMFrameTimeRecord timeRecord;
	timeRecord.scale = kTimeScale;
	timeRecord.recordSize = sizeof(ICMFrameTimeRecord);
	timeRecord.frameNumber = 0;
	timeRecord.flags = icmFrameTimeDecodeImmediately;	
	
	theError = ICMDecompressionSessionDecodeFrame(decompressionSession,
												  data,length,
												  NULL,&timeRecord,NULL);	
	
	if (theError)
		NSLog(@"ICMDecompressionSessionDecodeFrame() failed with error %i",theError);
	
	return (theError == noErr ? YES : NO);
}

- (BOOL) compressFrame:(CVPixelBufferRef)frame timeStamp:(NSTimeInterval)timestamp duration:(NSTimeInterval)duration
{
	OSStatus theError;
	
	theError = ICMCompressionSessionEncodeFrame(compressionSession,
												frame, (timestamp >= 0.0 ? (SInt64)(timestamp * kTimeScale) : 0),
												(duration >= 0.0 ? (SInt64)(duration * kTimeScale) : 0),
												((timestamp >= 0.0 ? kICMValidTime_DisplayTimeStampIsValid : 0) |
												 (duration >= 0.0 ? kICMValidTime_DisplayDurationIsValid : 0)),
												NULL,NULL,NULL);
	if (theError)
		NSLog(@"ICMCompressionSessionEncodeFrame() failed with error %i",theError);
	
	return (theError == noErr ? YES : NO);
}

- (BOOL) flushFrames
{
	OSStatus theError;
	
	// Flush pending frames in compression session
	theError = ICMCompressionSessionCompleteFrames(compressionSession,
												   true,0,0);
	if (theError)
		NSLog(@"ICMCompressionSessionCompleteFrames() failed with error %i",theError);
	
	return (theError == noErr ? YES: NO);
}

- (void) shutdownServer
{
	CFRunLoopStop(CFRunLoopGetMain());
}

#else
/*************************
 * Begin Proxy Only code *
 *************************/

/**
 * Construct the other end of the proxy and link to it
 */
- (id) init {
	[super init];
	
	// Startup the 32-bit daemon process
	task = [[NSTask alloc] init];

    // TODO: This path needs to be determined in a portable way, probably with some assistance from Java to help find the executable.
	[task setLaunchPath:@"EncodingServer"];
	
	// Construct a pipe to use when interacting with the task
	NSPipe *readPipe = [NSPipe pipe];
    NSFileHandle *readHandle = [readPipe fileHandleForReading];
	
	// Set the standard output so we can read it
    [task setStandardOutput: readPipe];
	
	@try {
		// Launch the task
		[task launch];
		
		// Wait for it to spit out some output.  Hopefully, this is the IPC Registered Name
		NSData * ipcRegData = [readHandle availableData];		
		NSString * ipcRegName = [[[NSString alloc] initWithData:ipcRegData encoding:NSASCIIStringEncoding] autorelease];
		
		// Create a proxy to the process
		proxy = [[NSConnection
				  rootProxyForConnectionWithRegisteredName:ipcRegName
				  host:nil] retain];
		[proxy setProtocolForProxy:@protocol(QTCodecProtocol)];
		
		// Test the link or codec linkage if linked locally (32-bit)
		NSString * response = [proxy isAlive];
		NSLog(@"Got response: %@",response);
		
		if (response == nil) {
			[[NSException exceptionWithName:@"Proxy Exception" reason:@"The proxy did not respond" userInfo:nil] raise];
		}
	}
	
	@catch (NSException * ex) {
		NSLog(@"Unable to launch helper, path is: %@, error is: %@",[task launchPath],[ex reason]);
		[ex raise];
	}
		
	return self;
}

- (NSString *) isAlive {
	return [proxy isAlive];
}

- (void) dealloc {
	if (task != nil && [task isRunning] == YES) {
		[task terminate];
		[task release];
		task = nil;
	}
	
	[proxy release];
	proxy = nil;
	[super dealloc];
}

- (BOOL) compressFrame:(CVPixelBufferRef)frame timeStamp:(NSTimeInterval)timestamp duration:(NSTimeInterval)duration {
	return [proxy compressFrame:frame timeStamp:timestamp duration:duration];
}

- (BOOL) flushFrames {
	return [proxy flushFrames];
}

- (void) shutdownServer {
	[proxy shutdownServer];
}

#endif


@end
