package spacegame.server;

import spacegame.server.chat.ChatMessage;
import spacegame.server.chat.Chats;

public class SpacegameNetworkProtocol implements ProtocolHandler {

	public static final int ERR_CMD_FORMAT = 1;
	public static final int ERR_PARSE_VAL = 2;
	
	private Chats chats;
	private GameState gameState;
	
	public SpacegameNetworkProtocol(){
		chats = new Chats();
		gameState = new GameState();
	}
	
	@Override
	public void processRequest(Request r) {
		String msg = r.getMessage();
		ClientInfo info = r.getClientInfo();
		if(msg.length() > 0 && msg.charAt(0) == '#'){//It's a chat!
			ChatMessage chat = new ChatMessage(info.getName(),info.getJob(),info.getUID(),msg.substring(1, msg.length()));
			chats.addChat(chat);
			ClientInfo[] allClients = Main.server.getAllClientInfo();
			for(ClientInfo client: allClients){
				if(client.equals(info)){
					//they don't need their own chat message
				}else{
					client.sendMessage("chat ["+chat.getJob()+":"+chat.getName()+" @ "
							+chat.getTimeString()+"] #"+chat.getMessage());
				}
			}
		}else{
			String[] words = msg.split(" ");
			switch(words[0]){
				case "get":
					doGet(msg, words, info, r);
					break;
				case "set":
					doSet(msg, words, info, r);
					break;
				case "push":
					doPush(msg, words, info, r);
					break;
				case "OK":
					//maybe I will want something here in the future...
					break;
				case "ERR":
					//I will probably want something here too..
					break;
				case "UNK":
					//And maybe something here...
					break;
				default:
					r.reply("UNK");
					break;
			}
		}
	}
	
	public void doGet(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.reply("ERR "+ERR_CMD_FORMAT);
		}else{
			switch(words[1]){
				case "version":
					info.sendMessage("set version "+Main.VERSION);
					break;
				case "name":
					info.sendMessage("set name "+info.getName());
					break;
				case "job":
					info.sendMessage("set job "+info.getJob());
					break;
				default:
					gameState.doGet(words[1],info,r);
					//r.reply("UNK");
					break;	
			}
		}
	}
	
	public void doSet(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<3){
			r.reply("ERR "+ERR_CMD_FORMAT);
		}else{
			switch(words[1]){
				case "name":
					info.setName(words[2]);
					r.reply("OK");
					break;
				case "job":
					info.setJob(words[2]);
					r.reply("OK");
					break;
				default:
					gameState.doSet(words[1],words[2],info,r);
					//r.reply("UNK");
					break;
			}
		}
	}
	
	public void doPush(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.reply("ERR "+ERR_CMD_FORMAT);
		}else{
			ClientInfo[] allClients = Main.server.getAllClientInfo();
			for(ClientInfo client: allClients){
				if(client.equals(info)){
					//they don't need their own push message
				}else{
					client.sendMessage(msg);
				}
			}
		}
	}

	public GameState getGameState(){
		return gameState;
	}
}