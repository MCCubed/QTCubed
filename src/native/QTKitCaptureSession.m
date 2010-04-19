//
//  QTKitCaptureSession.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
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

#import "QTKitCaptureSession.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _allocInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1allocInit
(JNIEnv * env, jclass classRef) {
	jlong sessionPointer;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	sessionPointer = (jlong)[[QTCaptureSession alloc]init];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return sessionPointer;
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _release
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1release
(JNIEnv * env, jclass classRef, jlong sessionPointer) {
	QTCaptureSession * captureSession = (QTCaptureSession *) sessionPointer;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	if ([captureSession isRunning]) {
		// Stop the capture session
		[captureSession performSelectorOnMainThread:@selector(stopRunning) withObject:nil waitUntilDone:YES];
	}

	[captureSession performSelectorOnMainThread:@selector(release) withObject:nil waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _addInput
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1addInput
(JNIEnv * env, jobject objectRef, jlong captureSessionRef, jlong captureInputRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;
	QTCaptureInput * captureInput = (QTCaptureInput *) captureInputRef;
	
	BOOL result;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	NSError * errorPtr;
	
	result = [captureSession addInput:captureInput error:&errorPtr];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	if (result == NO) {
		// TODO: Error handling and throw an exception using the errorPtr data
		return JNI_FALSE;
	} else {	
		return JNI_TRUE;
	}
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _addOutput
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1addOutput
(JNIEnv *env, jobject objectRef, jlong captureSessionRef, jlong captureOutputRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;
	QTCaptureOutput * captureOutput = (QTCaptureOutput *) captureOutputRef;
		
	BOOL result;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSError * errorPtr;
	
	result = [captureSession addOutput:captureOutput error:&errorPtr];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	

	if (result == NO) {
		// TODO: Error handling and throw an exception using the errorPtr data
		return JNI_FALSE;
	} else {	
		return JNI_TRUE;
	}
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _start
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1startRunning
(JNIEnv * env, jobject objectRef, jlong captureSessionRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;

	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureSession performSelectorOnMainThread:@selector(startRunning) withObject:nil waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _isRunning
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1isRunning
(JNIEnv *env, jobject objectRef, jlong captureSessionRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;	
	jboolean retval;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	BOOL running = [captureSession isRunning];
	if (running == YES) {
		retval = JNI_TRUE;
	} else {
		retval = JNI_FALSE;
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
	
	return retval;	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _stopRunning
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1stopRunning
(JNIEnv *env, jobject objectRef, jlong captureSessionRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureSession performSelectorOnMainThread:@selector(stopRunning) withObject:nil waitUntilDone:NO];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
}	

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _removeInput
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1removeInput
(JNIEnv *env, jobject objectRef, jlong captureSessionRef, jlong captureInputRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;
	QTCaptureInput * captureInput = (QTCaptureInput *) captureInputRef;
		
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureSession removeInput:captureInput];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureSession
 * Method:    _removeOutput
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureSession__1removeOutput
(JNIEnv *env, jobject objectRef, jlong captureSessionRef, jlong captureOutputRef) {
	QTCaptureSession * captureSession = (QTCaptureSession *) captureSessionRef;
	QTCaptureOutput * captureOutput = (QTCaptureOutput *) captureOutputRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureSession removeOutput:captureOutput];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}	

