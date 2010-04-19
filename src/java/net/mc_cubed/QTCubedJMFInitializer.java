//
//  QTCubedJMFInitializer.java
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

package net.mc_cubed;

import com.sun.media.renderer.video.AWTRenderer;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.Collection;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.PackageManager;
import net.mc_cubed.qtcubed.QTKitCaptureDevice;
import net.mc_cubed.qtcubed.QTKitFormatUtils;

/**
 *
 * @author shadow
 */
public class QTCubedJMFInitializer {

    static {
        Vector packagePrefix = PackageManager.getContentPrefixList();
        String myPackagePrefix = new String("net.mc_cubed.qtcubed");
        if (!packagePrefix.contains(myPackagePrefix)) {
            // Add new package prefix to end of the package prefix list.
            packagePrefix.addElement(myPackagePrefix);
            PackageManager.setContentPrefixList(packagePrefix);
            // Save the changes to the package prefix list.
        }

        packagePrefix = PackageManager.getProtocolPrefixList();
        if (!packagePrefix.contains(myPackagePrefix)) {
            // Add new package prefix to end of the package prefix list.
            packagePrefix.addElement(myPackagePrefix);
            PackageManager.setProtocolPrefixList(packagePrefix);
            // Save the changes to the package prefix list.
        }

        for (QTKitCaptureDevice captureDevice : QTKitCaptureDevice.inputDevices()) {
            String name = captureDevice.localizedDisplayName();
            MediaLocator ml = new MediaLocator("quicktime://" + captureDevice.uniqueId());
            Collection<Format> formats = QTKitFormatUtils.QTKitToJMF(captureDevice.getFormatDescriptions());
            Format[] formatsArray = formats.toArray(new Format[0]);
            CaptureDeviceInfo info = new CaptureDeviceInfo(name,ml,formatsArray);
            CaptureDeviceManager.addDevice(info);
            System.out.println("Added capture device: " + info);
        }
		
        // These are likely to generate exceptions, so do this last
        try {
            PackageManager.commitProtocolPrefixList();
            PackageManager.commitContentPrefixList();
        } catch (Exception ex) {
            // If we fail, we fail, no harm done.
        }

        AWTRenderer renderer = new AWTRenderer();
        for (Format format :renderer.getSupportedInputFormats()) {
            System.out.println("Supported Format: " + format);
        }

    }

    public static void init() {
        Logger.getAnonymousLogger().info("Initialized QTCubed JMF Plugin");
    }
}
