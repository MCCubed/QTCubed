//
//  QTKitCaptureDecompressedVideoOutput.m
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

#import "QTKitCaptureDecompressedVideoOutput.h"

void setPixelFormat(QTCaptureDecompressedVideoOutput * videoOutput, long nativeFormatNumber);

jobject pixelFormatToJava(JNIEnv * env, int format) {
	jclass pixelFormatEnumClass  = (*env)->FindClass(env,"net/mc_cubed/qtcubed/QTKitPixelFormat");
	jmethodID valuesMethod       = (*env)->GetStaticMethodID(env,pixelFormatEnumClass,"values","()[Lnet/mc_cubed/qtcubed/QTKitPixelFormat;");
	jmethodID nativeValueMethod  = (*env)->GetMethodID(env,pixelFormatEnumClass,"getNativeValue","()I");
	jobjectArray enumValuesArray = (*env)->CallStaticObjectMethod(env,pixelFormatEnumClass,valuesMethod);
	
	int arrayLength = (*env)->GetArrayLength(env,enumValuesArray);
	for (int i = 0; i < arrayLength; i++) {
		jobject enumValue = (*env)->GetObjectArrayElement(env,enumValuesArray,i);
		jint nativeValue = (*env)->CallIntMethod(env,enumValue,nativeValueMethod);
		if (format == nativeValue) {
			return enumValue;
		}
	}

	return nil;
}

int javaToPixelFormat(JNIEnv * env, jobject pixelFormat) {
	jclass pixelFormatEnumClass  = (*env)->FindClass(env,"net/mc_cubed/qtcubed/QTKitPixelFormat");
	jmethodID nativeValueMethod  = (*env)->GetMethodID(env,pixelFormatEnumClass,"getNativeValue","()I");
	jint nativeValue = (*env)->CallIntMethod(env,pixelFormat,nativeValueMethod);
	return nativeValue;
}
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
	
	// Alloc the delegate which will receive the video data
	QTKitCaptureDecompressedVideoOutput * delegate = [[QTKitCaptureDecompressedVideoOutput alloc] initWithEnv:env javaObject:objectRef];
	
	// Connect the video delegate to the output object
	[videoOutput setDelegate:delegate];
	
	// Java could potentially take some time to process the video data, so let's make sure we cover all bases
	[videoOutput setAutomaticallyDropsLateVideoFrames:YES];
	
	// Let's set a default video format for now
//	setPixelFormat(videoOutput, (long) kCVPixelFormatType_32BGRA);
	
	// Return a reference to the decompressed video output object
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
	if (!pixelBufferAttributes) {
		pixelBufferAttributes = [NSDictionary dictionaryWithObjectsAndKeys: 
									[NSNumber numberWithInt:newWidth],(id)kCVPixelBufferWidthKey,
									[NSNumber numberWithInt:newHeight],(id)kCVPixelBufferHeightKey,
									nil,nil];
	} else {	
		pixelBufferAttributes = [NSMutableDictionary dictionaryWithDictionary:pixelBufferAttributes];
		[pixelBufferAttributes setValue:[NSNumber numberWithInt:newWidth]  forKey:(id)kCVPixelBufferWidthKey];
		[pixelBufferAttributes setValue:[NSNumber numberWithInt:newHeight] forKey:(id)kCVPixelBufferHeightKey];
	}
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
(JNIEnv *env, jobject objectRef, jlong videoOutputRef) {
	jint jwidth;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
	
	NSDictionary * pixelBufferAttributes = [videoOutput pixelBufferAttributes];
	
	NSNumber * width = [pixelBufferAttributes valueForKey:(id)kCVPixelBufferWidthKey];

	jwidth = [width intValue];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
	
	return jwidth;
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _getHeight
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1getHeight
(JNIEnv *env, jobject objectRef, jlong videoOutputRef) {
	jint jheight;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
		
	NSDictionary * pixelBufferAttributes = [videoOutput pixelBufferAttributes];
	
	NSNumber * height = [pixelBufferAttributes valueForKey:(id)kCVPixelBufferHeightKey];
	
	jheight = [height intValue];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
	
	return jheight;	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _getPixelFormat
 * Signature: (J)Lnet/mc_cubed/qtcubed/QTKitPixelFormat;
 */
JNIEXPORT jobject JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1getPixelFormat
(JNIEnv *env, jobject objectRef, jlong videoOutputRef) {
	jobject jpixelFormat;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
		
	NSDictionary * pixelBufferAttributes = [videoOutput pixelBufferAttributes];
	
	NSNumber * format = [pixelBufferAttributes valueForKey:(id)kCVPixelBufferPixelFormatTypeKey];
	
	int formatValue = [format intValue];

	jpixelFormat = pixelFormatToJava(env,formatValue);
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);	
	
	return jpixelFormat;
}

void setPixelFormat(QTCaptureDecompressedVideoOutput * videoOutput, long nativeFormatNumber) {

	NSDictionary * pixelBufferAttributes = [videoOutput pixelBufferAttributes];
	
	NSNumber * formatNSNumber = [NSNumber numberWithLong:nativeFormatNumber];
	
	if (!pixelBufferAttributes) {
		pixelBufferAttributes = [NSDictionary dictionaryWithObjectsAndKeys: 
								 formatNSNumber,(id)kCVPixelBufferPixelFormatTypeKey,
								 nil,nil];
	} else {
		pixelBufferAttributes = [NSMutableDictionary dictionaryWithDictionary:pixelBufferAttributes];
		[pixelBufferAttributes setValue:formatNSNumber forKey:(id)kCVPixelBufferPixelFormatTypeKey];
	}
	
	[videoOutput setPixelBufferAttributes:pixelBufferAttributes];

}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput
 * Method:    _setPixelFormat
 * Signature: (JJ)Lnet/mc_cubed/qtcubed/QTKitPixelFormat;
 */
JNIEXPORT jobject JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedVideoOutput__1setPixelFormat
(JNIEnv *env, jobject objectRef, jlong videoOutputRef, jlong nativeFormatNumber) {
	JNF_COCOA_ENTER(env);
	
	QTCaptureDecompressedVideoOutput * videoOutput = (QTCaptureDecompressedVideoOutput *)videoOutputRef;
	
	setPixelFormat(videoOutput, nativeFormatNumber);
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
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
	// Move into Java to deliver the data
	JNIEnv *env;
	(*g_vm)->AttachCurrentThread (g_vm, (void **) &env, NULL);

	// Set up autorelease and exception handling
//	JNF_COCOA_ENTER(env);

	void * rawData = [sampleBuffer bytesForAllSamples];
	int length = [sampleBuffer lengthForAllSamples];
	QTFormatDescription * formatDescription = [sampleBuffer formatDescription];
	QTTime duration = [sampleBuffer duration];
	
	float frameDuration = duration.timeValue/duration.timeScale;
	float fps = 1/frameDuration;
	
	jint format = [formatDescription formatType];
	NSValue * pixelSize = [formatDescription attributeForKey:QTFormatDescriptionVideoEncodedPixelsSizeAttribute];
	NSSize size = [pixelSize sizeValue];
	jint width = size.width;
	jint height = size.height;
	NSLog(@"Outputting frame sized %d x %d of length %d with format: %#x",width,height,length,format);
	
	switch (format) {
			// 8 bit codecs
		case kCVPixelFormatType_1Monochrome:
		case kCVPixelFormatType_2Indexed:
		case kCVPixelFormatType_4Indexed:
		case kCVPixelFormatType_8Indexed:
		case kCVPixelFormatType_1IndexedGray_WhiteIsZero:
		case kCVPixelFormatType_2IndexedGray_WhiteIsZero:
		case kCVPixelFormatType_4IndexedGray_WhiteIsZero:
		case kCVPixelFormatType_8IndexedGray_WhiteIsZero:
		case kCVPixelFormatType_422YpCbCr8:
		case kCVPixelFormatType_4444YpCbCrA8:
		case kCVPixelFormatType_4444YpCbCrA8R:
		case kCVPixelFormatType_444YpCbCr8:
		case kCVPixelFormatType_420YpCbCr8Planar:
		case kCVPixelFormatType_422YpCbCr_4A_8BiPlanar:
		case kCVPixelFormatType_24RGB:
		case kCVPixelFormatType_24BGR:
		default:
		{
			// Re-use the existing array if possible
			if (byteFrameData == nil || (*env)->GetArrayLength(env,byteFrameData) < length) {
				// Create an appropriately sized byte array to hold the data
				byteFrameData = (*env)->NewGlobalRef(env,(*env)->NewByteArray(env,length));
			}
			if (byteFrameData) {
				// Copy the raw data into the byteArray
				(*env)->SetByteArrayRegion(env,byteFrameData,0,length,rawData);
			
				// Get the class reference for our object
				jclass classRef = (*env)->GetObjectClass(env,objectRef);
				// Get the pushFrame methodId
				jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([BIIIF)V");
				// Call pushFrame with the byte array
				(*env)->CallVoidMethod(env,objectRef,methodId,byteFrameData,format,width,height,fps);
			}
			break;
		}	
			// 16 bit (short) storage of values
		case kCVPixelFormatType_16BE555:
		case kCVPixelFormatType_16LE555:
		case kCVPixelFormatType_16LE5551:
		case kCVPixelFormatType_16BE565:
		case kCVPixelFormatType_16LE565:
		case kCVPixelFormatType_16Gray:
		case kCVPixelFormatType_422YpCbCr16:
		case kCVPixelFormatType_422YpCbCr10:
		case kCVPixelFormatType_444YpCbCr10:
		{
			// Re-use the existing array if possible
			if (shortFrameData == nil || (*env)->GetArrayLength(env,shortFrameData) < length/2) {
				// Create an appropriately sized byte array to hold the data
				shortFrameData = (*env)->NewGlobalRef(env,(*env)->NewShortArray(env,length/2));
			}
			if (shortFrameData) {
				// Copy the raw data into the byteArray
				(*env)->SetShortArrayRegion(env,shortFrameData,0,length/2,rawData);
			
				// Get the class reference for our object
				jclass classRef = (*env)->GetObjectClass(env,objectRef);
				// Get the pushFrame methodId
				jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([SIIIF)V");
				// Call pushFrame with the short array
				(*env)->CallVoidMethod(env,objectRef,methodId,shortFrameData,format,width,height,fps);			
			}
			break;
		}	
			// 32 bit (int) storage of values
		case kCVPixelFormatType_32ABGR:
		case kCVPixelFormatType_32AlphaGray:
		case kCVPixelFormatType_32ARGB:
		case kCVPixelFormatType_32BGRA:
		case kCVPixelFormatType_32RGBA:
		{
			// Re-use the existing array if possible
			if (intFrameData == nil || (*env)->GetArrayLength(env,intFrameData) < length/4) {
				// Create an appropriately sized byte array to hold the data
				intFrameData = (*env)->NewGlobalRef(env,(*env)->NewIntArray(env,length/4));
			}
			if (intFrameData) {
				// Copy the raw data into the byteArray
				(*env)->SetByteArrayRegion(env,intFrameData,0,length/4,rawData);
			
				// Get the class reference for our object
				jclass classRef = (*env)->GetObjectClass(env,objectRef);
				// Get the pushFrame methodId
				jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([IIIIF)V");
				// Call pushFrame with the int array
				(*env)->CallVoidMethod(env,objectRef,methodId,intFrameData,format,width,height,fps);
			}
			break;
		}
	}
	// Autorelease and exception cleanup
//	JNF_COCOA_EXIT(env);

	// Detatch from Java
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
