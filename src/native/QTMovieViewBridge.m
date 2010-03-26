//
//  QTMovieViewBridge.m
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
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

#include "QTMovieViewBridge.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTMovieView
 * Method:    createNSViewLong
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView_createNSViewLong
(JNIEnv *env, jobject objectRef) {
	jlong viewPointer;

	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	viewPointer = (jlong)[[QTMovieViewBridge alloc]init];

	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return viewPointer;
}

@implementation QTMovieViewBridge

-(id)init
{
    return [super init];
}


- (void) awtMessage:(jint)messageID message:(jobject)message env:(JNIEnv *)env {
	switch (messageID) {
		case net_mc_cubed_qtcubed_QTKitMovieView_SET_MOVIE:
		{
			jclass cls = (*env)->GetObjectClass(env,message);
			jmethodID getLongMethod = (*env)->GetMethodID(env,cls,"longValue","()J");
			jlong movieRef = (*env)->CallLongMethod(env,message,getLongMethod);
			[self setMovie:(QTMovie *)movieRef];
			break;			
		}
		case net_mc_cubed_qtcubed_QTKitMovieView_PLAY_MOVIE:
		{
			[self play:self];
			break;			
		}
		case net_mc_cubed_qtcubed_QTKitMovieView_PAUSE_MOVIE:
		{
			[self pause:self];
			break;
		}
			// more cases...
	}
}

@end

