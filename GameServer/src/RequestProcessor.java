import java.io.IOException;
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
				System.out.println("Processing Request!");
				Request r = requests.remove();
				protocol.processRequest(r);
			}else{
				//System.out.println("request queue emty");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void exit(){
		done = true;
	}
		
}
