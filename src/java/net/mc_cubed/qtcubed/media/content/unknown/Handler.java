//
//  Handler.java
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

import com.sun.media.BasicPlayer;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.IncompatibleSourceException;
import javax.media.TimeBase;
import javax.media.protocol.DataSource;
import net.mc_cubed.qtcubed.QTCubedFactory;
import net.mc_cubed.qtcubed.QTMovieView;

/**
 *
 * @author shadow
 */
public class Handler extends BasicPlayer {

    final QTMovieView view;

    public Handler() {
		Logger.getLogger(getClass().getName()).info("Instantiating QTCubed Handler");
        QTMovieView newView = null;
        try {
            newView = QTCubedFactory.initQTMovieView();
        } catch (InstantiationException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }

        view = newView;
    }

    @Override
    public Component getVisualComponent() {
        return view.getComponent();
    }

    @Override
    public void setSource(DataSource source) throws IOException, IncompatibleSourceException {
        super.setSource(source);
        try {
            view.setMovie(QTCubedFactory.initQTMovie(source.getLocator().getURL()));
        } catch (InstantiationException ex) {
            throw new IncompatibleSourceException(ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doStart() {
        super.doStart();
        view.play();
    }

    @Override
    protected void doStop() {
        super.doStop();
        view.pause();
    }

    @Override
    protected boolean audioEnabled() {
        return true;
    }

    @Override
    protected boolean videoEnabled() {
        return true;
    }

    @Override
    protected TimeBase getMasterTimeBase() {
        return null;
    }

    @Override
    public void updateStats() {
        // Do nothing
    }


}
