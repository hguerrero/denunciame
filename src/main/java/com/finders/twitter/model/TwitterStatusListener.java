package com.finders.twitter.model;

import org.kie.api.runtime.rule.EntryPoint;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TwitterStatusListener implements StatusListener {

    // Drools Fusion entry point
    private EntryPoint ep;
    
    /**
     * Default constructor. Stores the session entry point.
     */
    public TwitterStatusListener(EntryPoint ep) {
        this.ep = ep;
    }

    /**
     * Whenever a new message (Status) is twitted, it is
     * inserted into the session entry point.
     */
    public void onStatus( Status status ) {
        ep.insert( status );
    }

    public void onDeletionNotice( StatusDeletionNotice statusDeletionNotice ) {}
    public void onTrackLimitationNotice( int numberOfLimitedStatuses ) {}
    public void onScrubGeo( long userId, long upToStatusId ) {}
    public void onException( Exception ex ) {}

    public void onStallWarning(StallWarning warning) {
    }
}