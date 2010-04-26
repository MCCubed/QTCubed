//
//  QTKitCaptureDecompressedAudioOutput.m
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

#import "QTKitCaptureDecompressedAudioOutput.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedAudioOutput
 * Method:    _allocInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedAudioOutput__1allocInit
(JNIEnv * env, jclass objectRef) {
	jlong ref;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	QTCaptureDecompressedAudioOutput * audioOutput = [[QTCaptureDecompressedAudioOutput alloc] init];

	QTKitCaptureDecompressedAudioOutput * delegate = [[QTKitCaptureDecompressedAudioOutput alloc] initWithEnv:env javaObject:objectRef];
	
	[audioOutput setDelegate:delegate];
	
	ref = (jlong) audioOutput;
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return ref;
	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTKitCaptureDecompressedAudioOutput
 * Method:    _release
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTKitCaptureDecompressedAudioOutput__1release
(JNIEnv *env, jobject objectRef, jlong captureOutputRef) {
	// Release both the object and its delegate
	QTCaptureDecompressedAudioOutput * captureOutput = (QTCaptureDecompressedAudioOutput *) captureOutputRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	id delegate = [captureOutput delegate];
	
	[delegate release];
	
	[captureOutput release];
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}


@implementation QTKitCaptureDecompressedAudioOutput

- (QTKitCaptureDecompressedAudioOutput *)initWithEnv:(JNIEnv *) env javaObject:(jobject) javaObjectRef {
	[super init];
	// Save a reference to the VM
	(*env)->GetJavaVM(env,&g_vm);
	// Create a global reference to this object so we can access it later
	objectRef = (*env)->NewGlobalRef(env,javaObjectRef);
	
	return self;
}

- (void)captureOutput:(QTCaptureOutput *)captureOutput didOutputAudioSampleBuffer:(QTSampleBuffer *)sampleBuffer fromConnection:(QTCaptureConnection *)connection {
	// Move into Java to deliver the data
	JNIEnv *env;
	(*g_vm)->AttachCurrentThread (g_vm, (void **) &env, NULL);
	
	void * rawData = [sampleBuffer bytesForAllSamples];
	int length = [sampleBuffer lengthForAllSamples];
	QTFormatDescription * formatDescription = [sampleBuffer formatDescription];
	QTTime duration = [sampleBuffer duration];
	
	NSData * audioDescriptionData = [formatDescription attributeForKey:QTFormatDescriptionAudioStreamBasicDescriptionAttribute];
	AudioStreamBasicDescription * audioDescription = (AudioStreamBasicDescription *)[audioDescriptionData bytes];
	
	NSLog(@"Outputting buffer of length %d ms with format: %#x and flags: %#x",length,audioDescription->mFormatID,audioDescription->mFormatFlags);
	
	switch (audioDescription->mFormatFlags & 0x05) {
		case kAudioFormatFlagIsFloat:
		{
			// Number of array slots of floats we need
			int floatLength = length/sizeof(float);
			// Re-use the existing array if possible
			if (floatBufferData == nil || (*env)->GetArrayLength(env,floatBufferData) < length) {
				// Clean up the previously allocated global reference
				if (floatBufferData != nil) {
					(*env)->DeleteGlobalRef(env,floatBufferData);
					floatBufferData = nil;
				}
				// Create an appropriately sized float array to hold the data
				floatBufferData = (*env)->NewGlobalRef(env,(*env)->NewFloatArray(env,floatLength));
			}
			if (floatBufferData) {
				// Copy the raw data into the byteArray
				(*env)->SetFloatArrayRegion(env,floatBufferData,0,floatLength,rawData);
				// Get the class reference for our object
				jclass classRef = (*env)->GetObjectClass(env,objectRef);
				// Get the pushFrame methodId
				jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([FIIIII)V");
				// Call pushFrame with the byte array
				(*env)->CallVoidMethod(env,objectRef,methodId,floatBufferData,floatLength,audioDescription->mFormatID,audioDescription->mSampleRate, audioDescription->mBitsPerChannel, audioDescription->mChannelsPerFrame);
			}
		}
		default:
		case kAudioFormatFlagIsSignedInteger:
		{
			// Determine whether the data is signed or not
			jboolean isSigned;
			if (kAudioFormatFlagIsSignedInteger & audioDescription->mFormatFlags) {
				isSigned = JNI_TRUE;
			} else {
				isSigned = JNI_FALSE;
			}
			
			// We should test how many bits we have per sample
			switch (audioDescription->mBitsPerChannel) {
				default:
				case 8: // use bytes
				{
					// Re-use the existing array if possible
					if (byteBufferData == nil || (*env)->GetArrayLength(env,byteBufferData) < length) {
						// Clean up the previously allocated global reference
						if (byteBufferData != nil) {
							(*env)->DeleteGlobalRef(env,byteBufferData);
							byteBufferData = nil;
						}
						// Create an appropriately sized float array to hold the data
						byteBufferData = (*env)->NewGlobalRef(env,(*env)->NewByteArray(env,length));
					}
					if (byteBufferData) {
						// Copy the raw data into the byteArray
						(*env)->SetByteArrayRegion(env,byteBufferData,0,length,rawData);
						// Get the class reference for our object
						jclass classRef = (*env)->GetObjectClass(env,objectRef);
						// Get the pushFrame methodId
						jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([BIIIIIZ)V");
						// Call pushFrame with the byte array
						(*env)->CallVoidMethod(env,objectRef,methodId,byteBufferData,length,audioDescription->mFormatID,audioDescription->mSampleRate, audioDescription->mBitsPerChannel, audioDescription->mChannelsPerFrame,isSigned);
					}
					break;
				}
				case 16: // use shorts
				{
					int shortLength = length / sizeof(short);
					// Re-use the existing array if possible
					if (shortBufferData == nil || (*env)->GetArrayLength(env,shortBufferData) < shortLength) {
						// Clean up the previously allocated global reference
						if (shortBufferData != nil) {
							(*env)->DeleteGlobalRef(env,shortBufferData);
							shortBufferData = nil;
						}
						// Create an appropriately sized float array to hold the data
						shortBufferData = (*env)->NewGlobalRef(env,(*env)->NewByteArray(env,shortLength));
					}
					if (shortBufferData) {
						// Copy the raw data into the byteArray
						(*env)->SetByteArrayRegion(env,shortBufferData,0,shortLength,rawData);
						// Get the class reference for our object
						jclass classRef = (*env)->GetObjectClass(env,objectRef);
						// Get the pushFrame methodId
						jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([SIIIIIZ)V");
						// Call pushFrame with the byte array
						(*env)->CallVoidMethod(env,objectRef,methodId,shortBufferData,shortLength,audioDescription->mFormatID,audioDescription->mSampleRate, audioDescription->mBitsPerChannel, audioDescription->mChannelsPerFrame,isSigned);
					}
					break;
				}
				case 32: // use ints
				{
					int intLength = length / sizeof(int);
					// Re-use the existing array if possible
					if (intBufferData == nil || (*env)->GetArrayLength(env,intBufferData) < intLength) {
						// Clean up the previously allocated global reference
						if (intBufferData != nil) {
							(*env)->DeleteGlobalRef(env,intBufferData);
							intBufferData = nil;
						}
						// Create an appropriately sized float array to hold the data
						intBufferData = (*env)->NewGlobalRef(env,(*env)->NewByteArray(env,intLength));
					}
					if (intBufferData) {
						// Copy the raw data into the byteArray
						(*env)->SetByteArrayRegion(env,intBufferData,0,intLength,rawData);
						// Get the class reference for our object
						jclass classRef = (*env)->GetObjectClass(env,objectRef);
						// Get the pushFrame methodId
						jmethodID methodId = (*env)->GetMethodID(env,classRef,"pushFrame","([IIIIIIZ)V");
						// Call pushFrame with the byte array
						(*env)->CallVoidMethod(env,objectRef,methodId,intBufferData,intLength,audioDescription->mFormatID,audioDescription->mSampleRate, audioDescription->mBitsPerChannel, audioDescription->mChannelsPerFrame,isSigned);
					}
					break;
				}
			}
			break;
		}
	}
	
	// Detatch from Java
	(*g_vm)->DetachCurrentThread (g_vm);
	
}

/* Clean up java references so they can be properly GCed in java */
- (void) dealloc {

	// Attach to java so we can release references
	JNIEnv *env;
	(*g_vm)->AttachCurrentThread (g_vm, (void **) &env, NULL);

	// release the references we hold

	if (objectRef != nil) {
		(*env)->DeleteGlobalRef(env,objectRef);
		objectRef = nil;		
	}
	if (byteBufferData != nil) {
		(*env)->DeleteGlobalRef(env,byteBufferData);
		byteBufferData = nil;		
	}
	if (shortBufferData != nil) {
		(*env)->DeleteGlobalRef(env,shortBufferData);
		shortBufferData = nil;		
	}
	if (intBufferData != nil) {
		(*env)->DeleteGlobalRef(env,intBufferData);
		intBufferData = nil;		
	}
	if (floatBufferData != nil) {
		(*env)->DeleteGlobalRef(env,floatBufferData);
		floatBufferData = nil;		
	}
	
	// Detatch from Java
	(*g_vm)->DetachCurrentThread (g_vm);
	
	g_vm = nil;
	
	[super dealloc];
}

@end
