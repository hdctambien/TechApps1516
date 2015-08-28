import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main {
	private static boolean done = false;
	public static void main(String[] args) {
		Server server = new Server();
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
		while(!done){
			System.out.print("\\> ");
			if(in.nextLine().equals("exit")){
				done = true;
				//System.exit(0);
			}
		}
		server.done = true;
		Thread.yield();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
