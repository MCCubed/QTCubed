//
//  EncodingServerMain.m
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

#import "EncodingServerMain.h"
#import "QTCodec.h"

int main( int argc, const char* argv[] ) {
	NSLog(@"Starting Encoding Server");
	// Be responsible about memory management
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];

	// Allocate our server object
	EncodingServerMain * serverMain = [[[EncodingServerMain alloc] init] autorelease];
	// Run it and collect a return value
	int retval = [serverMain run];
	// Drain the top level auto-release pool (including our server!)
	[pool drain];

	NSLog(@"Encoding Server Exiting");
	
	// Return a value to the OS
	return retval;
}

@implementation EncodingServerMain

/**
 * Publish the distributed objects nessisary to encode and decode video frames using Quicktime.  These are not available in 64-bit, and so MUST be accessed via this IPC channel.
 */

- (id) init {
	[super init];

	encodeConnection = [[NSConnection alloc] init];
	[encodeConnection setRootObject:[[QTCodec alloc] init]];
	[encodeConnection registerName:@"net.mc_cubed.qtcubed.QTCubedEncodingServer"];

	return self;
}

- (int) run {
	
	printf("Starting main runloop");

	CFRunLoopRun();	
	return 0;
}
@end
