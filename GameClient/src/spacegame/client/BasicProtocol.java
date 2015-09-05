package spacegame.client;
import java.net.InetAddress;

/**
 * This is a Basic implementation of AbstractProtocol that prints messages recieved from the server to the
 * standard output (System.out). It also contains functionality for cleanly terminating the connection with the
 * server.
 * @author Caleb Wilson
 */
public class BasicProtocol extends AbstractProtocol {
	
	private InetAddress iaddress;
	private int port;
	
	/**
	 * Creates a BasicProtocol object to handle messages received by the Client.
	 *  In order to start this protocol listening to the client for messages,
	 * you need to create and start a Thread for this protocol with the code 'Thread t = new Thread(protocol);'
	 * and 't.start();'.
	 * @param client a spacegame.client.Client object that this protocol binds to
	 */
	public BasicProtocol(Client client){
		super(client);
		iaddress = client.getAddress();
		port = client.getPort();
	}
	
	/**
	 * This implements the abstract method process from AbstractProtocol. Messages received from the client will
	 * be displayed on the Standard Output (System.out).
	 */
	@Override
	public void process(String command) {
		System.out.println("[Server@ "+iaddress.getHostAddress()+":"+port+"]: "+command);		
	}

}
