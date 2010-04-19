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
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed;

public class QTTimeRange {
	protected final QTTime time;
	protected final QTTime duration;
	
	public QTTimeRange(QTTime time, QTTime duration) {
		this.time = new QTTime(time);
		this.duration = new QTTime(duration);
	}
	
	public boolean isTimeInRange(QTTime time) {
		// TODO: implement this function
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public boolean equals(QTTimeRange range) {
		if (range != null && this.time.equals(range.time) && this.duration.equals(range.duration)) {
			return true;
		} else {
			return false;
		}
	}
	
	public QTTime getEnd() {
		return time.add(duration);
	}
	
	public QTTimeRange union(QTTimeRange range) {
		// TODO: Compute a union of the current, and supplied ranges
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public QTTimeRange intersect(QTTimeRange range) {
		// TODO: Compute an intersection of the current and supplied ranges
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public String toString() {
		// TODO: Compute the string value of this range in the format:
		// dd:hh:mm:ss.ff/ts~dd:hh:mm:ss.ff/ts
		return time.toString() + "~" + getEnd().toString();
	}
	
	public QTTimeRange(String range) {
		// TODO: construct the QTTimeRange from the supplied string with format:
		// dd:hh:mm:ss.ff/ts~dd:hh:mm:ss.ff/ts
		throw new UnsupportedOperationException("Not Implemented");
	}

}