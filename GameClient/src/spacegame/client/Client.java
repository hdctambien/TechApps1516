package spacegame.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class connects to a server at the specified IP Address and port. Create a thread for the client so that
 * it will run its read loop. Whenever the read loop gets a message from the server, it transfers it to an 
 * internal queue. The messages are made available through the methods 'boolean hasMessage()' and 
 * 'String getNextMessage()'. You can also send messages back to the server using the client's method
 * 'void sendMessage(String message)'
 * @author Caleb Wilson
 */
public class Client implements Runnable{
	
	private InetAddress iaddress;
	private Socket s;
	private BufferedReader reader;
	private PrintWriter writer;
	private Queue<String> messageQueue;
	private int port;
	
	private volatile boolean done;
	
	/**
	 * Creates a new Client object that connects to the server at the given IP Address and port. Once the client
	 * is created it should be put in a thread object to start its read loop with code like this 
	 * 'Thread t = new Thread(client);' and 't.start();'
	 * @param address the IP Address or host name of the server
	 * @param port the port of the server
	 * @throws IOException if the socket created is unable to connect to the server
	 */
	public Client(String address, int port) throws IOException{
		iaddress = InetAddress.getByName(address);
		this.port = port;
		s = new Socket(iaddress, port);
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		writer = new PrintWriter(s.getOutputStream());
		messageQueue = new LinkedBlockingQueue<String>();
	}
	
	/**
	 * A method that returns the IP Address of the server that the client is connected to
	 * @return the IP Address of the server that the client is connected to
	 */
	public InetAddress getAddress(){
		return iaddress;
	}
	
	/**
	 * A method that returns the port on the server that the client is connected to
	 * @return the port on the server that the client is connected to
	 */
	public int getPort(){
		return port;
	}
	
	/**
	 * This method sends the given message to the server that the client is connected to
	 * @param message the message String to be sent to the server
	 */
	public void sendMessage(String message){
		writer.println(message);
		writer.flush();
	}
	/**
	 * This method returns true if the Client's message Queue has a message from the server and false if it 
	 * does not contain any messages
	 * @return whether or not the Client's message Queue has a message available
	 */
	public synchronized boolean hasMessage(){
		return !messageQueue.isEmpty();
	}
	/**
	 * This method returns the next message from the Client's message queue, if available.
	 * If there is no available message, the String returned will be null.
	 * @return the next message in the Client's queue from the server
	 */
	public synchronized String getNextMessage(){
		return messageQueue.poll();
	}
	private synchronized void addMessage(String message){
		messageQueue.add(message);
	}

	/**
	 * This method should be called by a Thread object. It loops indefinitely until the method 'stop()' is
	 * called or until the client receives a command by the server to stop reading (then the server closes the
	 * connection).
	 * This loop reads line by line from the server and every line it reads is added as a String to the client's
	 * internal message queue. These messages can be accessed via the methods 'boolean hasMessage()' and
	 * 'String getNextMessage()'
	 */
	@Override
	public void run() {
		while(!done){
			try {
				String message = reader.readLine();
				addMessage(message);
				if(message.equals("TERM")){
					//This indicates server is closing connection
					done = true;
				}else if(message.equals("exit")){
					//This indicates that the server would like to close the connection
					done = true;
					sendMessage("TERM");
					Thread.sleep(100);//Give time for the server read thread to close
					s.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	/**
	 * This method stops the client read thread that is looping to receive messages from the server. 
	 * Call this method when you would like to stop reading from the server.
	 * Please note that the client itself may end its read loop if it is told by the server to exit.
	 */
	public void stop(){
		done = true;
	}

}
