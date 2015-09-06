package spacegame.server;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
	
	public static final float VERSION = 1.0f;
	
	private static boolean done = false;
	private static ClientInfo serverInfo;
	
	public static Server server;
	
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
			}else{
				server.processor.addRequest(new Request(cmd,serverInfo));
			}
		}
		server.stop();
		in.nextLine();//lets the user see all server messages before the final closing.
		in.close();
		System.exit(0);
	}

}
