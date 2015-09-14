package spacegame.server;
import java.io.PrintWriter;

public class EchoProtocol implements ProtocolHandler {
	
	public void processRequest(Request r){
		String msg = r.getMessage();
		PrintWriter writer = r.getClientInfo().getPrintWriter();
		writer.println("echo "+msg);
		writer.flush();
	}

}