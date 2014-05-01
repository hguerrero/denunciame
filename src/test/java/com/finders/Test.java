package com.finders;

import java.util.Date;

import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;

import twitter4j.Status;

import com.finders.rules.MockPlace;
import com.finders.rules.MockStatus;
import com.finders.rules.MockUser;
import com.finders.twitter.glob.MockTwitterService;


public class Test {

	@org.junit.Test
	public void testKSesison() {
		KieServices ks = KieServices.Factory.get();
    	KieFileSystem kfs = ks.newKieFileSystem();
    	kfs.write( "src/main/resources/rules.drl", ResourceFactory.newClassPathResource("rules/crimeRules.drl") );
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
		KieSession ksession = createKieSession(kbase);
		ksession.getEntryPoint("twitter").insert(createStatus("usr1", "Hello robbing", 1, "US"));
		ksession.getEntryPoint("twitter").insert(createStatus("usr1", "Hello robbing", 1, "US"));
		ksession.getEntryPoint("twitter").insert(createStatus("usr1", "Hello robbing", 1, "US"));
		ksession.getEntryPoint("twitter").insert(createStatus("usr1", "Hello robbing", 1, "US"));
		ksession.fireAllRules();
	}
	
	 private Status createStatus(String user, String message, long timestamp, String countryCode) {
         MockStatus status = (MockStatus) createStatus(user, message, timestamp);
         MockPlace place = new MockPlace();
         place.setCountryCode(countryCode);
         status.setPlace(place);
         return status;
	 }

	 private Status createStatus(String user, String message, long timestamp) {
         MockStatus status = new MockStatus();
         status.setText(message);
         MockUser usr = new MockUser();
         usr.setScreenName(user);
         status.setUser(usr);
         status.setCreatedAt(new Date(timestamp));
         return status;
	 }

	
    private KieSession createKieSession( final KieBase kbase ) {
    	KieServices ks = KieServices.Factory.get();
        final KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption( ClockTypeOption.get( ClockType.REALTIME_CLOCK.getId() ) );
        final KieSession ksession = kbase.newKieSession(ksconf, null);
        ksession.setGlobal("twitter", new MockTwitterService());//TwitterServiceImpl.getInstance()
        return ksession;
    }
}
