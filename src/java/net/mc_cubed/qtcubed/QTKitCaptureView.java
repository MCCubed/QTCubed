//
//  QTKitCaptureView.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
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

import com.apple.eawt.CocoaComponent;
import java.awt.Component;
import java.awt.Dimension;
import net.mc_cubed.QTCubed;

/**
 *
 * @author shadow
 */
public class QTKitCaptureView extends CocoaComponent {
    protected static final int SET_CAPTURE_SESSION = 1;

	protected Dimension preferredSize = new Dimension(320,240);

	static QTCubed qtCubed;
	
    protected QTKitCaptureSession captureSession;
    
    @Override
    native public long createNSViewLong();


    @Override
    public int createNSView() {
        return (int)createNSViewLong();
    }

    public void setCaptureSession(QTKitCaptureSession session) {
        this.captureSession = session;
        sendMessage(SET_CAPTURE_SESSION, session.getCaptureSessionRef());
    }

    public Component getComponent() {
        return this;
    }
	
	public Dimension getMaximumSize() {
		// TODO: Get the maximum size from the native code
		return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
	}
	
	public Dimension getMinimumSize() {
		// TODO: Get the minimum size from the native code
		return new Dimension(2,2);
	}
	
	public Dimension getPreferredSize() {
		// TODO: Get the preferred size from the native code			
		return preferredSize;
	}
}
