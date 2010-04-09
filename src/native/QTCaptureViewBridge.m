//
//  QTCaptureViewBridge.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTCaptureViewBridge.h"


/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureView
 * Method:    createNSViewLong
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureView_createNSViewLong
(JNIEnv *env, jobject objectRef) {
	jlong viewPointer;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	viewPointer = (jlong)[[QTCaptureViewBridge alloc]init];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return viewPointer;
	
}

@implementation QTCaptureViewBridge
-(id)init
{
    return [super init];
}


- (void) awtMessage:(jint)messageID message:(jobject)message env:(JNIEnv *)env {
	switch (messageID) {
		case net_mc_cubed_qtcubed_QTKitCaptureView_SET_CAPTURE_SESSION:
		{
			jclass cls = (*env)->GetObjectClass(env,message);
			jmethodID getLongMethod = (*env)->GetMethodID(env,cls,"longValue","()J");
			jlong captureSessionRef = (*env)->CallLongMethod(env,message,getLongMethod);
			[self setCaptureSession:(QTCaptureSession *) captureSessionRef];
			break;			
		}
			// more cases...
	}
}

@end
