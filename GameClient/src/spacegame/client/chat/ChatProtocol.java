package spacegame.client.chat;

import spacegame.client.AbstractProtocol;
import spacegame.client.Client;

public class ChatProtocol extends AbstractProtocol implements ChatListener {

	private ChatPanel panel;
	
	public ChatProtocol(Client client, ChatPanel chatPanel) {
		super(client);
		panel = chatPanel;
	}

	@Override
	public void process(String command) {
		if(command.startsWith("chat")){
			String[] pieces = command.split(" ",2);
			if(pieces.length>1){
				panel.addChat(pieces[1]);
			}
		}

	}

	@Override
	public void chatRecieved(ChatEvent chat) {
		sendMessage("#"+chat.getChat());
	}

}
