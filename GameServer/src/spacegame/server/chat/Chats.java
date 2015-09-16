package spacegame.server.chat;

import java.util.*;

/**
 * A Chats object holds all ChatMessage objects for the server, with methods for adding and getting chats.
 * @author Caleb Wilson
 */
public class Chats {

	ArrayList<ChatMessage> chats;
	//TODO: add permissions
	
	/**
	 * Instantiates a new Chats object for storing chats.
	 */
	public Chats(){
		chats = new ArrayList<ChatMessage>();
		chats.add(new ChatMessage("ChatsManager","Server",0,
				"Welcome to the Chat System! Make sure to effectively communicate to your comrades!"));
	}
	
	/**
	 * Add a ChatMessage
	 * @param message the ChatMessage
	 */
	public void addChat(ChatMessage message){
		chats.add(message);
	}
	
	/**
	 * Add a chat message. This method creates a ChatMessage object with the information given
	 * and adds it to the Chats.
	 * @param name The name of the chatter
	 * @param job The job of the chatter
	 * @param uid The Unique Identifier of the chatter's ClientInfo
	 * @param message The chat message
	 */
	public void addChat(String name, String job, int uid, String message){
		chats.add(new ChatMessage(name,job,uid,message));
	}
	
	/**
	 * This method gives the chat message at the requested index. Chats are stored chronologically.
	 * @param index the index of the chat
	 * @return the chat at the specified index, as a ChatMessage
	 */
	public ChatMessage getChatMessage(int index){
		return chats.get(index);
	}
	
	/**
	 * This method gives the size of the underlying list that holds the ChatMessage s
	 * @return the size of the Chat list, as an int
	 */
	public int getChatListSize(){
		return chats.size();
	}
	
}
