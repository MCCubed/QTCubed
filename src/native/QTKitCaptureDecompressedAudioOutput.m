//
//  QTKitCaptureDecompressedAudioOutput.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTKitCaptureDecompressedAudioOutput.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedAudioOutput
 * Method:    _allocInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedAudioOutput__1allocInit
(JNIEnv * env, jclass classRef) {
	jlong ref;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	ref = (jlong) [[QTCaptureDecompressedAudioOutput alloc] init];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return ref;
	
}