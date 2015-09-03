package spacegame.server;
import java.io.*;
import java.net.Socket;

public class RequestForwarder implements Runnable{

	private volatile boolean done;
	private ClientInfo info;
	private BufferedReader reader;
	private Socket socket;
	private RequestProcessor processor;
	private Thread readThread;
	
	public RequestForwarder(ClientInfo info, RequestProcessor rp) throws IOException{
		this.info = info;
		socket = info.getSocket();
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		processor = rp;
		readThread = new Thread(this);
	}
	
	public void start(){
		readThread.start();
	}

	@Override
	public void run() {
		done = false;
		try{
			while(!done){
				String message = reader.readLine();
				if(message.equals("exit")){
					done = true;
				}
				processor.addRequest(new Request(message, info));
			}
		}catch(IOException e){
			System.out.println("IO Error occurred in RequestForwarder read thread!");
			e.printStackTrace();
		}
		
	}

	public Thread getThread(){
		return readThread;
	}
	
}
