package com.finders.twitter.commands;

import java.io.IOException;

import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.io.ResourceFactory;

import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import com.finders.twitter.glob.MockTwitterService;
import com.finders.twitter.model.TwitterDumpListener;
import com.finders.twitter.model.TwitterStatusListener;

/**
 * TwitterCBR
 */
public class TwitterRunnerOnline implements TwitterRunner {
    public static final boolean disableLog = true;
    
    private final String rulesContent;
    private boolean running = false;
    
    private Thread runningThread = null;
    private Thread runningThread2 = null;
    private KieSession ksession = null;

    private TwitterStream twitterStream;
    
	private AgendaEventListener agendaListener;

	private TwitterDumpListener dumpListener;

    public TwitterRunnerOnline(String rulesContent) {
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
        
        // Connects to the twitter stream and register the listener 
        this.runningThread = new Thread( new Runnable() {
            public void run() {
                StatusListener listener = new TwitterStatusListener( ep );
                TwitterRunnerOnline.this.twitterStream = new TwitterStreamFactory().getInstance();
                TwitterRunnerOnline.this.twitterStream.addListener( listener );
                if (dumpListener != null) {
                	TwitterRunnerOnline.this.twitterStream.addListener(dumpListener);
                }
            	TwitterRunnerOnline.this.twitterStream.sample();
            }
        } );
        runningThread.start();
        this.runningThread2 = new Thread(new Runnable() {
        	public void run() {
        		ksession.fireUntilHalt();
        	}
        });
        // Starts to fire rules in Drools Fusion
        runningThread2.start();
        
        running = true;
    }

    public boolean isRunning() {
    	return running;
    }

    public void addAgendaEventListener(AgendaEventListener agendaListener) {
    	this.agendaListener = agendaListener;
    }
    
    public void addTwitterDumpListener(TwitterDumpListener dumpListener) {
    	this.dumpListener = dumpListener;
    }
    
    public void stop() {
    	runningThread.interrupt();
    	runningThread2.interrupt();
    	twitterStream.shutdown();
    	ksession.dispose();
    	running = false;
    }
    
    /**
     * Creates a Drools KnowledgeBase and adds the given rules file into it
     */
    private KieBase createKieBase( ) {
    	KieServices ks = KieServices.Factory.get();
    	KieFileSystem kfs = ks.newKieFileSystem();
    	kfs.write( "src/main/resources/rules.drl", ResourceFactory.newByteArrayResource(this.rulesContent.getBytes() ) );
    	// Parses and compiles the rules file
    	KieBuilder kbuilder = ks.newKieBuilder(kfs);
    	kbuilder.buildAll();
    	if (kbuilder.getResults().hasMessages(Message.Level.ERROR)) {
    		System.err.println(kbuilder.getResults());
    		throw new IllegalArgumentException("Problem constructing kiebase");
    	}

    	// Configures the Stream mode
    	KieBaseConfiguration conf = ks.newKieBaseConfiguration();
    	conf.setOption( EventProcessingOption.STREAM );
    	
    	
    	KieModule kmodule = kbuilder.getKieModule();
    	KieContainer kcontainer = ks.newKieContainer(kmodule.getReleaseId());
    	KieBase kbase = kcontainer.newKieBase(conf);
        
        return kbase;
    }

    /**
     * Creates a Drools Stateful Kie Session
     */
    private KieSession createKieSession( final KieBase kbase ) {
    	KieServices ks = KieServices.Factory.get();
        final KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption( ClockTypeOption.get( ClockType.REALTIME_CLOCK.getId() ) );
        final KieSession ksession = kbase.newKieSession(ksconf, null);
        if (this.agendaListener != null) {
        	ksession.addEventListener(agendaListener);
        }
        ksession.setGlobal("twitter", new MockTwitterService());//TwitterServiceImpl.getInstance()
        return ksession;
    }

}
