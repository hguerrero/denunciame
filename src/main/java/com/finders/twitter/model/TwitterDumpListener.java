package com.finders.twitter.model;

import twitter4j.StallWarning;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public abstract class TwitterDumpListener implements StatusListener {

	public void onException(Exception ex) { }
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) { }
	public void onScrubGeo(long userId, long upToStatusId) { }
	public void onStallWarning(StallWarning warning) { }

}
