package spacegame.client;
import java.io.PrintWriter;

public abstract class AbstractProtocol implements Runnable{

	Client client;
	PrintWriter writer;
	
	private volatile boolean done;
	
	public AbstractProtocol(Client client){
		this.client = client;
		writer = client.getPrintWriter();
	}
	
	public abstract void process(String command);

	public void run(){
		done = false;
		while(!done){
			if(client.hasMessage()){
				String message = client.getNextMessage();
				process(message);
			}
		}
	}
	
	public void stop(){
		done = true;
	}
	
}
