//
//  QTKitCaptureDeviceInput.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTKitCaptureDeviceInput.h"

/*
  * Class:     net_mc_cubed_qtcubed_QTKitCaptureDeviceInput
  * Method:    _allocInitWithCaptureDevice
  * Signature: (J)J
  */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDeviceInput__1allocInitWithCaptureDevice
(JNIEnv * env, jclass classRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	
	jlong captureDeviceInputRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	captureDeviceInputRef = (jlong) [[QTCaptureDeviceInput alloc] initWithDevice: captureDevice];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);

	return captureDeviceInputRef;
}
