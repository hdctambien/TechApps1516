import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInfo {

	private Socket client;
	private OutputStream out;
	private PrintWriter writer;
	private String name;
	
	public ClientInfo(Socket s){
		client = s;
		try {
			out = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer = new PrintWriter(out);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String nm){
		name = nm;
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
	
}
