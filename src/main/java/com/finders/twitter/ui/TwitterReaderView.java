package com.finders.twitter.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TwitterReaderView extends JFrame {

	private static final long serialVersionUID = 20140121L;
	
	private JComboBox<String> mode;
	private JFileChooser ruleFile;
	private JButton useRulesButton;
	private JButton findButton;
	private JButton startButton;
	private JButton stopButton;
	private JTextArea allStatuses;
	private JScrollPane allStatusesPane;
	private JTextArea ruleContent;
	private JTextArea ruleOutput;
	private JScrollPane ruleOutputPane;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JTextField rulefileName;
	
	public TwitterReaderView(TwitterReaderModel model) {
		super("Twitter CEP reader");
		initialize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		getContentPane().setLayout(new BorderLayout());
		this.leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(getMainControlsPanel(), BorderLayout.NORTH);
		leftPanel.add(getRuleOutputPanel(), BorderLayout.CENTER);
		this.rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(getRuleContentHeaderPanel(), BorderLayout.NORTH);
		rightPanel.add(getRuleContentPanel(), BorderLayout.CENTER);
		this.mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(leftPanel, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(getStatusUpdatesPanel(), BorderLayout.SOUTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(50, 50, ((int) screenSize.getWidth()) - 100, ((int) screenSize.getHeight()) - 100);
	}
	
	private JScrollPane getRuleOutputPanel() {
		this.ruleOutput = new JTextArea(15, 30);
		this.ruleOutputPane = new JScrollPane(ruleOutput,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return ruleOutputPane;
	}

	private JScrollPane getRuleContentPanel() {
		this.ruleContent = new JTextArea(15, 30);
		JScrollPane scrollPane = new JScrollPane(ruleContent,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

	private JPanel getStatusUpdatesPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("All status updates"), BorderLayout.NORTH);
		this.allStatuses = new JTextArea(10, 80);
		this.allStatusesPane = new JScrollPane(allStatuses,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(allStatusesPane, BorderLayout.CENTER);
		return panel;
	}

	public JTextArea getAllStatuses() {
		return allStatuses;
	}
	
	public JComboBox<String> getMode() {
		return mode;
	}
	
	public JButton getUseRulesButton() {
		return useRulesButton;
	}
	
	public JButton getFindButton() {
		return findButton;
	}
	
	public JFileChooser getRuleFile() {
		return ruleFile;
	}
	
	public JTextField getRulefileName() {
		return rulefileName;
	}

	public JButton getStartButton() {
		return this.startButton;
	}
	
	public JButton getStopButton() {
		return stopButton;
	}
	
	private JPanel getRuleContentHeaderPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("Rules content:"));
		this.useRulesButton = new JButton("Implement");
		panel.add(this.useRulesButton);
		this.startButton = new JButton("START");
		panel.add(startButton);
		this.stopButton = new JButton("STOP");
		panel.add(stopButton);
		return panel;
	}
	
	private JPanel getMainControlsPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel leftPanel = new JPanel(new GridLayout(1,2));
		leftPanel.add(new JLabel("Mode:"));
		this.mode = new JComboBox<String>();
		this.mode.addItem("Online");
		this.mode.addItem("Offline");
		this.mode.setSelectedItem("Online");
		leftPanel.add(this.mode);
		JPanel rightPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rightPanel1.add(new JLabel("Rule file:"));
		this.rulefileName = new JTextField(10);
		rulefileName.setEditable(false);
		rightPanel1.add(rulefileName);
		this.findButton = new JButton("Select file");
		String rulePath = "";
		try {
			rulePath = getClass().getResource("/rules").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.ruleFile = new JFileChooser(rulePath);
		rightPanel1.add(findButton);
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel1);
		return mainPanel;
	}

	public JTextArea getRuleContent() {
		return ruleContent;
	}
	
	public JTextArea getRuleOutput() {
		return ruleOutput;
	}

	public JScrollPane getAllStatusesPane() {
		return allStatusesPane;
	}
	
	public JScrollPane getRuleOutputPane() {
		return ruleOutputPane;
	}
}
