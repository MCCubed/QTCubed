//
//  QTTime.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
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

package net.mc_cubed.qtcubed;

import java.math.BigInteger;

public class QTTime {
	public static long kQTTimeIsIndefinite = 1 << 0;
	public static final QTTime QTZeroTime = new QTTime(BigInteger.ZERO,0L,0L);
	public static final QTTime QTIndefiniteTime = new QTTime(BigInteger.ZERO,0L,kQTTimeIsIndefinite);

	protected static long FLAG_MASK = 0x01L;
	final BigInteger timeValue;
	final long timeScale;
	final long flags;

	// Copy constructor
	QTTime(QTTime time) {
		this.timeValue = time.timeValue;
		this.timeScale = time.timeScale;
		this.flags = time.flags;
	}
	
	public QTTime(BigInteger timeValue, long timeScale, long flags) {
		this.timeValue = timeValue;
		this.timeScale = timeScale;
		this.flags = flags;
	}

	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN QTTime QTMakeTime (long long timeValue, long timeScale)
	 */
	public QTTime(BigInteger timeValue, long timeScale) {
		this(timeValue,timeScale,0);
	}
	
	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN NSComparisonResult QTTimeCompare (QTTime time, QTTime otherTime)
	 */
	public boolean equals(QTTime time) {
		// TODO: Make sure this comparison is correct, and probably need to include a small delta to ensure calculation inaccuracies don't spoil the calculation
		if ((flags ^ time.flags & FLAG_MASK) == 0){			
			if (((flags & kQTTimeIsIndefinite) != 0) || 
				timeValue.multiply(BigInteger.valueOf(timeScale)).equals(time.timeValue.multiply(BigInteger.valueOf(time.timeScale)))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Takes the place of this function
	 * QTKIT_EXTERN QTTime QTTimeFromString (NSString *string)
	 * which decodes the following format:
	 *  dd:hh:mm:ss.ff/ts
	 */
	public QTTime(String timeString) {
		// TODO: Implement this constructor
		throw new UnsupportedOperationException("Not implemented");
	}
	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN BOOL QTTimeIsIndefinite (QTTime time)
	 */
	public boolean isIndefinite() {
		return ((flags & kQTTimeIsIndefinite) != 0) ? true : false;
	}

		
	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN QTTime QTMakeTimeScaled (QTTime time, long timeScale)
	 */
	public QTTime(QTTime time, long timeScale) {
		// TODO: Implement this constructor
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN QTTime QTTimeIncrement (QTTime time, QTTime increment)
	 */
	public QTTime add(QTTime time) {
		// TODO: Implement this function
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN QTTime QTTimeDecrement (QTTime time, QTTime decrement)
	 */
	public QTTime subtract(QTTime time) {
		// TODO: Implement this function
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Takes the place of the following function:
	 * QTKIT_EXTERN NSString *QTStringFromTime (QTTime time)
	 * which encodes to the following format:
	 *  dd:hh:mm:ss.ff/ts
	 */
	@Override
	public String toString() {
		// TODO: encode time into SMPTETime
		throw new UnsupportedOperationException("Not implemented");
	}
//	QTKIT_EXTERN QTTime QTMakeTimeWithTimeRecord (TimeRecord timeRecord)				AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
//	QTKIT_EXTERN QTTime QTMakeTimeWithTimeInterval (NSTimeInterval timeInterval)		AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
	
//	QTKIT_EXTERN BOOL QTGetTimeRecord (QTTime time, TimeRecord *timeRecord)				AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
//	QTKIT_EXTERN BOOL QTGetTimeInterval (QTTime time, NSTimeInterval *timeInterval)		AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
		
//	QTKIT_EXTERN QTTime QTTimeIncrement (QTTime time, QTTime increment)					AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
//	QTKIT_EXTERN QTTime QTTimeDecrement (QTTime time, QTTime decrement)					AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
	
	// dd:hh:mm:ss.ff/ts
//	QTKIT_EXTERN NSString *QTStringFromTime (QTTime time)								AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
//	QTKIT_EXTERN QTTime QTTimeFromString (NSString *string)								AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
	
//	QTKIT_EXTERN BOOL QTTimeIsIndefinite (QTTime time)									AVAILABLE_QTKIT_VERSION_7_0_AND_LATER;
	
/*	@interface NSValue (NSValueQTTimeExtensions)
	+ (NSValue *)valueWithQTTime:(QTTime)time;
	- (QTTime)QTTimeValue;
	@end
	
	@interface NSCoder (NSQTTimeCoding)
	- (void)encodeQTTime:(QTTime)time forKey:(NSString *)key;
	- (QTTime)decodeQTTimeForKey:(NSString *)key;
	@end
*/	
	// SMPTETime:
		
/*	#if QTKIT_VERSION_MAX_ALLOWED >= QTKIT_VERSION_7_2
		
		@interface NSValue (NSValueQTSMPTETimeExtensions)
		+ (NSValue *)valueWithSMPTETime:(SMPTETime)time;
		- (SMPTETime)SMPTETimeValue;
	@end
	
	@interface NSCoder (NSCoderQTSMPTETimeCoding)
	- (void)encodeSMPTETime:(SMPTETime)time forKey:(NSString *)key;
	- (SMPTETime)decodeSMPTETimeForKey:(NSString *)key;
	@end*/
	
}