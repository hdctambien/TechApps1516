package spacegame.server;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import spacegame.util.Config;

/**
 * This is the Main class of the server in charge of running and setup. This class is the class that runs in the
 * GameServer.jar file. It also contains a version field for the version of the server's protocol.
 * @author Caleb Wilson
 */
public class Main {
	
	public static final float VERSION = 1.2f;
	
	private static boolean done = false;
	private static ClientInfo serverInfo;
	
	public volatile static Server server;
	
	/**
	 * Starts the server.
	 * @param args
	 */
	public static void main(String[] args) {
		server = new Server();
		Thread sthread = new Thread(server);
		sthread.start();
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Server Started with IP Address of "+InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" type 'exit' to quit.");
		serverInfo = ClientInfo.getServerTerminalClientInfo();
		server.infos.add(serverInfo);
		while(!done){
			System.out.print("\\> ");
			String cmd = in.nextLine();
			if(cmd.equals("exit")){
				done = true;
				//System.exit(0);
			}else if(cmd.equals("dps")){//Debug Physics setup
				server.processor.addRequest(new Request("set ship Ship.1",serverInfo));
				server.processor.addRequest(new Request("set heading 45",serverInfo));
				server.processor.addRequest(new Request("set powerFuel 1000",serverInfo));
				server.processor.addRequest(new Request("set throttle 100",serverInfo));
				server.processor.addRequest(new Request("tdp",serverInfo));
			}else{
				server.processor.addRequest(new Request(cmd,serverInfo));
			}
		}
		server.stop();
		in.nextLine();//lets the user see all server messages before the final closing.
		in.close();
		System.exit(0);
	}
	
	public static Server getServer(){
		return server;
	}
	public static Config getConfig(){
		return server.getConfig();
	}
}
