package spacegame.server;
import java.io.PrintWriter;

/**
 * A basic implementation of a ProtocolHandler that echoes all client requests, appending "echo " to the front of
 * their request
 * @author Caleb Wilson
 */
public class EchoProtocol implements ProtocolHandler {
	
	/**
	 * Takes the client request and echoes it back to the client, prepending "echo " to the request. This method
	 * is a basic implementation of ProtocolHandler.processRequest(Request r)
	 */
	public void processRequest(Request r){
		String msg = r.getMessage();
		PrintWriter writer = r.getClientInfo().getPrintWriter();
		writer.println("echo "+msg);
		writer.flush();
	}

}