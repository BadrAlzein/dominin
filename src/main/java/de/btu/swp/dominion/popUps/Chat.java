package de.btu.swp.dominion.popUps;

import javax.swing.*;

public class Chat {

	JFrame newFrame = new JFrame("Chat");
	JButton sendMessage;
	JTextArea textbox;
	JTextField messagebox;

	public void visibel() {
		newFrame.setVisible(true);
		
		messagebox = new JTextField(30);
		textbox = new JTextArea();
		sendMessage = new JButton("Send Message");
		
		newFrame.add(sendMessage);
		
		newFrame.setSize(1000,1000);
		
	}
}