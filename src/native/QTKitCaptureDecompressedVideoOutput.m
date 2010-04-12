//
//  QTKitCaptureDecompressedVideoOutput.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTKitCaptureDecompressedVideoOutput.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _allocInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1allocInit
(JNIEnv * env, jobject objectRef) {
	jlong ref;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	NSLog(@"Allocing video output and delegates");
	
	QTCaptureDecompressedVideoOutput * videoOutput = [[QTCaptureDecompressedVideoOutput alloc] init];
	
	QTKitCaptureDecompressedVideoOutput * delegate = [[QTKitCaptureDecompressedVideoOutput alloc] initWithEnv:env javaObject:objectRef];
		
	[videoOutput setDelegate:delegate];
	
	[videoOutput setAutomaticallyDropsLateVideoFrames:YES];

	ref = (jlong) videoOutput;
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return ref;
	
}


@implementation QTKitCaptureDecompressedVideoOutput

- (QTKitCaptureDecompressedVideoOutput *)initWithEnv:(JNIEnv *) env javaObject:(jobject) javaObjectRef {
	[super init];
	// Save a reference to the VM
	(*env)->GetJavaVM(env,&g_vm);
	// Create a global reference to this object so we can access it later
	objectRef = (*env)->NewGlobalRef(env,javaObjectRef);
	
	return self;
}

- (void)captureOutput:(QTCaptureOutput *)captureOutput didOutputVideoFrame:(CVImageBufferRef)videoFrame withSampleBuffer:(QTSampleBuffer *)sampleBuffer fromConnection:(QTCaptureConnection *)connection {
	void * rawData = [sampleBuffer bytesForAllSamples];
	int length = [sampleBuffer lengthForAllSamples];
			
	// Move into Java to deliver the data
	JNIEnv *env;
	(*g_vm)->AttachCurrentThread (g_vm, (void **) &env, NULL);

	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Create an appropriately sized byte array to hold the data
	jbyteArray frameData = (*env)->NewByteArray(env,length);
	// Copy the raw data into the byteArray
	(*env)->SetByteArrayRegion(env,frameData,0,length,rawData);
	
	// Get the class reference for our object
	jclass classRef = (*env)->GetObjectClass(env,objectRef);
	// Get the pushFrame methodId
	jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([B)V");
	// Call pushFrame with the byte array
	(*env)->CallVoidMethod(env,objectRef,methodId,frameData);

	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	(*g_vm)->DetachCurrentThread (g_vm);
}

- (void) dealloc
{
	// Dealloc the global Java object reference, and nil both references to the VM
	JNIEnv *env;
	(*g_vm)->AttachCurrentThread (g_vm, (void **) &env, NULL);
	(*env)->DeleteGlobalRef(env,objectRef);
	objectRef = nil;
	(*g_vm)->DetachCurrentThread (g_vm);
	g_vm = nil;
	
	[super dealloc];
}

@end
