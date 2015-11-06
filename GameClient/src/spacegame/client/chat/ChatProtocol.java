package spacegame.client.chat;

import java.net.InetAddress;

import spacegame.GlobalFlags;
import spacegame.client.AbstractProtocol;
import spacegame.client.Client;
import static spacegame.client.chat.ChatPanel.GUI_COMMAND;
import static spacegame.client.chat.ChatPanel.GUI_CHAT;

public class ChatProtocol extends AbstractProtocol implements ChatListener {

	private ChatPanel panel;
	private volatile int guiType;
	private InetAddress iaddress;
	private int port;
	
	public ChatProtocol(Client client, ChatPanel chatPanel){
		this(client,chatPanel,GUI_CHAT);
	}
	
	public ChatProtocol(Client client, ChatPanel chatPanel, int type) {
		super(client);
		panel = chatPanel;
		guiType = type;
		iaddress = client.getAddress();
		port = client.getPort();
	}

	@Override
	public void process(String command) {
		if(guiType==GUI_COMMAND){
			if(!GlobalFlags.FILTER_SERIAL_MAPS||!(command.contains("serial")||(command.contains("map")&&command.contains("push")))){
				//Filter map pushes
				panel.addChat("[Server@ "+ iaddress.getHostAddress()+":"+port+"]: "+command);
			}			
		}else if(command.startsWith("chat")){
			String[] pieces = command.split(" ",2);
			if(pieces.length>1){
				panel.addChat(pieces[1]);
			}
		}

	}

	@Override
	public void chatRecieved(ChatEvent chat) {
		if(guiType==GUI_COMMAND){
			sendMessage(chat.getChat());
		}else if(chat.getChat().startsWith("~")){
			sendMessage(chat.getChat().substring(1));
		}else{
		sendMessage("#"+chat.getChat());
		}
	}

}
