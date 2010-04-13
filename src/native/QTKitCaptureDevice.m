//
//  QTKitCaptureDevice.m
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import "QTKitCaptureDevice.h"

NSString * deviceTypeToMediaType(jint deviceType) {
	switch (deviceType) {
		case 0: return QTMediaTypeVideo;
		case 1: return QTMediaTypeSound;
		case 2: return QTMediaTypeText;
		case 3: return QTMediaTypeBase;
		case 4: return QTMediaTypeMPEG;
		case 5: return QTMediaTypeMusic;
		case 6: return QTMediaTypeTimeCode;
		case 7: return QTMediaTypeSprite;
		case 8: return QTMediaTypeFlash;
		case 9: return QTMediaTypeMovie;
		case 10: return QTMediaTypeTween;
		case 11: return QTMediaType3D;
		case 12: return QTMediaTypeSkin;
		case 13: return QTMediaTypeQTVR;
		case 14: return QTMediaTypeHint;
		case 15: return QTMediaTypeStream;
		case 16: return QTMediaTypeMuxed;
		case 17: return QTMediaTypeSubtitle;
		case 18: return QTMediaTypeClosedCaption;
		default: return nil;			
	}
}

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

	NSString *  mediaType = deviceTypeToMediaType(deviceType);
	
	// Get the default capture device for the specified media type
	captureDevice = [QTCaptureDevice defaultInputDeviceWithMediaType:mediaType];
	
	// Above method is not a create or new, so we need to retain this object
	[captureDevice retain];
	
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
	
	// Number of devices to loop through
	int count = [deviceArray count];
	
	// Iterate through the capture devices, assigning their values to the elements in the array
	for (int devNum = 0; devNum < count; devNum++) {
		QTCaptureDevice * device = [deviceArray objectAtIndex:devNum];
		[device retain];
		longVals[devNum] = (jlong) device;
	}

	// Re-assign the values back to the array
	(*env)->SetLongArrayRegion(env,retval,0,count,longVals);
	
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

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _getFormatDescriptions
 * Signature: (J)[Lnet/mc_cubed/qtcubed/QTKitFormatDescription;
 */
JNIEXPORT jobjectArray JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1getFormatDescriptions
(JNIEnv *env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	jobjectArray retval;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSArray* formatDescriptions = [captureDevice formatDescriptions];
	
	NSUInteger objCount = [formatDescriptions count];

	// Get the format description class
	jclass qtKitfdclass = (*env)->FindClass(env,"net/mc_cubed/qtcubed/QTKitFormatDescription");

	// Find the default constructor
	jmethodID fdConstructor = (*env)->GetMethodID(env,qtKitfdclass,"<init>","()V");
	
	// Create a default instance to use while initializing the array
	jobject blankfdobject = (*env)->NewObject(env,qtKitfdclass,fdConstructor);

	// Create the initial array
	retval = (*env)->NewObjectArray(env,objCount,qtKitfdclass,blankfdobject);
	
	// Get the enum objects we need
	jclass mediaTypeEnumClass = (*env)->FindClass(env,"net/mc_cubed/qtcubed/QTKitMediaType");
	jfieldID videoFieldId = (*env)->GetStaticFieldID(env,mediaTypeEnumClass,"VIDEO","Lnet/mc_cubed/qtcubed/QTKitMediaType;");
	jfieldID soundFieldId = (*env)->GetStaticFieldID(env,mediaTypeEnumClass,"SOUND","Lnet/mc_cubed/qtcubed/QTKitMediaType;");
	jfieldID muxedFieldId = (*env)->GetStaticFieldID(env,mediaTypeEnumClass,"MUXED","Lnet/mc_cubed/qtcubed/QTKitMediaType;");
	jobject videoMediaType = (*env)->GetStaticObjectField(env,mediaTypeEnumClass,videoFieldId);
	jobject soundMediaType = (*env)->GetStaticObjectField(env,mediaTypeEnumClass,soundFieldId);
	jobject muxedMediaType = (*env)->GetStaticObjectField(env,mediaTypeEnumClass,muxedFieldId);		
	
	// Get the method signatures for setters we will use
	jmethodID setMediaTypeMethodId = (*env)->GetMethodID(env,qtKitfdclass,"setMediaType","(Lnet/mc_cubed/qtcubed/QTKitMediaType;)V");
	jmethodID setFormatTypeMethodId = (*env)->GetMethodID(env,qtKitfdclass,"setFormatType","(Ljava/lang/String;)V");
	jclass propertiesClass = (*env)->FindClass(env,"java/util/Properties");
	jmethodID setPropertyMethodId = (*env)->GetMethodID(env,propertiesClass,"setProperty","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
	jfieldID propertiesField = (*env)->GetFieldID(env,qtKitfdclass,"formatDescriptionAttributes","Ljava/util/Properties;");
														  
	// Loop through each element, creating new format descriptions for them
	for (NSUInteger fdnum = 0; fdnum < objCount; fdnum++) {
		QTFormatDescription* currentfd = [formatDescriptions objectAtIndex:fdnum];
		
		// Construct the format descriptor object
		jobject newfd = (*env)->NewObject(env,qtKitfdclass,fdConstructor);
		
		// Transfer the Media Type property
		NSString* mediaType = [currentfd mediaType];
		if ([mediaType compare: QTMediaTypeVideo] == 0) {			
			(*env)->CallVoidMethod(env,newfd,setMediaTypeMethodId,videoMediaType);
		} else if ([mediaType compare: QTMediaTypeSound] == 0) {
			(*env)->CallVoidMethod(env,newfd,setMediaTypeMethodId,soundMediaType);
		} else if ([mediaType compare: QTMediaTypeMuxed] == 0) {
			(*env)->CallVoidMethod(env,newfd,setMediaTypeMethodId,muxedMediaType);
		} else {
				// TODO: throw an exception, unsupported type
		}

		// Transfer the formatType
		jsize buflength = 4;
		unichar buffer[buflength];
		UInt32 formatTypeInt = [currentfd formatType];
		char* formatType = &formatTypeInt;
		for (int i = 0; i < buflength; i++)
			buffer[i] = formatType[i];
		jstring formatTypeString = (*env)->NewString(env, (jchar *)buffer, buflength);
		(*env)->CallVoidMethod(env,newfd,setFormatTypeMethodId,formatTypeString);
		

		// Transfer attributes
		NSArray * keys;
		int count;
		keys = [[currentfd formatDescriptionAttributes] allKeys];
		count = [keys count];
		jobject newfdprops = (*env)->GetObjectField(env,newfd,propertiesField);
		for (int i = 0; i < count; i++) {
			id key = [keys objectAtIndex:i], object = [[currentfd formatDescriptionAttributes] objectForKey:key];
			// Check to make sure both the key and attribute are NSStrings before transferring them over
			if ([key respondsToSelector:@selector(getCharacters)] && [object respondsToSelector:@selector(getCharacters)]) {
			
				// Change the NSStrings into jstrings
				jstring propKey,propVal;
				jsize keybuflength = [key length];
				unichar keybuffer[keybuflength];
				[key getCharacters:keybuffer];	
				propKey = (*env)->NewString(env, (jchar *)keybuffer, keybuflength);

				jsize objectbuflength = [object length];
				unichar objectbuffer[objectbuflength];
				[object getCharacters:objectbuffer];	
				propVal = (*env)->NewString(env, (jchar *)objectbuffer, objectbuflength);
			
				// Add them to the properties by using the set method
				(*env)->CallVoidMethod(env,newfdprops,setPropertyMethodId,propKey,propVal);
			}
		}
		
		// Set it in the array
		(*env)->SetObjectArrayElement(env,retval,fdnum,newfd);
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return retval;
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _uniqueId
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1uniqueId
(JNIEnv *env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	jstring javaName;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSString * nsstringName = [captureDevice uniqueID];
	
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

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _modelUniqueId
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1modelUniqueId
(JNIEnv *env, jobject objectRef, jlong captureDeviceRef) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	jstring javaName;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSString * nsstringName = [captureDevice modelUniqueID];
	
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

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _hasMediaType
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1hasMediaType
(JNIEnv *env, jobject objectRef, jlong captureDeviceRef, jint mediaTypeOrdinal) {
	QTCaptureDevice * captureDevice = (QTCaptureDevice *) captureDeviceRef;
	jboolean retval;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	NSString * mediaType = deviceTypeToMediaType(mediaTypeOrdinal);
	
	BOOL result = [captureDevice hasMediaType:mediaType];
	if (result == YES) {
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
 * Method:    _deviceAttributes
 * Signature: (J)Ljava/util/Properties;
 */
JNIEXPORT jobject JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1deviceAttributes
(JNIEnv *, jobject, jlong);

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDevice
 * Method:    _deviceWithUniqueId
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDevice__1deviceWithUniqueId
(JNIEnv *env, jclass classRef, jstring deviceId) {
	QTCaptureDevice * captureDevice;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	const jchar *chars = (*env)->GetStringChars(env, deviceId, NULL);
	NSString *nsDeviceId = [NSString stringWithCharacters:(UniChar *)chars
												   length:(*env)->GetStringLength(env, deviceId)];
	(*env)->ReleaseStringChars(env, deviceId, chars);
	
	captureDevice = [QTCaptureDevice deviceWithUniqueID:nsDeviceId];
	// Not a "create" or "new" method, so this is autoreleased, which we don't want
	[captureDevice retain];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return (jlong) captureDevice;
	
}


