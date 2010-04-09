//
//  QTKitCaptureDevice.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTKitCaptureDevice.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _defaultInputDevice
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1defaultInputDevice
(JNIEnv * env, jclass classRef, jint deviceType) {
	QTCaptureDevice * captureDevice;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	NSString *  mediaType = nil;

	switch (deviceType) {
		case 0: mediaType = QTMediaTypeVideo; break;
		case 1: mediaType = QTMediaTypeSound; break;
		case 2: mediaType = QTMediaTypeText; break;
		case 3: mediaType = QTMediaTypeBase; break;
		case 4: mediaType = QTMediaTypeMPEG; break;
		case 5: mediaType = QTMediaTypeMusic; break;
		case 6: mediaType = QTMediaTypeTimeCode; break;
		case 7: mediaType = QTMediaTypeSprite; break;
		case 8: mediaType = QTMediaTypeFlash; break;
		case 9: mediaType = QTMediaTypeMovie; break;
		case 10: mediaType = QTMediaTypeTween; break;
		case 11: mediaType = QTMediaType3D; break;
		case 12: mediaType = QTMediaTypeSkin; break;
		case 13: mediaType = QTMediaTypeQTVR; break;
		case 14: mediaType = QTMediaTypeHint; break;
		case 15: mediaType = QTMediaTypeStream; break;
		case 16: mediaType = QTMediaTypeMuxed; break;
		case 17: mediaType = QTMediaTypeSubtitle; break;
		case 18: mediaType = QTMediaTypeClosedCaption; break;
			
	}
	
	captureDevice = [QTCaptureDevice defaultInputDeviceWithMediaType:mediaType];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return (jlong) captureDevice;
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _release
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1release
(JNIEnv * env, jobject objectRef, jlong captureDeviceRef) {
	
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureDevice release];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _inputDevices
 * Signature: ()[J
 */
JNIEXPORT jlongArray JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1inputDevices
(JNIEnv * env, jclass classRef) {
	jlongArray retval;
	
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Grab the list of capture devices
	NSArray * deviceArray = [QTCaptureDevice inputDevices];
	
	// Create a java array to hold them
	retval = (*env)->NewLongArray(env,[deviceArray count]);
	
	// Grab a pointer to the values we can use in C
	jlong * longVals = (*env)->GetLongArrayElements(env,retval,JNI_FALSE);
	
	// Iterate through the capture devices, assigning their values to the elements in the array
	for (int devNum = 0; devNum < [deviceArray count]; devNum++) {
		longVals[devNum] = (jlong) [deviceArray objectAtIndex:devNum];
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return retval;
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _open
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1open
(JNIEnv * env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSError * errorPtr;
	BOOL success = [captureDevice open: &errorPtr];
	
	if (success == NO) {
		// TODO: Handle errors appropriately by throwing an exception
		
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _isOpen
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1isOpen
(JNIEnv * env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	
	jboolean retval;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
		
	if ([captureDevice isOpen] == YES) {
		retval = JNI_TRUE;
	} else {
		retval = JNI_FALSE;
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return retval;
}	

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1close
(JNIEnv * env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	[captureDevice close];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}	

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _localizedDisplayName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1localizedDisplayName
(JNIEnv * env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	jstring javaName;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSString * nsstringName = [captureDevice localizedDisplayName];
	
	// Note that length returns the number of UTF-16 characters,
	// which is not necessarily the number of printed/composed characters
	jsize buflength = [nsstringName length];
	unichar buffer[buflength];
	[nsstringName getCharacters:buffer];	
	javaName = (*env)->NewString(env, (jchar *)buffer, buflength);
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return javaName;
}	

