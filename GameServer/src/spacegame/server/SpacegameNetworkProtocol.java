package spacegame.server;

import java.io.IOException;

import spacegame.GlobalFlags;
import spacegame.server.chat.ChatMessage;
import spacegame.server.chat.Chats;
import spacegame.util.Config;
import spacegame.util.ConfigParseException;
import spacegame.map.*;

public class SpacegameNetworkProtocol implements ProtocolHandler {

	public static final int ERR_CMD_FORMAT = 1;
	public static final int ERR_PARSE_VAL = 2;
	public static final int ERR_NOT_SUBSCRIBED = 3;
	public static final int ERR_IN_OPERATION = 4;
	
	private Chats chats;
	private GameState gameState;
	private StateUpdateHandler stateUpdateHandler;
	
	public SpacegameNetworkProtocol(){
		chats = new Chats();
		gameState = new GameState();
		stateUpdateHandler = new StateUpdateHandler();
		gameState.addGameStateListener(stateUpdateHandler);
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
				case "mapset":
					doMapSet(msg,words,info,r);
					break;
				case "push":
					doPush(msg, words, info, r);
					break;
				case "subscribe":
					doSubscribe(msg,words,info,r);
					break;
				case "unsubscribe":
					doUnsubscribe(msg,words,info,r);
				case "testSerial":
					testSerial(info);
					break;
				case "shoot":
					doShoot(msg,words,info,r);
					break;
				case "asteroid":
					doAsteroid(msg,words,info,r);
					break;
				case "config":
					doConfig(msg,words,info,r);
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
				case "noop": //NO OPERATION
					break;
				case "tdp"://Toggle Debug Physics
					GlobalFlags.DEBUG_PHYSICS = true;
					GlobalFlags.DEBUG_HEADING_GET_DOUBLE = true;
					break;
				default:
					r.reply("UNK");
					break;
			}
		}
	}

	public void doShoot(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<4){
			r.error(ERR_CMD_FORMAT);
		}else{
			String ship = words[1];
			if(gameState.getMap().hasEntityWithName(ship)){
				try{
					double x = Double.parseDouble(words[2]);
					double y = Double.parseDouble(words[3]);
					Vector2 v = Vector2.rect(x,y);
					//TODO: put position and vector info to check collision
					broadcast(info, msg);
				}catch(NumberFormatException e){
					r.error(ERR_PARSE_VAL);
				}
			}else{
				r.error(ERR_IN_OPERATION);
			}
		}
	}
	
	public void doConfig(String msg, String[] words, ClientInfo info, Request r){
		Config config = Main.getConfig();
		if(words.length<2){
			r.error(ERR_CMD_FORMAT);
		}else{
			if(words[1].equals("save")){
				try {
					config.saveConfig();
					r.reply("OK");
				} catch (IOException e) {
					e.printStackTrace();
					r.error(ERR_IN_OPERATION);
				}
			}else if(words[1].equals("load")){
				try{
					config.loadConfig();
					r.reply("OK");
					//TODO: reload config values into where they are used!
				}catch(ConfigParseException ce){
					ce.printStackTrace();
					r.error(ERR_IN_OPERATION);
				}
			}else if(words[1].equals("version")){
				info.sendMessage("config version="+Config.VERSION);
			}else if(words.length<3){
				r.error(ERR_CMD_FORMAT);
			}else if(words[1].equals("get")&&config.hasAny(words[2])){
				info.sendMessage("config ret "+words[2]+" "+config.getString(words[2],true));
			}else if(words.length<4){
				r.error(ERR_CMD_FORMAT);
			}else if(words[1].equals("put")||words[1].equals("set")){
				config.parsePut(words[2], words[3]);
				r.reply("OK");
			}
		}		
	}
	
	public void doAsteroid(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<3){
			r.error(ERR_CMD_FORMAT);
		}else{
			try{
				double x = Double.parseDouble(words[1]);
				double y = Double.parseDouble(words[2]);
				EntityFactory factory = gameState.getFactory();
				Entity asteroid = factory.createAsteroid(x,y);
				gameState.getUpdater().addEntity(asteroid);
				r.reply("OK");
			}catch(NumberFormatException e){
				r.error(ERR_PARSE_VAL);
			}
		}
	}
	
	public void doGet(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.error(ERR_CMD_FORMAT);
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
				case "id":
					info.sendMessage("set id "+info.getUID());
					break;
				case "ship":
					info.sendMessage("set ship "+info.getShip());
					break;
				case "map":
					info.sendMessage(gameState.getMap().serialize());
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
			r.error(ERR_CMD_FORMAT);
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
				case "ship":
					info.setShip(words[2]);
					r.reply("OK");
					break;
				default:
					gameState.doSet(words[1],words[2],info,r);
					//r.reply("UNK");
					break;
			}			
		}
	}
	
	public void doMapSet(String msg, String[] words, ClientInfo info, Request r) {
		if(words.length<4){
			r.error(ERR_CMD_FORMAT);
		}else{
			gameState.doMapSet(words[1],words[2],words[3],info,r);	
		}		
	}
	
	public void doPush(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.error(ERR_CMD_FORMAT);
		}else{
			broadcast(info,msg);
		}
	}
	
	public void broadcast(ClientInfo info, String msg){
		ClientInfo[] allClients = Main.server.getAllClientInfo();
		for(ClientInfo client: allClients){
			if(client.equals(info)){
				//they don't need their own message
			}else{
				client.sendMessage(msg);
			}
		}
	}
	
	public void doSubscribe(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.error(ERR_CMD_FORMAT);
		}else{
			stateUpdateHandler.addSubscriber(words[1], info);
			r.reply("OK");
		}
	}
	
	public void doUnsubscribe(String msg, String[] words, ClientInfo info, Request r){
		if(words.length<2){
			r.error(ERR_CMD_FORMAT);
		}else{
			if(stateUpdateHandler.removeSubscriber(words[1],info)){
				r.reply("OK");
			}else{
				r.error(ERR_NOT_SUBSCRIBED);
			}
		}
	}

	public GameState getGameState(){
		return gameState;
	}
	
	public void testSerial(ClientInfo info){
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		map.addEntity(factory.createShip());
		map.addEntity(factory.createAsteroid());
		info.sendMessage(map.serialize());
	}
}
