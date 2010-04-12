/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mc_cubed;

import java.util.Vector;
import java.util.logging.Logger;
import javax.media.PackageManager;

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

        // These are likely to generate exceptions, so do this last
        try {
            PackageManager.commitProtocolPrefixList();
            PackageManager.commitContentPrefixList();
        } catch (Exception ex) {
            // If we fail, we fail, no harm done.
        }

    }

    static void init() {
        Logger.getAnonymousLogger().info("Initialized QTCubed JMF Plugin");
    }
}
