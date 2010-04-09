//
//  QTKitCaptureDecompressedVideoOutput.h
//  QTCubed
//
//  Created by Chappell Charles on 10/04/09.
//  Copyright 2010 MC Cubed, Inc. All rights reserved.
//

#import <QTKit/QTKit.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "net_mc_cubed_qtcubed_QTKitCaptureDecompressedVideoOutput.h"



@interface QTKitCaptureDecompressedVideoOutput : NSObject {
@private
	JavaVM * g_vm;
	jobject objectRef;
}

- (QTKitCaptureDecompressedVideoOutput *)initWithEnv:(JNIEnv *) env javaObject:(jobject) objectRef;

- (void)captureOutput:(QTCaptureOutput *)captureOutput didOutputVideoFrame:(CVImageBufferRef)videoFrame withSampleBuffer:(QTSampleBuffer *)sampleBuffer fromConnection:(QTCaptureConnection *)connection;

@end
