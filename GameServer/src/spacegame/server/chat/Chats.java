package spacegame.server.chat;

import java.util.*;

public class Chats {

	ArrayList<ChatMessage> chats;
	//TODO: add permissions
	
	public Chats(){
		chats = new ArrayList<ChatMessage>();
		chats.add(new ChatMessage("ChatsManager","Server",0,
				"Welcome to the Chat System! Make sure to effectively communicate to your comrades!"));
	}
	
	public void addChat(ChatMessage message){
		chats.add(message);
	}
	
	public void addChat(String name, String job, int uid, String message){
		chats.add(new ChatMessage(name,job,uid,message));
	}
	
	public ChatMessage getChatMessage(int index){
		return chats.get(index);
	}
	
	public int getChatListSize(){
		return chats.size();
	}
	
}
