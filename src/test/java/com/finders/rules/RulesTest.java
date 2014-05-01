package com.finders.rules;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.io.ResourceFactory;

import twitter4j.HashtagEntity;
import twitter4j.Status;

import com.finders.twitter.glob.TwitterService;

public class RulesTest {

	@Before
	public void setUp() {
		System.setProperty( "drools.dialect.mvel.strict", "false" );
	}
	
	@Test
	public void testPrintOutRules() throws Exception {
		KieSession ksession = createKieSession("rules/1-printOutRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getEntryPoint("twitter").insert(createStatus("george", "Hello", clock.getCurrentTime() + 1));
		ksession.getEntryPoint("twitter").insert(createStatus("mary", "Something", clock.getCurrentTime() + 2));
		clock.advanceTime(1, TimeUnit.SECONDS);
		int ruleExecCount = ksession.fireAllRules(new AgendaFilter() {
			public boolean accept(Match match) {
				if (!match.getRule().getName().equals("Dump tweets")) 
					throw new RuntimeException("Shouldn't fire this rule: " + match.getRule().getName());
				return true;
			}
		});
		Assert.assertEquals(2, ruleExecCount);
		ksession.dispose();
	}

	@Test
	public void testPatternRules() throws Exception {
		KieSession ksession = createKieSession("rules/2-patternRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getEntryPoint("twitter").insert(createStatus("george", "Hello", clock.getCurrentTime() + 1));
		ksession.getEntryPoint("twitter").insert(createStatus("mary", "Something", clock.getCurrentTime() + 2));
		ksession.getEntryPoint("twitter").insert(createStatus("mary", "Something different lol", clock.getCurrentTime() + 3));
		clock.advanceTime(1, TimeUnit.SECONDS);
		int ruleExecCount = ksession.fireAllRules(new AgendaFilter() {
			public boolean accept(Match match) {
				if (!match.getRule().getName().equals("Dump tweets from people laughing")) 
					throw new RuntimeException("Shouldn't fire this rule: " + match.getRule().getName());
				return true;
			}
		});
		Assert.assertEquals(1, ruleExecCount);
		ksession.dispose();
	}
	
	@Test
	public void testRegionalRules() throws Exception  {
		KieSession ksession = createKieSession("rules/3-regionalRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getEntryPoint("twitter").insert(createStatus("jorge", "Hola", clock.getCurrentTime() + 1, "AR"));
		ksession.getEntryPoint("twitter").insert(createStatus("george", "Hello", clock.getCurrentTime() + 2, "US"));
		clock.advanceTime(1, TimeUnit.SECONDS);
		int ruleExecCount = ksession.fireAllRules(new AgendaFilter() {
			public boolean accept(Match match) {
				if (!match.getRule().getName().equals("Dump tweets from US")) 
					throw new RuntimeException("Shouldn't fire this rule: " + match.getRule().getName());
				return true;
			}
		});
		Assert.assertEquals(1, ruleExecCount);
		ksession.dispose();
	}
	
	@Test
	public void testConversationRules() throws Exception {
		KieSession ksession = createKieSession("rules/4-conversationRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getEntryPoint("twitter").insert(createStatus("jorge", "Hola", clock.getCurrentTime() + 1));
		MockStatus status2 = (MockStatus) createStatus("george", "Hello", clock.getCurrentTime() + 3000);
		status2.setInReplyToScreenName("jorge");
		ksession.getEntryPoint("twitter").insert(status2);
		ksession.getEntryPoint("twitter").insert(createStatus("george", "How Are you?", clock.getCurrentTime() + 3000));
		clock.advanceTime(1, TimeUnit.SECONDS);
		int ruleExecCount = ksession.fireAllRules(new AgendaFilter() {
			public boolean accept(Match match) {
				if (!match.getRule().getName().equals("Dump tweets from user conversation")) 
					throw new RuntimeException("Shouldn't fire this rule: " + match.getRule().getName());
				return true;
			}
		});
		Assert.assertEquals(1, ruleExecCount);
		ksession.dispose();
	}
	
	@Test @Ignore("Only seems to work with realtime clocks")
	public void testCountRules() throws Exception {
		final KieSession ksession = createKieSession("rules/5-countRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getEntryPoint("twitter").insert(createStatus("jorge", "Hola", clock.getCurrentTime() + 1));
		MockStatus status2 = (MockStatus) createStatus("george", "Hello", clock.getCurrentTime() + 2);
		status2.setInReplyToScreenName("jorge");
		ksession.getEntryPoint("twitter").insert(status2);
		ksession.getEntryPoint("twitter").insert(createStatus("george", "How Are you?", clock.getCurrentTime() + 3));
		final AtomicInteger counter = new AtomicInteger(0);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				ksession.fireUntilHalt(new AgendaFilter() {
					public boolean accept(Match match) {
						synchronized (counter) {
							counter.incrementAndGet();
							if (!match.getRule().getName().equals("Count tweets in 10 seconds")) 
								throw new RuntimeException("Shouldn't fire this rule: " + match.getRule().getName());
							return true;
						}
					}
				});
			}
		});
		thread.start();
		clock.advanceTime(3, TimeUnit.SECONDS);
		counter.get();
		clock.advanceTime(3, TimeUnit.SECONDS);
		counter.get();
		clock.advanceTime(3, TimeUnit.SECONDS);
		counter.get();
		clock.advanceTime(3, TimeUnit.SECONDS);
		Thread.sleep(500);
		counter.get();
		Assert.assertEquals(1, counter.get());
		ksession.halt();
		ksession.dispose();
	}
	
	@Test
	public void testSplitByRetweetsRules() throws Exception {
		KieSession ksession = createKieSession("rules/6-splitByRetweetsRules.drl", null);
		SessionPseudoClock clock = ksession.getSessionClock();
		MockStatus status1 = (MockStatus) createStatus("jorge", "Hola", clock.getCurrentTime() + 1);
		MockStatus status2 = (MockStatus) createStatus("maria", "Que tal", clock.getCurrentTime() + 4);
		status2.setRetweetCount(1);
		MockStatus status3 = (MockStatus) createStatus("george", "Hello", clock.getCurrentTime() + 7);
		MockStatus status4 = (MockStatus) createStatus("mary", "How Are you?", clock.getCurrentTime() + 9);
		status4.setRetweetCount(2);
		ksession.getEntryPoint("twitter").insert(status1);
		ksession.getEntryPoint("twitter").insert(status2);
		ksession.getEntryPoint("twitter").insert(status3);
		ksession.getEntryPoint("twitter").insert(status4);
		clock.advanceTime(1, TimeUnit.SECONDS);
		Assert.assertEquals(0, ksession.getEntryPoint("retweeted").getFactCount());
		Assert.assertEquals(0, ksession.getEntryPoint("non-retweeted").getFactCount());
		int ruleExecCount = ksession.fireAllRules();
		Assert.assertEquals(5, ruleExecCount);
		Assert.assertEquals(2, ksession.getEntryPoint("retweeted").getFactCount());
		Assert.assertEquals(2, ksession.getEntryPoint("non-retweeted").getFactCount());
		ksession.dispose();
	}
	
	@Test
	public void testInferenceMedicRules() throws Exception {
		TwitterService mockService = EasyMock.createMock(TwitterService.class);
		KieSession ksession = createKieSession("rules/7-inferenceMedicRules.drl", mockService);
		SessionPseudoClock clock = ksession.getSessionClock();
		MockStatus status1 = (MockStatus) createStatus("george", "I have the flu", clock.getCurrentTime() + 1);
		MockStatus status2 = (MockStatus) createStatus("mary", "I sneezed too much today", clock.getCurrentTime() + 2);
		MockStatus status3 = (MockStatus) createStatus("sarah", "I sneezed too much today", clock.getCurrentTime() + 3);
		MockStatus status4 = (MockStatus) createStatus("john", "I sneezed too much today", clock.getCurrentTime() + 4);
		MockStatus status5 = (MockStatus) createStatus("kevin", "I sneezed too much today", clock.getCurrentTime() + 5);
		MockStatus status6 = (MockStatus) createStatus("thomas", "I sneezed too much today", clock.getCurrentTime() + 6);
		MockStatus status7 = (MockStatus) createStatus("tom", "I sneezed too much today", clock.getCurrentTime() + 7);
		MockStatus status8 = (MockStatus) createStatus("jerry", "I sneezed too much today", clock.getCurrentTime() + 8);
		MockStatus status9 = (MockStatus) createStatus("louis", "I sneezed too much today", clock.getCurrentTime() + 9);
		MockStatus status10 = (MockStatus) createStatus("natalie", "I sneezed too much today", clock.getCurrentTime() + 10);
		MockStatus status11 = (MockStatus) createStatus("george", "I sneezed too much today", clock.getCurrentTime() + 11);
		MockStatus status12 = (MockStatus) createStatus("george", "I sneezed too much today", clock.getCurrentTime() + 12);
		EntryPoint ep = ksession.getEntryPoint("twitter");
		ep.insert(status1);
		ep.insert(status2);
		ep.insert(status3);
		ep.insert(status4);
		ep.insert(status5);
		ep.insert(status6);
		ep.insert(status7);
		ep.insert(status8);
		ep.insert(status9);
		ep.insert(status10);
		ep.insert(status11);
		ep.insert(status12);
		mockService.tweetToUser(EasyMock.anyObject(Status.class), EasyMock.eq("Check hospitals near you"));
		EasyMock.expectLastCall().times(12);
		EasyMock.replay(mockService);
		
		ksession.fireAllRules();
		
		EasyMock.verify(mockService);
		
		ksession.dispose();
	}
	
	@Test
	public void testInferenceTrendRules() throws Exception {
		TwitterService mockService = EasyMock.createMock(TwitterService.class);
		KieSession ksession = createKieSession("rules/8-inferenceTrendRules.drl", mockService);
		SessionPseudoClock clock = ksession.getSessionClock();
		MockStatus status1 = (MockStatus) createStatus("george", "I like #claro", clock.getCurrentTime() + 1);
		MockStatus status2 = (MockStatus) createStatus("mary", "#claro is good", clock.getCurrentTime() + 2);
		MockStatus status3 = (MockStatus) createStatus("tim", "I like #movistar", clock.getCurrentTime() + 3);
		MockStatus status4 = (MockStatus) createStatus("tim", "I like #personal", clock.getCurrentTime() + 3);
		addHashtagEntity(status1, "#claro");
		addHashtagEntity(status2, "#claro");
		addHashtagEntity(status3, "#movistar");
		addHashtagEntity(status4, "#personal");
		EntryPoint ep = ksession.getEntryPoint("twitter");
		ep.insert(status1);
		ep.insert(status2);
		ep.insert(status3);
		final AtomicBoolean ruleFired = new AtomicBoolean(false);
		ksession.fireAllRules(new AgendaFilter() {
			public boolean accept(Match match) {
				String ruleName = "Bigger than everyone!!";
				if (match.getRule().getName().equals(ruleName)) {
					ruleFired.set(true);
				}
				return true;
			}
		});
		Assert.assertTrue(ruleFired.get());
		ksession.dispose();
	}
	
	private void addHashtagEntity(MockStatus status, String tag) {
		HashtagEntity entity = new MockHashtagEntity(tag, status.getText().indexOf(tag), status.getText().indexOf(tag) + tag.length());
		HashtagEntity[] entities = new HashtagEntity[] { entity };
		status.setHashtagEntities(entities);
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
	
    private KieSession createKieSession( String ruleFile, TwitterService service ) {
    	KieServices ks = KieServices.Factory.get();
    	KieFileSystem kfs = ks.newKieFileSystem();
    	kfs.write( "src/main/resources/rules.drl", ResourceFactory.newClassPathResource( ruleFile ) );
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

    	final KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption( ClockTypeOption.get( ClockType.PSEUDO_CLOCK.getId() ) );
        final KieSession ksession = kbase.newKieSession(ksconf, null);
        ksession.setGlobal("twitter", service);
        return ksession;
    }

}
