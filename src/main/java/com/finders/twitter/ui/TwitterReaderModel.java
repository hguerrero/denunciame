package com.finders.twitter.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.kie.api.event.rule.AgendaEventListener;

import com.finders.twitter.commands.TwitterRunner;
import com.finders.twitter.commands.TwitterRunnerOffline;
import com.finders.twitter.commands.TwitterRunnerOnline;
import com.finders.twitter.model.TwitterDumpListener;

public class TwitterReaderModel {

    static {
        // disable twitter4j log
        System.setProperty( "twitter4j.loggerFactory", "twitter4j.internal.logging.NullLoggerFactory" );
        System.setProperty( "drools.dialect.mvel.strict", "false" );
    }
    
    private Map<String, Class<? extends TwitterRunner>> modes = new HashMap<String, Class<? extends TwitterRunner>>();
    
    private TwitterRunner currentRun = null;
    private String currentMode;
    private String currentRuleContent = null;

	private AgendaEventListener agendaListener;
	private TwitterDumpListener dumpListener;
    
    public TwitterReaderModel() {
		modes.put("Online", TwitterRunnerOnline.class);
		modes.put("Offline", TwitterRunnerOffline.class);
	}
    
	public synchronized void switchMode(String mode) throws Exception {
		System.out.println("switchMode: " + mode);
		this.currentMode = mode;
	}
	
	public void start() throws Exception {
		restart(modes.get(currentMode));
	}
	
	public String selectRuleFile(File file) throws IOException {
		this.currentRuleContent = FileUtils.readFileToString(file);
		return currentRuleContent;
	}

	public void updateRules(String drlContent) throws Exception {
		System.out.println("updateRules : " + drlContent);
		this.currentRuleContent = drlContent;
		if (this.currentRun != null) {
			restart(this.currentRun.getClass());
		}
	}

	private void restart(Class<? extends TwitterRunner> runnerClass) throws Exception {
		stop();
		Constructor<? extends TwitterRunner> cons = runnerClass.getConstructor(String.class);
		if (currentRuleContent != null) {
			this.currentRun = cons.newInstance(currentRuleContent);
			this.currentRun.addAgendaEventListener(agendaListener);
			this.currentRun.addTwitterDumpListener(dumpListener);
			this.currentRun.go();
		}
	}

	public void stop() {
		if (this.currentRun != null) {
			this.currentRun.stop();
			this.currentRun = null;
		}
	}
	
	public void registerRuleOutputListener(AgendaEventListener listener) {
		this.agendaListener = listener;
	}
	
	public void registerTwitterDumpListener(TwitterDumpListener listener) {
		this.dumpListener = listener;
	}
}
