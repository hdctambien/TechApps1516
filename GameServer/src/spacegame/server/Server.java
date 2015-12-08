package spacegame.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
//import java.net.SocketTimeoutException;
import java.util.*;

import spacegame.util.Config;
import spacegame.util.ConfigParseException;

public class Server implements Runnable{
	private static int port = 8080; //0xFACE;
	volatile boolean done;
	ServerSocket ss;
	List<Thread> serviceThreads;
	List<RequestForwarder> forwarders;
	List<ClientInfo> infos;
	RequestProcessor processor;
	Thread processorThread;
	SpacegameNetworkProtocol protocol;
	
	private static final String PROP_PORT = "port";
	
	private Config config;
	
	public Server(){
		serviceThreads = new ArrayList<Thread>();
		//ProtocolHandler echo = new EchoProtocol();
		protocol = new SpacegameNetworkProtocol();
		processor = new RequestProcessor(protocol);
		processorThread = new Thread(processor);
		
		forwarders = new ArrayList<RequestForwarder>();
		infos = new ArrayList<ClientInfo>();
		loadConfig();
		
	}
	
	public Config getConfig(){
		return config;
	}
	
	private void loadConfig(){
		System.out.println("Loading configuration data from file 'server.config'");
		boolean defaulted = false;
		config = new Config("server");
		try{
			config.loadConfig();
		}catch(ConfigParseException ce){
			System.out.println("An error occurred while parsing the Config file:");
			ce.printStackTrace();
			defaulted = true;
		}
		if(config.hasInt(PROP_PORT)){
			port = config.getInt(PROP_PORT);
		}else{
			defaulted = true;
			config.put(PROP_PORT, port);
		}
		
		if(defaulted){
			System.out.println("Default values were be loaded because config was missing values.");
			System.out.println("Config will be saved to include all required values for next launch.");
			try {
				config.saveConfig();
			} catch (IOException e) {
				System.out.println("Error occurred while saving Config:");
				e.printStackTrace();
			}
		}
		System.out.println("Config values for this session are as follows:");
		config.printAll();
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
				Socket client = ss.accept(); System.out.print("Accepted a Client!");
				ClientInfo info = new ClientInfo(client);
				System.out.println(" Client was assigned UID "+info.getUID());
				RequestForwarder forwarder = new RequestForwarder(info, processor);
				forwarder.start();
				serviceThreads.add(forwarder.getThread());
				forwarders.add(forwarder);
				infos.add(info);
			} catch(SocketException se){
				System.out.println("Server Socket closed!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop(){
		done = true;
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		protocol.getGameState().stopUpdater();
		System.out.println("Map update loop terminated.");
		if(!forwarders.isEmpty()){
			for(RequestForwarder forwarder: forwarders){
				forwarder.tryTerminate();
			}
		}
		System.out.println("Server has Shutdown...");
	}
	
	public void removeClient(RequestForwarder forwarder){
		forwarders.remove(forwarder);
		serviceThreads.remove(forwarder.getThread());
		infos.remove(forwarder.getClientInfo());
	}
	
	public ClientInfo[] getAllClientInfo(){
		return infos.toArray(new ClientInfo[infos.size()]);
	}
	
}
