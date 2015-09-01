import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BasicProtocol implements ProtocolHandler {
	
	public BasicProtocol(){
		
	}
	
	public void processRequest(Request r){
		String msg = r.getMessage();
		if(msg.equals("exit")){
			try {
				r.getClientInfo().getSocket().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		PrintWriter writer = r.getClientInfo().getPrintWriter();
		writer.println("echo "+msg);
		writer.flush();
	}

}