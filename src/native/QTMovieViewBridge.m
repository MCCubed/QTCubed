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
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

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

	NSLog(@"Allocated new view instance: %x",viewPointer);
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return viewPointer;
}
/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _getFillColor
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jint JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1getFillColor
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	jint retval;
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;

	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
		
	NSColor * nsColor = [view fillColor];
	
	int red = [nsColor redComponent] * 255,
	green = [nsColor greenComponent] * 255,
	blue = [nsColor blueComponent] * 255;
	
	retval = red << 16 & green << 8 & blue;
		
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);

	return retval;
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _setFillColor
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1setFillColor
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef, jint colorjint) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	CGFloat red = ((colorjint * 0xff0000) >> 16) / 255.0,
	green = ((colorjint & 0xff00) >> 8) / 255.0,
	blue = (colorjint & 0xff) / 255.0;
	
	NSColor * nsColor = [NSColor colorWithCalibratedRed:red green:green blue:blue alpha:0.0f];
						 
	[view performSelectorOnMainThread:@selector(setFillColor) withObject:nsColor waitUntilDone:NO];

	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _gotoBeginning
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1gotoBeginning
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Do on the main thread
	[view performSelectorOnMainThread:@selector(gotoBeginning) withObject:view waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _gotoEnd
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1gotoEnd
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Do on the main thread
	[view performSelectorOnMainThread:@selector(gotoEnd) withObject:view waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _gotoPosterFrame
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1gotoPosterFrame
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Do on the main thread
	[view performSelectorOnMainThread:@selector(gotoPosterFrame) withObject:view waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _stepForward
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1stepForward
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Do on the main thread
	[view performSelectorOnMainThread:@selector(stepForward) withObject:view waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitMovieView
 * Method:    _stepBackward
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitMovieView__1stepBackward
(JNIEnv * env, jobject objectRef, jlong qtMovieViewRef) {
	QTMovieViewBridge* view = (QTMovieViewBridge*) qtMovieViewRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
		
	// Do on the main thread
	[view performSelectorOnMainThread:@selector(stepBackward) withObject:view waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

jboolean booleanFromObject(JNIEnv * env,jobject booleanObject) {
	jclass booleanClass = (*env)->GetObjectClass(env,booleanObject);
	jmethodID booleanValueMethod = (*env)->GetMethodID(env,booleanClass,"booleanValue","()Z");
	if (booleanValueMethod) {
		return (*env)->CallBooleanMethod(env,booleanObject,booleanValueMethod);
	} else {
		return JNI_FALSE;
	}	
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
		case net_mc_cubed_qtcubed_QTKitMovieView_SET_PRESERVES_ASPECT:
		{
			// get the boolean value from the Boolean object
			jboolean jboolValue = booleanFromObject(env,message);
			BOOL preserveAspect;
			if (jboolValue == JNI_TRUE) {
				preserveAspect = YES;
			} else {
				preserveAspect = NO;
			}
			[self setPreservesAspectRatio:preserveAspect];
		}
		case net_mc_cubed_qtcubed_QTKitMovieView_SET_CONTROLLER_VISIBILITY:
		{
			// get the boolean value from the Boolean object
			jboolean jboolValue = booleanFromObject(env,message);
			BOOL controllerVisible;
			if (jboolValue == JNI_TRUE) {
				controllerVisible = YES;
			} else {
				controllerVisible = NO;
			}
			[self setControllerVisible:controllerVisible];
		}
			// more cases...
	}
}

@end

