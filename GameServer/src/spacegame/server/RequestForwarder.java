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
					try {
						PrintWriter writer = info.getPrintWriter();
						writer.println("TERM");//This indicates to the client reader thread to stop reading
						writer.flush();
						Main.server.removeClient(this);//remove this from the server's list of active clients
						Thread.sleep(100);//Give time to the client reader thread to receive message
						info.getSocket().close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Client (UID:"+info.getUID()+") Connection closing...");
				}else if(message.equals("TERM")){
					done = true;
					//I don't remove this forwarder from the server list right now because this is closing from a server
					//request and: 
					//     1) the server is currently enumerating through its list of clients, removal would cause an exception
					//     2) the server is going to shutdown after this and not use its lists again. The server is responsible
					//      for deleting its lists after enumerating or just waiting for garbage collection to do it.
					System.out.println("Client (UID:"+info.getUID()+") Connection closing...");
				}else{
					processor.addRequest(new Request(message, info));
				}				
			}
		}catch(IOException e){
			System.out.println("IO Error occurred in RequestForwarder read thread!");
			e.printStackTrace();
		}
		
	}

	public Thread getThread(){
		return readThread;
	}
	
	public void tryTerminate(){//Blocks until readthread terminates
		PrintWriter p = info.getPrintWriter();
		p.println("exit");
		p.flush();
		while(!done){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ClientInfo getClientInfo(){
		return info;
	}
	
}
