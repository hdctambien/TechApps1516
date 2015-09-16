package spacegame.server;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class receives Request objects from RequestForwarders and hands them one by one, in order, to a 
 * ProtocolHandler given to it. It runs on a thread that consumes Requests from a Request Queue.
 * 
 * @author Caleb Wilson
 */
public class RequestProcessor implements Runnable {

	private Queue<Request> requests;
	private ProtocolHandler protocol;
	private volatile boolean done;
	
	/**
	 * Create a new RequestProcessor with the given ProtocolHandler that will respond to the Requests
	 * @param handler the ProtocolHandler
	 */
	public RequestProcessor(ProtocolHandler handler){
		requests = new LinkedBlockingQueue<Request>();
		protocol = handler;
	}
	
	/**
	 * This adds a Request to the RequestProcessor request queue. This method is called by RequestForwarders who
	 * receive messages from clients and add them to the RequestProcessor queue
	 * @param r the client's Request
	 */
	public synchronized void addRequest(Request r){
		requests.add(r);
	}
	
	/**
	 * This is the RequestProcessor thread that continuously processes Client Requests from the RequestProcessor
	 * Request Queue.
	 */
	public void run(){
		done = false;
		while(!done){
			if(!requests.isEmpty()){
				Request r = requests.remove();
				System.out.println("Processing Request: "+r.getMessage());
				protocol.processRequest(r);
			}else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void exit(){
		done = true;
	}
		
}
