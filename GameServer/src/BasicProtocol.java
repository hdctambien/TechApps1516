import java.io.IOException;
import java.net.Socket;

//hi caleb
public class BasicProtocol extends ProtocolHandler implements Runnable {
	
	boolean done = false;
	
	public BasicProtocol(Socket client) throws IOException{
		super(client);
	}
	
	@Override
	void serviceClient() {
		try{
			while(!done){
				String message = in.readLine();
				System.out.println("[Client]: "+message);
				if(message.equals("exit")){
					done = true;
				}else{
					out.println("echo "+message); out.flush();
					System.out.println("[Server]: echo "+message);
				}
			}
			System.out.println("Closing client connection...");
			client.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
