package spacegame.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable{
	
	private InetAddress iaddress;
	private Socket s;
	private BufferedReader reader;
	private PrintWriter writer;
	private Queue<String> messageQueue;
	private int port;
	
	private volatile boolean done;
	
	public Client(String address, int port) throws IOException{
		iaddress = InetAddress.getByName(address);
		this.port = port;
		s = new Socket(iaddress, port);
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		writer = new PrintWriter(s.getOutputStream());
		messageQueue = new LinkedBlockingQueue<String>();
	}
	
	protected PrintWriter getPrintWriter(){
		return writer;
	}
	
	public InetAddress getAddress(){
		return iaddress;
	}
	
	public int getPort(){
		return port;
	}
	
	//TODO: Implement these methods!!!
	public void sendMessage(String message){
		writer.println(message);
		writer.flush();
	}
	public synchronized boolean hasMessage(){
		return !messageQueue.isEmpty();
	}
	public synchronized String getNextMessage(){
		return messageQueue.poll();
	}
	private synchronized void addMessage(String message){
		messageQueue.add(message);
	}

	@Override
	public void run() {
		while(!done){
			try {
				String message = reader.readLine();
				addMessage(message);
				if(message.equals("TERM")){
					//This indicates server is closing connection
					done = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	public void stop(){
		done = true;
	}

}
