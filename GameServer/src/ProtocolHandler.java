import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public abstract class ProtocolHandler implements Runnable {
	
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	public ProtocolHandler(Socket client) throws IOException{
		this.client = client;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
	}
	
	public void run(){
		serviceClient();
	}
	
	abstract void serviceClient();
	
}
