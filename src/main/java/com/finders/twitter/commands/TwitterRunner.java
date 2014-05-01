package com.finders.twitter.commands;

import java.io.IOException;

import org.kie.api.event.rule.AgendaEventListener;

import com.finders.twitter.model.TwitterDumpListener;

import twitter4j.TwitterException;

public interface TwitterRunner {

	void go() throws TwitterException, IOException;
	
	void stop();

	void addAgendaEventListener(AgendaEventListener agendaListener);

	void addTwitterDumpListener(TwitterDumpListener dumpListener);

	boolean isRunning();
}
