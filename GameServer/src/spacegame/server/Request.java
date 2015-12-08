package spacegame.server;

/**
 * This class represents a one-line, String request from a client. It is created by a RequestForwarder object
 * that is listening to the client and handed to a RequestProcessor queue. The RequestProcessor loops over the
 * queue, taking requests from every client and giving them to a ProtocolHandler to process one-by-one in the
 * order they were received. A request contains the message of the client, as well as some information about the
 * client and means to send a reply back to the client. The ClientInfo of the request also contains functionality
 * to send messages back to the client
 * @author Caleb Wilson
 */
public class Request {
	private String message;
	private ClientInfo cInfo;
	
	/**
	 * Creates a new Request object given the message of the client and information about the client. The
	 * ClientInfo object also contains a method to send messages back to the client.
	 * @param msg the one line message sent by the client
	 * @param cinfo an object that stores basic information about the client
	 */
	public Request(String msg, ClientInfo cinfo){
		message = msg;
		cInfo = cinfo;
	}
	
	/**
	 * Returns the one line message sent by the client
	 * @return a String message
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * Returns the ClientInfo of the client that sent the message. See ClientInfo for more information about what
	 * you can do with this information.
	 * @return the ClientInfo about the client
	 */
	public ClientInfo getClientInfo(){
		return cInfo;
	}
	
	/**
	 * Prepends the given prefix message to the original client message and sends it back to the client. Useful
	 * for giving back ERR, OK, or UNK messages to the client. Note that you can send directly what you want to
	 * the client by using ClientInfo.sendMessage(String message)
	 * @param prefix the reply prefix (a String)
	 */
	public void reply(String prefix){
		cInfo.sendMessage(prefix+" "+message);
	}
	
	public void error(int code){
		reply("ERR "+code);
	}
}
