//
//  QTMovieViewBridge.m
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//
//  This is proprietary software, and may not be used without the express written consent of:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  このソフトウェアは専売権付ソフトウェアです。株式会社MCキューブの許可なく複製、転用、販売等の二次利用をすることを固く禁じます。
//  株式会社エムシーキューブ
//  埼玉県さいたま市浦和区上木崎１−３−４

#include "QTMovieViewBridge.h"

/*
 * Class:     net_mc_cubed_qtcubed_QTMovieView
 * Method:    createNSViewLong
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_mc_1cubed_qtcubed_QTMovieView_createNSViewLong
(JNIEnv *env, jobject objectRef) {
	jlong viewPointer;

	/* Set up autorelease and exception handling */
	JNF_COCOA_ENTER(env);

	viewPointer = (jlong)[[QTMovieViewBridge alloc]init];

	/* Autorelease and exception cleanup */
	JNF_COCOA_EXIT(env);
	
	return viewPointer;
}

@implementation QTMovieViewBridge

-(id)init
{
    return [super init];
}


- (void) awtMessage:(jint)messageID message:(jobject)message env:(JNIEnv *)env {
	switch (messageID) {
		case net_mc_cubed_qtcubed_QTMovieView_SET_MOVIE:
		{
			jclass cls = (*env)->GetObjectClass(env,message);
			jmethodID getLongMethod = (*env)->GetMethodID(env,cls,"longValue","()J");
			jlong movieRef = (*env)->CallLongMethod(env,message,getLongMethod);
			[self setMovie:(QTMovie *)movieRef];
			break;			
		}
			// more cases...
	}
}

@end

