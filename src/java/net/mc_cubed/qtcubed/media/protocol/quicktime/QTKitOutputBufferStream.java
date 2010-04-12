/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mc_cubed.qtcubed.media.protocol.quicktime;

import java.io.IOException;
import javax.media.Buffer;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.PushBufferStream;
import net.mc_cubed.qtcubed.QTKitCaptureDataDelegate;

/**
 *
 * @author shadow
 */
abstract public class QTKitOutputBufferStream implements PushBufferStream,QTKitCaptureDataDelegate {

    byte[] sampleData;

    BufferTransferHandler bth;
    
    public void read(Buffer buffer) throws IOException {
        if (sampleData != null) {
            buffer.setData(sampleData);
            buffer.setLength(sampleData.length);
            sampleData = null;
        } else {
            buffer.setLength(0);
        }
    }

    public void setTransferHandler(BufferTransferHandler bth) {
        this.bth = bth;
    }

    public long getContentLength() {
        return sampleData.length;
    }

    public void nextSample(byte[] sampleData) {
        this.sampleData = sampleData;
        if (bth != null) {
            bth.transferData(this);
        }
    }

    public QTKitOutputBufferStream(QTCubedDelegator delegator) {
        delegator.setDataDelegate(this);
    }

}
