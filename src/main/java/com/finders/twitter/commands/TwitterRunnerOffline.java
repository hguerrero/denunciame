/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finders.twitter.commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.io.ResourceFactory;

import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.TwitterException;

import com.finders.twitter.glob.MockTwitterService;
import com.finders.twitter.model.TwitterDumpListener;
import com.finders.twitter.model.TwitterStatusListener;

/**
 * TwitterCBR
 */
public class TwitterRunnerOffline implements TwitterRunner {
    public static final boolean disableLog = true;

    private final String rulesContent;

    private KieSession ksession = null;
    
	private TwitterDumpListener dumpListener;

	private AgendaEventListener agendaListener;
	
	private Thread runningThread;
	
	private boolean running = false;

    public TwitterRunnerOffline(String rulesContent) {
    	super();
    	this.rulesContent = rulesContent;
	}
    
    /**
     * Main method
     */
    public void go() throws TwitterException, IOException{
        
        // Creates a knowledge base
        final KieBase kbase = createKieBase();
        
        // Creates a knowledge session
        this.ksession = createKieSession( kbase );
        
        // Gets the stream entry point 
        final EntryPoint ep = ksession.getEntryPoint( "twitter" );
        
        new Thread( new Runnable() {
            public void run() {
                // Starts to fire rules in Drools Fusion
                ksession.fireUntilHalt();
            }
        }).start();

        this.runningThread = new Thread(new Runnable() {
        	public void run() {
        		// reads the status stream and feeds it to the engine 
                feedEvents( ksession, ep );
                ksession.halt();
        	}
        });
        this.runningThread.start();
        this.running = true;
    }

    public void addAgendaEventListener(AgendaEventListener agendaListener) {
    	this.agendaListener = agendaListener;
    }
    
    public void addTwitterDumpListener(TwitterDumpListener dumpListener) {
    	this.dumpListener = dumpListener;
    }

    /**
     * Feed events from the stream to the engine
     * @param ksession
     * @param ep
     */
    private void feedEvents( final KieSession ksession, final EntryPoint ep ) {
        try {
            StatusListener listener = new TwitterStatusListener( ep );
            ObjectInputStream in = new ObjectInputStream( getClass().getResourceAsStream( "/twitterstream.dump" ) );
            SessionPseudoClock clock = ksession.getSessionClock();
            
            for( int i = 0; ; i++ ) {
                try {
                    // Read an event
                    Status st = (Status) in.readObject();
                    // Using the pseudo clock, advance the clock
                    clock.advanceTime( st.getCreatedAt().getTime() - clock.getCurrentTime(), TimeUnit.MILLISECONDS );
                    // call the listener
                    listener.onStatus( st );
                    if (this.dumpListener != null) {
                    	this.dumpListener.onStatus(st);
                    }
                    ksession.getEntryPoint("twitter").insert(st);
                } catch( IOException ioe ) {
                    break;
                }
            }
            in.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void stop() {
    	runningThread.interrupt();
    	ksession.dispose();
    	running = false;
    }
    
    public boolean isRunning() {
    	return running;
    }
    /**
     * Creates a Drools KnowledgeBase and adds the given rules file into it
     */
    private KieBase createKieBase( ) {
    	KieServices ks = KieServices.Factory.get();
    	KieFileSystem kfs = ks.newKieFileSystem();
    	kfs.write( "src/main/resources/rules.drl", ResourceFactory.newByteArrayResource(this.rulesContent.getBytes()) );
    	// Parses and compiles the rules file
    	KieBuilder kbuilder = ks.newKieBuilder(kfs);

    	// Configures the Stream mode
    	KieBaseConfiguration conf = ks.newKieBaseConfiguration();
    	conf.setOption( EventProcessingOption.STREAM );
    	
    	KieModule kmodule = kbuilder.getKieModule();
    	KieContainer kcontainer = ks.newKieContainer(kmodule.getReleaseId());
    	KieBase kbase = kcontainer.newKieBase(conf);
        
        return kbase;
    }

    /**
     * Creates a Drools Stateful Knowledge Session
     */
    private KieSession createKieSession( final KieBase kbase ) {
    	KieServices ks = KieServices.Factory.get();
        final KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption( ClockTypeOption.get( ClockType.PSEUDO_CLOCK.getId() ) );
        final KieSession ksession = kbase.newKieSession(ksconf, null);
        if (agendaListener != null) {
        	ksession.addEventListener(agendaListener);
        }
        ksession.setGlobal("twitter", new MockTwitterService());
        return ksession;
    }

}
