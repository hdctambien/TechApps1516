package spacegame.server;
import java.io.*;
import java.net.Socket;

/**
 * A class that continuously reads from a client socket and passes one line messages from the client to a
 * RequestProcessor as Request objects. Don't forget to start() it!
 * @author Caleb Wilson
 */
public class RequestForwarder implements Runnable{

	private volatile boolean done;
	private ClientInfo info;
	private BufferedReader reader;
	private Socket socket;
	private RequestProcessor processor;
	private Thread readThread;
	
	/**
	 * Create a new RequestForwarder that reads messages from the given client and feeds them into the given
	 * RequestProcessor. The RequestForwarder will attempt to create a read stream from the client's socket.
	 * The RequestForwarder will not start reading until you tell it to start(). a tryTerminate() method is also
	 * available to terminate the read thread and client connection.
	 * @param info the information about the client
	 * @param rp the RequestProcessor to feed to
	 * @throws IOException If it can't create read streams from the client socket
	 */
	public RequestForwarder(ClientInfo info, RequestProcessor rp) throws IOException{
		this.info = info;
		socket = info.getSocket();
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		processor = rp;
		readThread = new Thread(this);
	}
	
	/**
	 * Start the read Thread for the forwarder
	 */
	public void start(){
		readThread.start();
	}

	/**
	 * Do not call this method directly. It is called by the read thread to loop reading operations
	 */
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

	/**
	 * Get the reading Thread of this object. Please be courteous and tell the forwarder to tryTerminate()
	 * instead of getting its Thread and calling interupt() or something else that would forcibly close the
	 * thread
	 * @return
	 */
	public Thread getThread(){
		return readThread;
	}
	
	/**
	 * A method that tells the client it is time to close the connection and coordinates to terminate the
	 * connection and read thread gracefully. This method will block until such has been accomplished.
	 */
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
	
	/**
	 * Get the ClientInfo associated with this forwarder.
	 * @return the ClientInfo of the client this forwarder is interacting with
	 */
	public ClientInfo getClientInfo(){
		return info;
	}
	
}
