/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mc_cubed.qtcubed.media.protocol.quicktime;

import javax.media.Format;
import javax.media.protocol.ContentDescriptor;
import net.mc_cubed.qtcubed.QTKitCaptureDecompressedAudioOutput;

/**
 *
 * @author shadow
 */
public class QTKitAudioCapture extends QTKitOutputBufferStream {

    public QTKitAudioCapture(QTKitCaptureDecompressedAudioOutput output) {
        super(output);
        // TODO: Do something with the output object

    }

    public Format getFormat() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ContentDescriptor getContentDescriptor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean endOfStream() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] getControls() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getControl(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
