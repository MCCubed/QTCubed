package net.mc_cubed.qtcubed;

import com.sun.media.BasicPlayer;
import javax.media.TimeBase;

public class QTPlayer extends BasicPlayer {
	/**
     * Return true if the player is currently playing media 
     * with an audio track.
     * @return true if the player is playing audio.
     */
    protected boolean audioEnabled() {
		throw new java.lang.UnsupportedOperationException("Not yet implemented");
	}
	
    /**
     * Return true if the player is currently playing media 
     * with a video track.
     * @return true if the player is playing video.
     */
    protected boolean videoEnabled() {
		throw new java.lang.UnsupportedOperationException("Not yet implemented");
	}
	
    /**
     * This should be implemented by the subclass.
     * The subclass method should return the master TimeBase -- the
     * TimeBase that all other controllers slave to.
     * Use SystemTimeBase if unsure.
     * @return the master time base.
     */
    protected TimeBase getMasterTimeBase() {
		throw new java.lang.UnsupportedOperationException("Not yet implemented");
	}

	/**
     * This is being called from a looping thread to update the stats.
     */
	public void updateStats() {
		// TODO: Do something
	}
	
}