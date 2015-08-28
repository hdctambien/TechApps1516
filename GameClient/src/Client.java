import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	private static int PORT = 8080;//0xFACE;
	private static String ipaddress;
	
	private int port;
	private InetAddress iaddress;
	private Socket s;
	private BufferedReader reader;
	private PrintWriter writer;
	private boolean done;
	
	public Client(String address, int port) throws IOException{
		iaddress = InetAddress.getByName(address);
		this.port = port;
		s = new Socket(iaddress, port);
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		writer = new PrintWriter(s.getOutputStream());
		done = false;
	}
	
	public void enterProtocolLoop(Scanner in) throws IOException{
		while(!done){
			System.out.print("<BasicClient\\> ");
			String message = in.nextLine();
			writer.println(message); writer.flush();
			if(message.equals("exit")){
				done = true;
			}else{
				String response = reader.readLine();
				System.out.println("[Server@ "+iaddress.getHostAddress()+":"+port+"]: "+response);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter server IP address: ");
		ipaddress = in.nextLine();
		//System.out.println("Type the message you would like to send to the server...");
		try{
			Client client = new Client(ipaddress, PORT);			
			client.enterProtocolLoop(in);			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Now Exiting...");
		in.nextLine();
	}

}
