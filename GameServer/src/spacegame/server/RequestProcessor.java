package spacegame.server;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestProcessor implements Runnable {

	private Queue<Request> requests;
	private ProtocolHandler protocol;
	private volatile boolean done;
	
	public RequestProcessor(ProtocolHandler handler){
		requests = new LinkedBlockingQueue<Request>();
		protocol = handler;
	}
	public synchronized void addRequest(Request r){
		requests.add(r);
	}
	
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
