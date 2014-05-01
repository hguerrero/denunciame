package com.finders.twitter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;

import twitter4j.Status;

import com.finders.twitter.model.TwitterDumpListener;

public class TwitterReaderController {

	private final TwitterReaderView view;
	private final TwitterReaderModel model;

	public TwitterReaderController(TwitterReaderModel model,
			TwitterReaderView view) throws Exception {
		this.view = view;
		this.model = model;
		initView();
		initModel();
	}
	
	private void initModel() throws Exception {
		this.model.registerRuleOutputListener(new DefaultAgendaEventListener() {
			@Override
			public void afterMatchFired(AfterMatchFiredEvent event) {
				String ruleName = event.getMatch().getRule().getName();
				List<Object> firers = event.getMatch().getObjects();
				StringBuilder sb = new StringBuilder();
				sb.append("Rule fired: ").append(ruleName).append('\n');
				int index = 1;
				for (Object firer : firers) {
					if (firer instanceof Status) {
						Status stat = (Status) firer;
						sb.append(" - Status involved ").append(index).append(": ").
							append(MessageFormat.format( "@{0} - {1}",
                                    stat.getUser().getScreenName(),
                                    stat.getText() )).append('\n');
					}
				}
				view.getRuleOutput().append(sb.toString());
				view.getRuleOutput().repaint();
				view.getRuleOutput().setCaretPosition(view.getRuleOutput().getText().length());
				/*view.getRuleOutputPane().scrollRectToVisible(
						view.getRuleOutputPane().getBounds());*/
			}
		});
		this.model.registerTwitterDumpListener(new TwitterDumpListener() {
			public void onStatus(Status status) {
				view.getAllStatuses().append("\n" + MessageFormat.format( "[{0,time,full}] @{1} - {2}",
                        status.getCreatedAt(),
                        status.getUser().getScreenName(),
                        status.getText() ));
				view.getAllStatuses().repaint();
				view.getAllStatuses().setCaretPosition(view.getAllStatuses().getText().length());
				/*view.getAllStatusesPane().scrollRectToVisible(
						view.getAllStatusesPane().getBounds());*/
			}
		});
		this.model.switchMode("Online");
	}
	
	private void initView() {
		this.view.getMode().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					String item = (String) evt.getItem();
					try {
						model.switchMode(item);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(view, "Problem switching to " + item + " mode: " + e.getMessage());
					}
				}
			}
		});
		this.view.getUseRulesButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					model.updateRules(view.getRuleContent().getText());
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(view, "Problem updating rules: " + e.getMessage());
				}
			}
		});
		this.view.getStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					String ruleContent = view.getRuleContent().getText();
					String item = (String) view.getMode().getSelectedItem();
					model.updateRules(ruleContent);
					model.switchMode(item);
					model.start();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(view, "Problem starting engine: " + e.getMessage());
				}
			}
		});
		this.view.getStopButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.stop();
			}
		});

		this.view.getFindButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (view.getRuleFile().showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
					File file = view.getRuleFile().getSelectedFile();
					try {
						String fileName = file.getName();
						String content = model.selectRuleFile(file);
						view.getRuleContent().setText(content);
						view.getRulefileName().setText(fileName);
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(view, "Problem loading file: " + e.getMessage());
					}
				}
			}
		});
	}

}
