package spacegame.client.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements ActionListener {

	private JTextField chatField;
	private JTextArea outputBox;
	private JScrollPane scroller;
	
	public static final int W_PADDING = 10;
	public static final int TXT_FIELD_HEIGHT = 20;
	public static final int H_PADDING = 15+TXT_FIELD_HEIGHT;
	
	private LinkedList<ChatListener> chatListeners;
	
	public ChatPanel(int width, int height){
		chatListeners = new LinkedList<ChatListener>();
		
		chatField = new JTextField();
		outputBox = new JTextArea(width-W_PADDING,height-H_PADDING);
		scroller = new JScrollPane(outputBox);
		
		
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatField.setPreferredSize(new Dimension(width-W_PADDING,TXT_FIELD_HEIGHT));
		scroller.setPreferredSize(new Dimension(width-W_PADDING,height-H_PADDING));
		outputBox.setEditable(false);
		outputBox.setLineWrap(true);
		chatField.addActionListener(this);
		add(scroller,BorderLayout.NORTH);
		add(chatField,BorderLayout.SOUTH);
		setPreferredSize(new Dimension(width,height));
	}

	public void addChatListener(ChatListener listener){
		chatListeners.add(listener);
	}
	
	public void removeChatListener(ChatListener listener){
		chatListeners.remove(listener);
	}
	
	private void fireChatEvent(ChatEvent e){
		for(ChatListener listener: chatListeners){
			listener.chatRecieved(e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = chatField.getText();
		addChat("[YOU] #"+text);
		ChatEvent chat = new ChatEvent(text);
		fireChatEvent(chat);
		chatField.setText("");
	}
	
	public void addChat(String message){
		outputBox.setText(outputBox.getText()+message+"\n");
	}
	
}
