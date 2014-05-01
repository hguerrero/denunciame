package com.finders.twitter.glob;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterServiceImpl implements TwitterService {

	private static final TwitterServiceImpl INSTANCE = new TwitterServiceImpl();
	
	private final Twitter twitter;
	
	private TwitterServiceImpl() {
		this.twitter = new TwitterFactory().getInstance();
	}

	public static TwitterServiceImpl getInstance() {
		return INSTANCE;
	}
	
	public void tweetToUser(Status replyTo, String message) {
		StatusUpdate latestStatus = new StatusUpdate("@" + replyTo.getUser().getScreenName() + " " + message);
		latestStatus.setInReplyToStatusId(replyTo.getId());
		try {
			twitter.updateStatus(latestStatus);
		} catch (TwitterException e) {
			//TODO
			e.printStackTrace();
		}
	}
	
	public void tweet(String message) {
		try {
			twitter.updateStatus(message);
		} catch (TwitterException e) {
			//TODO
			e.printStackTrace();
		}
	}
}
