//
//  QTCubed.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/26.
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

#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "net_mc_cubed_QTCubed.h"
#import "QTCodec.h"

/*
 * Class:     net_mc_cubed_QTCubed
 * Method:    startEncodingServer
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_QTCubed_startEncodingServer
(JNIEnv * env, jclass classRef) {
#if __LP64__
	NSTask * task;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
			
	// Startup the 32-bit daemon process
	task = [[NSTask alloc] init]; 
    [task setLaunchPath:@"EncodingServer"];
	
	NSPipe *readPipe = [NSPipe pipe];
    NSFileHandle *readHandle = [readPipe fileHandleForReading];
	
    NSPipe *writePipe = [NSPipe pipe];
    NSFileHandle *writeHandle = [writePipe fileHandleForWriting];
	
    [task setStandardInput: writePipe];
    [task setStandardOutput: readPipe];
	
	[task launch];
	
	[readHandle availableData]; // Block until a message is emitted

	// Test the link or codec linkage if linked locally (32-bit)
	QTCodec * codec = [[QTCodec alloc] init];	
	NSLog(@"Got response from QTCodec: %@",[codec isAlive]);
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	

	return (jlong)task;
#else
	return 0;
#endif	
}

/*
 * Class:     net_mc_cubed_QTCubed
 * Method:    _shutdown
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_QTCubed__1shutdown
(JNIEnv * env, jclass classRef, jlong taskRef) {
#if __LP64__
	NSTask * task = (NSTask *) taskRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	// TODO: Kill the task
	[task release];

	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
#endif	
}