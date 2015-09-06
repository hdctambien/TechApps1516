package spacegame.server;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInfo {

	private static int lastUID = 0;
	private static ClientInfo serverInfo;
	
	private Socket client;
	private OutputStream out;
	private PrintWriter writer;
	private String name;
	private String job;
	private int uid;
	
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
	
	protected static ClientInfo getServerTerminalClientInfo(){
		if(serverInfo == null){//create the info if it hasn't been created yet
			serverInfo = new ClientInfo();
		}
		return serverInfo;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String nm){
		name = nm;
	}
	
	public String getJob(){
		return job;
	}
	
	public void setJob(String newJob){
		job = newJob;
	}
	
	public int getUID(){
		return uid;
	}
	
	public PrintWriter getPrintWriter(){
		return writer;
	}
	
	public OutputStream getOutputStream(){
		return out;
	}
	
	public Socket getSocket(){
		return client;
	}
	
	public void sendMessage(String message){
		writer.println(message);
		writer.flush();
	}
	
}
