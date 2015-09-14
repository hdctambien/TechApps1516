package spacegame.server;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class holds information about a client that is connected to the server, and also some functionality to
 * send messages to the client.
 * @author Caleb Wilson
 */
public class ClientInfo {

	private static int lastUID = 0;
	private static ClientInfo serverInfo;
	
	private Socket client;
	private OutputStream out;
	private PrintWriter writer;
	private String name;
	private String job;
	private int uid;
	
	/**
	 * This constructor creates a new client based on a socket obtained on the Server from ServerSocket.accept()
	 * @param s The socket of the connected client
	 */
	public ClientInfo(Socket s){
		client = s;
		try {
			out = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer = new PrintWriter(out);
		uid = ++lastUID;
	}
	private ClientInfo(){
		//Creates a client info representing the server main prompt so I can execute those commands onto the 
		//SpacegameNetworkProtocol locally instead of writing two command processors
		client = null;
		out = System.out;
		uid = 0;
		name = "Server";
		job = "MainTerminal";
		writer = new PrintWriter(out);		
	}
	
	/**
	 * This is a method that returns a ClientInfo representing the server terminal. This is used by the server
	 * to feed its own terminal directly into the Protocol that talks to all the client, simulating a client for
	 * the server operator.
	 * @return the ClientInfo object representing the server terminal
	 */
	protected static ClientInfo getServerTerminalClientInfo(){
		if(serverInfo == null){//create the info if it hasn't been created yet
			serverInfo = new ClientInfo();
		}
		return serverInfo;
	}
	
	/**
	 * This method returns the name of the client represented by this object
	 * @return name of the client as a String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method sets the name of the client represented by this object
	 */
	public void setName(String nm){
		name = nm;
	}
	
	/**
	 * This method returns the job of the client represented by this object
	 * @return the job of the client as a String
	 */
	public String getJob(){
		return job;
	}
	
	/**
	 * This method sets the job of the client represented by this object
	 */
	public void setJob(String newJob){
		job = newJob;
	}
	
	/**
	 * This method returns the Unique Identifier (UID) assigned to the client when this object was created. 
	 * UIDs are assigned incrementally starting at 1 (UID of 0 is reserved for the server terminal).
	 * @return the UID of the client
	 */
	public int getUID(){
		return uid;
	}
	
	/**
	 * This method returns a PrintWriter connected to the client. Don't forget to flush() !!!!!
	 * @return PrintWriter to client
	 */
	public PrintWriter getPrintWriter(){
		return writer;
	}
	
	/**
	 * This method returns an OutputStream connected to the client
	 * @return OutputStream to client
	 */
	public OutputStream getOutputStream(){
		return out;
	}
	
	/**
	 * This method returns the socket that is connected to the client that the object represents.
	 * @return the socket connected to the client
	 */
	public Socket getSocket(){
		return client;
	}
	
	/**
	 * This method sends the given message to the client it is representing.
	 * @param message
	 */
	public void sendMessage(String message){
		writer.println(message);
		writer.flush();
		System.out.println("(UID:"+uid+") Sent Message: "+message);
	}
	
}
