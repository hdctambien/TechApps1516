import java.io.IOException;
import java.util.Scanner;

public class DebuggerClient {
	
	private static int PORT = 8080;//0xFACE;
	private static String ipaddress;
	private static volatile boolean done;
	private static Thread clientThread;
	private static Thread protocolThread;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter server IP address: ");
		ipaddress = in.nextLine();
		try{
			Client client = new Client(ipaddress, PORT);
			BasicProtocol basic = new BasicProtocol(client);
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(basic);
			protocolThread.start();
			enterProtocolLoop(in, client, basic);			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Now Exiting...");
		in.nextLine();
	}
	
	public static void enterProtocolLoop(Scanner in, Client client, AbstractProtocol protocol) throws IOException{
		done = false;
		while(!done){
			System.out.print("<BasicClient\\> ");
			String message = in.nextLine();
			client.sendMessage(message);
			System.out.println(":::Message sent to server!");
			if(message.equals("exit")){
				done = true;
				client.stop();
				protocol.stop();
			}
		}
		
	}
	
}
