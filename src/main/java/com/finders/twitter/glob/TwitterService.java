package com.finders.twitter.glob;

import twitter4j.Status;

public interface TwitterService {

	void tweetToUser(Status replyTo, String message);
	
	void tweet(String message);

}
