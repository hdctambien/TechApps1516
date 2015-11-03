package spacegame.client;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.map.GameMap;
import spacegame.map.MapEvent;
import spacegame.test.ImageLoaderTest;

/**
 * This class is a launcher for a basic, command line, client that can be used to debug the server and protocol
 * errors. It takes command line input from a user and sends it directly to the server. Messages received from the
 * server are displayed on the command line for the user to see. This debugger client uses the BasicProtocol
 * implementation of AbtractProtocol. This class along with the BasicProtocol class is an example of how to use 
 * this basic networking code of the package 'spacegame.client' to write your own client protocols to interact
 * with a server of similar or direct implementation of 'spacegame.server'
 */
public class DebuggerClient {
	
	public static final float VERSION = 1.2f;
	
	public static final String SHIP_NAME = "Ship.1";
	
	private static int PORT = 8080;//0xFACE;
	private static String ipaddress;
	private static volatile boolean done;
	private static Thread clientThread;
	private static Thread protocolThread;
	private static Thread updaterThread;
	private static ProtocolAggregator aggregator;
	private static GameMap map;
	private static ClientUpdater updater;
	
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
		if(name.equals("IMG")){
			ImageLoaderTest.main(args);
			return;
		}
		System.out.print("Please enter server IP address: ");
		ipaddress = in.nextLine();
		try{
			Client client = new Client(ipaddress, PORT);
			BasicProtocol basic = new BasicProtocol(client);
			SerialProtocol serial = new SerialProtocol(client);
			aggregator = new ProtocolAggregator(client);
			aggregator.addProtocol(basic);
			aggregator.addProtocol(serial);
			
			clientThread = new Thread(client);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			//Basic name and job setup
			client.sendMessage("set name "+name);			
			client.sendMessage("set job DebuggerClient");
			client.sendMessage("set ship "+SHIP_NAME);
			
			constructMap(serial, client);
			updater = new ClientUpdater(map);
			MapUpdateProtocol update = new MapUpdateProtocol(client, updater, SHIP_NAME, serial);
			aggregator.addProtocol(update);
			updaterThread = new Thread(updater);
			
			if(name.equals("ChatTester")){//Test the chat panel
				javax.swing.SwingUtilities.invokeLater(new ChatGUIRunner(client,aggregator));
			}
			
			enterProtocolLoop(in, client, aggregator);			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Now Exiting...");
		in.nextLine();
	}
	
	public static void constructMap(SerialProtocol serial, Client client){
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
			
			if(message.startsWith("set")){
				doSet(message);
			}
			else if(message.startsWith("mapset")){
				doMapSet(message);
			}
			
			if(message.equals("exit")){
				done = true;
				client.stop();
				protocol.stop();
				updater.stopUpdating();
			}else if(client.isExit()){
				done = true;
				protocol.stop();
				updater.stopUpdating();
			}
		}
	}
	private static void doSet(String message){
		String[] pieces = message.split(" ");
		if(pieces.length>=3){
			MapEvent event = new MapEvent(SHIP_NAME,map.getIndexByName(SHIP_NAME),pieces[1],pieces[2]);
			updater.addIOAction(event);
		}
	}
	private static void doMapSet(String message){
		String[] pieces = message.split(" ");
		if(pieces.length>=4){
			MapEvent event = new MapEvent(pieces[1],map.getIndexByName(pieces[1]),pieces[2],pieces[3]);
			updater.addIOAction(event);
		}
	}
	
}
class ChatGUIRunner implements Runnable{
	
	private Client client;
	private ProtocolAggregator aggregator;
	
	public ChatGUIRunner(Client c, ProtocolAggregator pa){
		client = c;
		aggregator = pa;
	}
	
	public void run(){
		JFrame frame = new JFrame("ChatTest");
		frame.setSize(700, 400);
		ChatPanel chat = new ChatPanel(600,350);
		frame.add(chat,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ChatProtocol protocol = new ChatProtocol(client,chat);
		chat.addChatListener(protocol);
		aggregator.addProtocol(protocol);
		
	}
	
}