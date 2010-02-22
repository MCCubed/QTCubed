//
//  QTMovieViewBridge.h
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import <QTKit/QTKit.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "net_mc_cubed_qtcubed_QTMovieView.h"


@interface QTMovieViewBridge : QTMovieView
{

}

-(id)init;
-(void)awtMessage:(jint)messageID message:(jobject)message env:(JNIEnv *)env;


@end
