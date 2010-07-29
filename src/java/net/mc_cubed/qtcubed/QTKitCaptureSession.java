//
//  QTKitCaptureSession.java
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

package net.mc_cubed.qtcubed;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author shadow
 */
class QTKitCaptureSession implements QTCaptureSession {

    protected final long captureSessionRef;
    protected List<QTKitCaptureInput> inputList = new LinkedList<QTKitCaptureInput>();
    protected List<QTKitCaptureOutput> outputList = new LinkedList<QTKitCaptureOutput>();

    public QTKitCaptureSession() {
        this(_allocInit());
    }

    native static protected long _allocInit();

	public List<QTCaptureOutput> getOutputList() {
		List<QTCaptureOutput> retval = new LinkedList<QTCaptureOutput>();
		retval.addAll(outputList);
		return retval;
	}
    protected QTKitCaptureSession(long captureSessionRef) {
        this.captureSessionRef = captureSessionRef;
    }

    long getCaptureSessionRef() {
        return captureSessionRef;
    }

    native static protected void _release(long captureSessionRef);

    @Override
    protected void finalize() {
        _release(captureSessionRef);
    }

    public boolean addInput(QTCaptureInput input) {
        inputList.add((QTKitCaptureInput)input);
        return _addInput(captureSessionRef, ((QTKitCaptureInput)input).getCaptureInputRef());
    }

    public boolean addOutput(QTCaptureOutput output) {
        outputList.add((QTKitCaptureOutput)output);
        return _addOutput(captureSessionRef, ((QTKitCaptureOutput)output).getCaptureOutputRef());

    }

    public void removeInput(QTCaptureInput input) {
        inputList.remove((QTKitCaptureInput)input);
        _removeInput(captureSessionRef,((QTKitCaptureInput)input).getCaptureInputRef());
    }

    public void removeOutput(QTCaptureOutput output) {
        outputList.remove((QTKitCaptureOutput)output);
        _removeOutput(captureSessionRef,((QTKitCaptureOutput)output).getCaptureOutputRef());
    }

    native protected boolean _addInput(long captureSessionRef, long captureInputRef);

    native protected boolean _addOutput(long captureSessionRef, long captureInputRef);

    public void startRunning() {
        _startRunning(captureSessionRef);
    }

    native protected void _startRunning(long captureSessionRef);

    public boolean isRunning() {
        return _isRunning(captureSessionRef);
    }

    native protected boolean _isRunning(long captureSessionRef);

    public void stopRunning() {
        _stopRunning(captureSessionRef);
    }

    native protected void _stopRunning(long captureSessionRef);

    private native void _removeInput(long captureSessionRef, long captureInputRef);

    private native void _removeOutput(long captureSessionRef, long captureOutputRef);
}
