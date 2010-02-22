//
//  QTMovieView.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/20.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
//
//  This is proprietary software, and may not be used without the express written consent of:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  このソフトウェアは専売権付ソフトウェアです。株式会社MCキューブの許可なく複製、転用、販売等の二次利用をすることを固く禁じます。
//  株式会社エムシーキューブ
//  埼玉県さいたま市浦和区上木崎１−３−４

package net.mc_cubed.qtcubed;

import java.awt.Dimension;
import com.apple.eawt.CocoaComponent;

public class QTMovieView extends CocoaComponent {

	protected static final int SET_MOVIE = 1;
	protected Dimension size = new Dimension(320,240);
	protected Dimension preferredSize = new Dimension(320,240);
	
	protected QTMovie movie;
	
	@Deprecated
	public int createNSView() {
		return (int)createNSViewLong();
	}
	
	// Instantiate the QTMovieView on the native side and return it as a long
	public native long createNSViewLong();
	
	public Dimension getMaximumSize() {
		// TODO: Get the maximum size from the native code
		return size;
	}
	
	public Dimension getMinimumSize() {
		// TODO: Get the minimum size from the native code
		return size;
	}
	
	public Dimension getPreferredSize() {
		// TODO: Get the preferred size from the native code			
		return preferredSize;
	}
	
	public void setMovie(QTMovie movie) {
		this.movie = movie;
		this.sendMessage(SET_MOVIE, movie.getMovieRef());
		Long l = 1L;
		long l2 = l.longValue();
	}
	
	public QTMovieView() {
		super();
	}
}