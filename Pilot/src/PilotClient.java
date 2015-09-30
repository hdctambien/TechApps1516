import java.io.IOException;
import java.util.Scanner;
import spacegame.client.*;

public class PilotClient {
	public static final int PORT = 8080;
	
	protected static Thread clientThread;
	protected static Thread protocolThread;
	
	private static Client client;
	
	public static void setup()
	{
		Scanner input = new Scanner( System.in );
		System.out.println("What is the IP?");
		String ipaddress = input.nextLine();
		
		try{
			client = new Client(ipaddress, PORT);
			PilotProtocol protocol = new PilotProtocol(client);
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(protocol);
			protocolThread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static Client getClient()
	{
		return client;
	}
	public static void sendMessage(String message)
	{
		client.sendMessage(message);
	}
	public static void exit(){
		client.sendMessage("exit");
	}
	public static void setHeading(double rocketHeading)
	{
		client.sendMessage("set rocketHeading "+rocketHeading);
	}
	public static void subscribe()
	{
		client.sendMessage("subscribe heading");
		client.sendMessage("subscribe velocityX");
		client.sendMessage("subscribe velocityY");
		client.sendMessage("subscribe posX");
		client.sendMessage("subscribe posY");
	}
}
