package spacegame.client;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.map.GameMap;
import spacegame.map.MapEvent;
import spacegame.test.ImageLoaderTest;
import spacegame.util.Config;
import spacegame.util.ConfigParseException;

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
	
	public static String SHIP_NAME = "Ship.1";
	
	private static int PORT;// = 8080;//0xFACE;
	private static String ipaddress;
	private static volatile boolean done;
	private static Thread clientThread;
	private static Thread protocolThread;
	private static Thread updaterThread;
	private static ProtocolAggregator aggregator;
	private static GameMap map;
	private static ClientUpdater updater;
	private static Config config;
	private static String name;
	
	private static final String CONFIG_FILE_NAME = "client";//.config added by Config class
	private static final String PROP_NAME = "name";
	private static final String PROP_PORT = "port";
	private static final String PROP_ADDRESS = "address";
	//private static final String PROP_
	
	/**
	 * This is the main method that launches the basic DebuggerClient. It creates a Client and a BasicProtocol,
	 * along with the Threads to run them.
	 * @param args the arguments given to java.exe on the command line (ignored)
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to DebuggerClient!");
		loadConfig(in);

		if(name.equals("IMG")){
			ImageLoaderTest.main(args);
			return;
		}
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
			
			switch(name){
				case "ChatTester"://Test the chat panel
					SwingUtilities.invokeLater(new GUIRunner(client,aggregator,ChatPanel.GUI_CHAT));
					break;
				case "PanelTester":
					SwingUtilities.invokeLater(new GUIRunner(client,aggregator,ChatPanel.GUI_COMMAND));
					break;
			}
			
			enterProtocolLoop(in, client, aggregator);			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Now Exiting...");
		in.nextLine();
	}

	private static void loadConfig(Scanner in) {
		boolean defaulted=false;
		System.out.println("Loading Config file 'client.config'...");
		config = new Config(CONFIG_FILE_NAME);
		try {
			config.loadConfig();
			printConfig();
		} catch (ConfigParseException e1) {
			//e1.printStackTrace();
			System.out.println("An Error occured while reading the config file, the user will be"+
			"prompted for all required input values");
			defaulted = true;
		}		
		if(config.hasString(PROP_NAME)){
			name = config.getString(PROP_NAME);
		}else{
			System.out.print("Please enter your name: ");
			name = in.nextLine();
			config.put(PROP_NAME,name);
			defaulted = true;
		}
		if(config.hasString(PROP_ADDRESS)){
			ipaddress = config.getString(PROP_ADDRESS);
		}else{
			System.out.print("Please enter server IP address: ");
			ipaddress = in.nextLine();
			config.put(PROP_ADDRESS,ipaddress);
			defaulted = true;
		}
		if(config.hasInt(PROP_PORT)){
			PORT = config.getInt(PROP_PORT);
		}else{
			System.out.print("Please enter server port (probably 8080): ");
			PORT = in.nextInt();
			in.nextLine();//push cursor down
			config.put(PROP_PORT,PORT);
			defaulted = true;
		}
		if(defaulted){
			System.out.println("Thank you for your user input! These values will be saved into a"+
		"config file called 'client.config' and loaded upon future startups of this client.");
			try {
				config.saveConfig();
			} catch (IOException e) {
				System.out.println("An Error occurred while trying to save the config file:");
				e.printStackTrace();
			}
		}
	}
	
	public static void printConfig(){
		System.out.println("ALL properties:");
		for(Map.Entry<String, String> entry: config.getAll().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
	}
	public static void printConfigStratified(){
		System.out.println("STRING properties:");
		for(Map.Entry<String, String> entry: config.getStrings().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("INT properties:");
		for(Map.Entry<String, Integer> entry: config.getInts().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("DOUBLE properties:");
		for(Map.Entry<String, Double> entry: config.getDoubles().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("BOOL properties:");
		for(Map.Entry<String, Boolean> entry: config.getBools().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
	}
	public static void printConfigDetailed(){
		printConfig();
		printConfigStratified();
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
	public static Config getConfig(){
		return config;
	}
	
}
class GUIRunner implements Runnable{
	
	private Client client;
	private ProtocolAggregator aggregator;
	private volatile int guiType;
	
	public GUIRunner(Client c, ProtocolAggregator pa, int type){
		client = c;
		aggregator = pa;
		guiType = type;
	}
	
	public void run(){
		JFrame frame = new JFrame("ChatTest");
		frame.setSize(700, 400);
		ChatPanel chat = new ChatPanel(600,350,guiType);
		frame.add(chat,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ChatProtocol protocol = new ChatProtocol(client,chat,guiType);
		chat.addChatListener(protocol);
		aggregator.addProtocol(protocol);
	}	
}