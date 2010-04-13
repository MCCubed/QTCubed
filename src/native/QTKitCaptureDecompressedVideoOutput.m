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

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _setFrameRate
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1setFrameRate
(JNIEnv *env, jobject objectRef, jlong videoOutputRef, jfloat newFrameRate) {
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
		
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
		
	NSTimeInterval frameInterval = 1.0f / newFrameRate;
		 
	[videoOutput setMinimumVideoFrameInterval:frameInterval];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _getFrameRate
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1getFrameRate
(JNIEnv *env, jobject objectRef, jlong videoOutputRef) {
	jfloat frameRate;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
		
	NSTimeInterval frameInterval = [videoOutput minimumVideoFrameInterval];
	
	frameRate = 1 / frameInterval;
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return frameRate;	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _setSize
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1setSize
(JNIEnv *env, jobject objectRef, jlong videoOutputRef, jint newWidth, jint newHeight) {
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;

	NSDictionary * pixelBufferAttributes = [videoOutput pixelBufferAttributes];
	
	NSNumber * width  = [NSNumber numberWithInt:newWidth];
	NSString * widthKey = (NSString*)kCVPixelBufferWidthKey;
	NSNumber * height = [NSNumber numberWithInt:newHeight];
	NSString * heightKey = (NSString*)kCVPixelBufferHeightKey;	
	
	[pixelBufferAttributes setValue:width  forKey:widthKey];
	[pixelBufferAttributes setValue:height forKey:heightKey];
	
	[videoOutput setPixelBufferAttributes:pixelBufferAttributes];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _getWidth
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1getWidth
(JNIEnv *, jobject, jlong);

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _getHeight
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1getHeight
(JNIEnv *, jobject, jlong);


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
