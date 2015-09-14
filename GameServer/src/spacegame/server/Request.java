package spacegame.server;

public class Request {
	private String message;
	private ClientInfo cInfo;
	
	public Request(String msg, ClientInfo cinfo){
		message = msg;
		cInfo = cinfo;
	}
	public String getMessage(){
		return message;
	}
	public ClientInfo getClientInfo(){
		return cInfo;
	}
	public void reply(String prefix){
		cInfo.sendMessage(prefix+" "+message);
	}
}
