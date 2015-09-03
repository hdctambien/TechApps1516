package spacegame.server;
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
				PrintWriter writer = r.getClientInfo().getPrintWriter();
				writer.println("TERM");//This indicates to the client reader thread to stop reading
				writer.flush();
				Thread.sleep(100);//Give time to the client reader thread to receive message
				r.getClientInfo().getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		PrintWriter writer = r.getClientInfo().getPrintWriter();
		writer.println("echo "+msg);
		writer.flush();
	}

}