package spacegame.client;
/**
 * An abstract object representing a client-side protocol. This protocol binds to a client to process all the
 * messages it receives from a server. To create a new protocol to bind to a client, first extend this class
 * and implement the 'void process(String command)' method.
 * @author Caleb Wilson
 */
public abstract class AbstractProtocol implements Runnable{

	Client client;
	
	private volatile boolean done;
	
	/**
	 * Creates a new AbstractProtocol with the given Client. Subclasses need to call this constructor
	 * with a Client object they receive in their own constructor using the statement 'super(client);' in the
	 * first line of their constructor. In order to start this protocol listening to the client for messages,
	 * you need to create and start a Thread for this protocol with the code 'Thread t = new Thread(protocol);'
	 * and 't.start();'
	 * @param client a spacegame.client.Client that this protocol will bind to
	 */
	public AbstractProtocol(Client client){
		this.client = client;
	}
	
	/**
	 * This abstract method is called once the client has received a message from the server. This method should
	 * use this command to perform whatever action its protocol implementation requires.
	 * @param command a String received by the client from the server
	 */
	public abstract void process(String command);

	/**
	 * This method should be called by a Thread object. It loops indefinitely until the method 'stop()' is called.
	 * This loop checks to see if the Client has received a message and, if so, it calls the abstract method
	 * 'public abstract void process(String command)' with the message the client received.
	 */
	@Override
	public void run(){
		done = false;
		while(!done){
			if(client.hasMessage()){
				String message = client.getNextMessage();
				process(message);
			}
		}
	}
	
	/**
	 * This method stops the protocol thread that is looping to check the client. Call this method when you would
	 * like to disconnect the client.
	 */
	public void stop(){
		done = true;
	}
	
	/**
	 * This method wraps the 'Client.sendMessage(String message)' method. Calling this method will cause the 
	 * protocol to call its client object's sendMessage() method with the given message. This message will be 
	 * given to the server that the client is connected to.
	 */
	public void sendMessage(String message){
		client.sendMessage(message);
	}
	
}
