package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public abstract class JCustomWindow extends JDialog {

	public JCustomWindow(String title) {
		super(Program.clientWindow, title, true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					Program.SendMessage("QUIT");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
