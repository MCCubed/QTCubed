//
//  QuicktimePlaybackEngine.java
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
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed.media.content.unknown;

import com.sun.media.BasicController;
import com.sun.media.InputConnector;
import com.sun.media.Module;
import com.sun.media.ModuleListener;
import javax.media.Buffer;
import javax.media.Format;

/**
 *
 * @author shadow
 */
public class QuicktimePlaybackEngine extends BasicController implements ModuleListener {

    
    @Override
    protected boolean isConfigurable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doRealize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void abortRealize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doPrefetch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void abortPrefetch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void doStart() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void bufferPrefetched(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopAtTime(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mediaEnded(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetted(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dataBlocked(Module module, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void framesBehind(Module module, float f, InputConnector ic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void markedDataArrived(Module module, Buffer buffer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void formatChanged(Module module, Format format, Format format1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void formatChangedFailure(Module module, Format format, Format format1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pluginTerminated(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void internalErrorOccurred(Module module) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
