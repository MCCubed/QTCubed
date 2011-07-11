//
//  QTCodec.java
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

package net.mc_cubed.qtcubed.media.codec;

import com.sun.media.BasicCodec;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import javax.media.Buffer;
import javax.media.Format;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;

/**
 *
 * @author shadow
 */
public class QTCodec extends BasicCodec {
	private long peer;
	private static final String encodingServerLocation;
	
    public QTCodec() {
        this.inputFormats = new Format[] { new VideoFormat("unknown") };
        this.outputFormats = new Format[] { new RGBFormat() };
		peer = _allocInit();
    }

	protected native long _allocInit();
	
    protected native Format[] _getSupportedFormats(long peer);

	static {
		// Write out the encoding server executable and store the location for native code
		String fullName = "EncodingServer";
		String libExtension = "";
		String resourceName = "/" + fullName + libExtension;
		// privileged code goes here
		InputStream inputStream = QTCodec.class.getResourceAsStream(resourceName);
		if (inputStream == null)
		{
			throw new NullPointerException("No resource found with name '"+resourceName+"'");
		}
		OutputStream outputStream = null;
		String encodingServerLoc = null;
		try
		{
			File tempFile = File.createTempFile(fullName, libExtension);
			tempFile.deleteOnExit();
			outputStream = new FileOutputStream(tempFile);
			byte[] buffer = new byte[8192];
			while (true)
			{
				int read = inputStream.read(buffer);
				if (read < 0)
				{
					break;
				}
				outputStream.write(buffer, 0, read);	
			}
			outputStream.flush();
			outputStream.close();
			outputStream = null;
			encodingServerLoc = tempFile.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally 
		{
			if (outputStream != null)
			{
				try {
					outputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}		
		
		encodingServerLocation = encodingServerLoc;
	}
    @Override
    public Format setInputFormat(Format format) {
        Format retval = super.setInputFormat(format);
        _setInputFormat(peer,format);
        return retval;
    }

    protected native void _setInputFormat(long peer,Format format);

    @Override
    public Format setOutputFormat(Format format) {
        if (format instanceof RGBFormat) {
            
            _setOutputFormat(peer,format);
            Format retval = super.setOutputFormat(format);
            return retval;
        }
        return null;

    }

    protected native void _setOutputFormat(long peer,Format format);

    @Override
    public int process(Buffer buffer, Buffer buffer1) {
        byte[] frameData = _processIntoFrames(peer,(byte[])buffer.getHeader(),(byte[])buffer.getData(),buffer.getOffset(),buffer.getLength());
        buffer1.setData(frameData);
        buffer1.setOffset(0);
        buffer1.setLength(frameData.length);
        buffer1.setFormat(outputFormat);
        return this.BUFFER_PROCESSED_OK;
    }

    @Override
    public Format[] getSupportedOutputFormats(Format format) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private native byte[] _processIntoFrames(long peer,byte[] header, byte[] buffer, int offset, int length);
	
	private native void _finalize(long peer);
	
	@Override
	protected void finalize() {
		_finalize(peer);
	}

}
