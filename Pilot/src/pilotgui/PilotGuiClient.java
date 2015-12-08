package pilotgui;
import java.io.IOException;
import java.util.Scanner;

import spacegame.client.*;
import spacegame.client.chat.ChatProtocol;
import spacegame.map.GameMap;

public class PilotGuiClient {
	public static final int PORT = 8080;
	
	protected static Thread clientThread;
	protected static Thread protocolThread;
	public static ProtocolAggregator aggregator;
	public static ClientUpdater updater;
	
	private static Client client;
	
	public static GameMap setup(String shipname)
	{
		Scanner input = new Scanner( System.in );
		System.out.println("What is the IP?");
		String ipaddress = input.nextLine();
		GameMap map;
		try{
			client = new Client(ipaddress, PORT);
			PilotGuiProtocol protocol = new PilotGuiProtocol(client);
			SerialProtocol serial = new SerialProtocol(client);
			aggregator = new ProtocolAggregator(client);
			aggregator.addProtocol(protocol);
			aggregator.addProtocol(serial);
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			client.sendMessage("set name DEFAULT");
			client.sendMessage("set job Pilot");
			
			client.sendMessage("get map");
			System.out.println("Getting Map...");
			while(!serial.hasSerial()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Building Map...");
			System.out.println("HAS SERIAL: "+serial.hasSerial());
			map = serial.getMapFromSerial();
			System.out.println("Map obtained!");
			
			updater = new ClientUpdater(map);
			MapUpdateProtocol update = new MapUpdateProtocol(client, updater, shipname, serial);
			aggregator.addProtocol(update);
			Thread updaterThread = new Thread(updater);
			updaterThread.start();
			
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		return map;
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
		System.out.println("Added Chat Protocol");
		aggregator.addProtocol(p);
	}
	public ProtocolAggregator getAgg()
	{
		return aggregator;
	}
	public ClientUpdater getUpdater()
	{
		return updater;
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
}
