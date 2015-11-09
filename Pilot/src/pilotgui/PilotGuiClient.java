package pilotgui;
import java.io.IOException;
import java.util.Scanner;

import spacegame.client.*;
import spacegame.client.chat.ChatProtocol;

public class PilotGuiClient {
	public static final int PORT = 8080;
	
	protected static Thread clientThread;
	protected static Thread protocolThread;
	public static ProtocolAggregator aggregator;
	
	private static Client client;
	
	public static void setup()
	{
		Scanner input = new Scanner( System.in );
		System.out.println("What is the IP?");
		String ipaddress = input.nextLine();
		
		try{
			client = new Client(ipaddress, PORT);
			PilotGuiProtocol protocol = new PilotGuiProtocol(client);
			aggregator = new ProtocolAggregator(client);
			aggregator.addProtocol(protocol);
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			client.sendMessage("set name DEFAULT");
			client.sendMessage("set job Pilot");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static Client getClient()
	{
		return client;
	}
	public ProtocolAggregator getAggregator()
	{
		return aggregator;
	}
	public void addProtocol(PilotGuiProtocol p)
	{
		aggregator.addProtocol(p);
	}
	public void addProtocol(ChatProtocol p)
	{
		aggregator.addProtocol(p);
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
