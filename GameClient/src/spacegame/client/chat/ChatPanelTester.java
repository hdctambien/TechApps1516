package spacegame.client.chat;

import java.awt.BorderLayout;
import javax.swing.JFrame;


public class ChatPanelTester {

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new JFrame("GUITest");
				frame.setSize(700, 400);
				ChatPanel chat = new ChatPanel(600,350);
				frame.add(chat,BorderLayout.SOUTH);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}	
}
