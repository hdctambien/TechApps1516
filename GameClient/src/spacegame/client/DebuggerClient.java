package spacegame.client;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is a launcher for a basic, command line, client that can be used to debug the server and protocol
 * errors. It takes command line input from a user and sends it directly to the server. Messages received from the
 * server are displayed on the command line for the user to see. This debugger client uses the BasicProtocol
 * implementation of AbtractProtocol. This class along with the BasicProtocol class is an example of how to use 
 * this basic networking code of the package 'spacegame.client' to write your own client protocols to interact
 * with a server of similar or direct implementation of 'spacegame.server'
 */
public class DebuggerClient {
	
	public static final float VERSION = 1.0f;
	
	private static int PORT = 8080;//0xFACE;
	private static String ipaddress;
	private static volatile boolean done;
	private static Thread clientThread;
	private static Thread protocolThread;
	
	/**
	 * This is the main method that launches the basic DebuggerClient. It creates a Client and a BasicProtocol,
	 * along with the Threads to run them.
	 * @param args the arguments given to java.exe on the command line (ignored)
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to DebuggerClient!");
		System.out.print("Please enter your name: ");
		String name = in.nextLine();
		System.out.print("Please enter server IP address: ");
		ipaddress = in.nextLine();
		try{
			Client client = new Client(ipaddress, PORT);
			BasicProtocol basic = new BasicProtocol(client);
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(basic);
			protocolThread.start();
			
			//Basic name and job setup
			client.sendMessage("set name "+name);			
			client.sendMessage("set job DebuggerClient");
			
			enterProtocolLoop(in, client, basic);			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Now Exiting...");
		in.nextLine();
	}
	
	/**
	 * This method loops to take command line input from a user and send it to the server
	 * @param in a Scanner for taking input on the command line
	 * @param client a spacegame.client.Client object that interfaces with the server
	 * @param protocol the protocol bound to the client
	 * @throws IOException
	 */
	public static void enterProtocolLoop(Scanner in, Client client, AbstractProtocol protocol) throws IOException{
		done = false;
		while(!done){
			//System.out.print("<BasicClient\\> ");
			String message = in.nextLine();
			client.sendMessage(message);
			System.out.println(":::Message sent to server!");
			
			if(message.equals("exit")){
				done = true;
				client.stop();
				protocol.stop();
			}else if(client.isExit()){
				done = true;
				protocol.stop();		
			}
		}
	}
}