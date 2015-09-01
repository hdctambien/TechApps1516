import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable{
	private static int port = 8080; //0xFACE;
	volatile boolean done;
	ServerSocket ss;
	List<Thread> serviceThreads;
	RequestProcessor processor;
	Thread processorThread;
	
	public Server(){
		serviceThreads = new ArrayList<Thread>();
		processor = new RequestProcessor(new BasicProtocol());
		processorThread = new Thread(processor);
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		processorThread.start();
		
		while(!done){
			
			try {
				Socket client = ss.accept(); System.out.println("Accepted a Client!");
				ClientInfo info = new ClientInfo(client);
				RequestForwarder forwarder = new RequestForwarder(info, processor);
				forwarder.start();
				serviceThreads.add(forwarder.getThread());				
				/*System.out.println("[Client]: "+in.readLine());
				out.println("OK"); out.flush(); System.out.println("[Server]: OK");
				client.close(); System.out.println("Closing Client Connection");*/
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
