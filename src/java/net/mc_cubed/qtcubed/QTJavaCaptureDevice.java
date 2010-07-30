//
//  QTJavaCaptureDevice.java
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

import java.util.Collection;
import java.util.Properties;

/**
 *
 * @author shdwshard
 */
public class QTJavaCaptureDevice implements QTCaptureDevice {

    public void open() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String localizedDisplayName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<QTFormatDescription> getFormatDescriptions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String uniqueId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String modelUniqueId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasMediaType(QTMediaType type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Properties deviceAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
