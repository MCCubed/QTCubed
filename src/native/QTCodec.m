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

/*
 * Class:     net_mc_cubed_qtcubed_media_codec_QTCodec
 * Method:    _processIntoFrames
 * Signature: ([B[BII)[B
 */
JNIEXPORT jbyteArray JNICALL Java_net_mc_1cubed_qtcubed_media_codec_QTCodec__1processIntoFrames
(JNIEnv *env, jobject objectRef, jbyteArray headerBytes, jbyteArray javaDataBytes, jint dataOffset, jint dataLength) {
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	// Not auto-released, be careful with this!
	jbyte* dataBytes = malloc(dataLength);
	
	(*env)->GetByteArrayRegion(env,javaDataBytes,dataOffset,dataLength,dataBytes);
	
	// This will take care of our malloced databytes so they won't leak
	NSData * nsDataBytes = [NSData dataWithBytesNoCopy:dataBytes length:dataLength freeWhenDone:YES];
	
	NSError * error = nil;	
	QTMovie * movie = [QTMovie movieWithData:nsDataBytes error:&error];
	
	[movie gotoBeginning];
	
	for (;QTTimeCompare([movie duration],[movie currentTime]) != NSOrderedSame;[movie stepForward]) {
		NSImage * frame = [movie currentFrameImage];		
	}
	
	free(dataBytes);	
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	
}

@implementation QTCodec

@end
