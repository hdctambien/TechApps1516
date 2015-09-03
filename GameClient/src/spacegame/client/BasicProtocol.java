package spacegame.client;
import java.io.PrintWriter;
import java.net.InetAddress;

public class BasicProtocol extends AbstractProtocol {
	
	private InetAddress iaddress;
	private int port;
	
	public BasicProtocol(Client client){
		super(client);
		iaddress = client.getAddress();
		port = client.getPort();
	}
	
	@Override
	public void process(String command) {
		if(command.equals("TERM")){
			client.sendMessage("exit");//This tells the server read thread to close before the server closes the connection
		}
		System.out.println("[Server@ "+iaddress.getHostAddress()+":"+port+"]: "+command);		
	}

}
