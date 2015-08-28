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
	
	public Server(){
		serviceThreads = new ArrayList<Thread>();
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!done){
			
			try {
				Socket client = ss.accept(); System.out.println("Accepted a Client!");
				BasicProtocol protocol = new BasicProtocol(client);
				Thread t = new Thread(protocol);
				t.start();
				serviceThreads.add(t);				
				/*System.out.println("[Client]: "+in.readLine());
				out.println("OK"); out.flush(); System.out.println("[Server]: OK");
				client.close(); System.out.println("Closing Client Connection");*/
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	
}
