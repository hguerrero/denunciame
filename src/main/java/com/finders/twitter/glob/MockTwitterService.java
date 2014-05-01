package com.finders.twitter.glob;

import twitter4j.Status;

public class MockTwitterService implements TwitterService {

	public void tweetToUser(Status replyTo, String message) {
		System.out.println("@" + replyTo.getUser().getScreenName() + " " + message);
	}
	
	public void tweet(String message) {
		System.out.println(message);
	}

}
