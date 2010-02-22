//
//  QTMovieBridge.m
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
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

#import <QTKit/QTKit.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "net_mc_cubed_qtcubed_QTMovie.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _canInitWithFile
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1canInitWithFile
(JNIEnv *env, jclass classRef, jstring jstringFilename) {
	jboolean canInit;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Convert the jstring into an NSString *
	const jchar *chars = (*env)->GetStringChars(env, jstringFilename, NULL);
	NSString *filename = [NSString stringWithCharacters:(UniChar *)chars
												 length:(*env)->GetStringLength(env, jstringFilename)];
	(*env)->ReleaseStringChars(env, jstringFilename, chars);
	
	// Do the actual call to QTMovie and set the return value depending on the result
	if ([QTMovie canInitWithFile:filename]) {
		canInit = JNI_TRUE;
	} else {
		canInit = JNI_FALSE;
	}		 
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return canInit;	
}
/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movieWithFile
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movieWithFile
(JNIEnv *env, jclass classRef, jstring jstringFilename) {
	jlong movieRef = 0;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
	// Convert the resulting jstring into an NSString *
	const jchar *chars = (*env)->GetStringChars(env, jstringFilename, NULL);
	NSString *filename = [NSString stringWithCharacters:(UniChar *)chars
												 length:(*env)->GetStringLength(env, jstringFilename)];
	(*env)->ReleaseStringChars(env, jstringFilename, chars);

	// Do the actual call to QTMovie and set the return value depending on the result
	NSError * error = nil;
	QTMovie * qtMovie = [QTMovie movieWithFile:filename error:&error];
	if (qtMovie != nil && error == nil) {
		// We want to keep this, and pass the pointer back to java to store
		[qtMovie retain];
		movieRef = (jlong)qtMovie;
	} else {
		jclass exClass = (*env)->FindClass(env,"java.lang.Exception");
		(*env)->ThrowNew(env,exClass,[[error localizedDescription] UTF8String]);
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return movieRef;	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _canInitWithURL
 * Signature: (Ljava/net/URL;)Z
 */
JNIEXPORT jboolean JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1canInitWithURL
(JNIEnv *, jclass, jobject);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movie
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movie
(JNIEnv *, jclass);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movieWithURL
 * Signature: (Ljava/net/URL;)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movieWithURL
(JNIEnv *, jclass, jobject);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movieWithData
 * Signature: ([B)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movieWithData
(JNIEnv * env, jclass classRef, jbyteArray jbytes) {
	jlong movieRef;
	
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);
	
/*	// Get the QTMovie object
	jclass cls = (*env)->GetObjectClass(env,movieObject);
	jmethodID getMovieRefMethod = (*env)->GetMethodID(env,cls,"getMovieRef","()J");
	jlong movieRef = (*env)->CallLongMethod(env,movieObject,getMovieRefMethod);
	QTMovie* qtMovie = (QTMovie*)movieRef;*/
	
	// get the bytes from the byte array
	int numBytes = (*env)->GetArrayLength(env,jbytes);
	jboolean isCopy;
	jbyte* bytes = (*env)->GetByteArrayElements(env,jbytes,&isCopy);
	
	// Copy the bytes into an NSData object
	NSData* dataObject = [NSData dataWithBytes:bytes length:numBytes];
	
	// Release the jbytesArray and bytes
	if (isCopy == JNI_TRUE) {
		free(bytes);
	}
	
	// Initialize the movie from the NSData object contents
	NSError * error = nil;
	QTMovie *qtMovie = [QTMovie movieWithData:dataObject error:&error];
	if (qtMovie != nil && error == nil) {
		// We want to keep this, and pass the pointer back to java to store
		[qtMovie retain];
		movieRef = (jlong)qtMovie;
	} else {
		jclass exClass = (*env)->FindClass(env,"java.lang.Exception");
		(*env)->ThrowNew(env,exClass,[[error localizedDescription] UTF8String]);
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return movieRef;	
}

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movieWithAttributes
 * Signature: (Ljava/util/Properties;)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movieWithAttributes
(JNIEnv *, jclass, jobject);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _movieNamed
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1movieNamed
(JNIEnv *, jclass, jstring);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _initWithFile
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1initWithFile
(JNIEnv *, jobject, jstring);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _initWithURL
 * Signature: (Ljava/net/URL;)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1initWithURL
(JNIEnv *, jobject, jobject);

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _initWithData
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1initWithData
(JNIEnv *env, jobject movieObject, jbyteArray jbytes) {
	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	// Get the QTMovie object
	jclass cls = (*env)->GetObjectClass(env,movieObject);
	jmethodID getMovieRefMethod = (*env)->GetMethodID(env,cls,"getMovieRef","()J");
	jlong movieRef = (*env)->CallLongMethod(env,movieObject,getMovieRefMethod);
	QTMovie* qtMovie = (QTMovie*)movieRef;
	
	// get the bytes from the byte array
	int numBytes = (*env)->GetArrayLength(env,jbytes);
	jboolean isCopy;
	jbyte* bytes = (*env)->GetByteArrayElements(env,jbytes,&isCopy);
	
	// Copy the bytes into an NSData object
	NSData* dataObject = [NSData dataWithBytes:bytes length:numBytes];
	
	// Release the jbytesArray and bytes
	if (isCopy == JNI_TRUE) {
		free(bytes);
	}
	
	// Initialize the movie from the NSData object contents
	NSError * error = nil;
	[qtMovie initWithData:dataObject error:&error];

	// If an error occured, create an exception with a description of the problem, and throw it.
	if (qtMovie == nil || error == nil) {
		jclass exClass = (*env)->FindClass(env,"java.lang.Exception");
		(*env)->ThrowNew(env,exClass,[[error localizedDescription] UTF8String]);
	}
	
	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
}

/*
 * Class:     net_mc_cubed_qtcubed_QTMovie
 * Method:    _invalidate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_net_mc_1cubed_qtcubed_QTMovie__1invalidate
(JNIEnv *, jobject);
