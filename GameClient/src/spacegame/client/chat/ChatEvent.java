package spacegame.client.chat;

public class ChatEvent {

	private String chatMessage;
	
	public ChatEvent(String chat){
		chatMessage = chat;
	}
	
	public String getChat(){
		return chatMessage;
	}
	
}
