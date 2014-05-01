package com.finders.twitter;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.finders.twitter.ui.TwitterReaderController;
import com.finders.twitter.ui.TwitterReaderModel;
import com.finders.twitter.ui.TwitterReaderView;

public class TwitterReaderApp {

	public static void main(String[] args) throws Exception {
		initLNF();
		TwitterReaderModel model = new TwitterReaderModel();
		TwitterReaderView view = new TwitterReaderView(model);
		new TwitterReaderController(model, view);
		view.setVisible(true);
	}
	
	private static void initLNF() throws Exception {
		try {
			boolean gtkExists = false;
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("GTK+".equals(info.getName())) {
		        	gtkExists = true;
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    if (!gtkExists) {
		    	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			e.printStackTrace();
		}
	}
}
