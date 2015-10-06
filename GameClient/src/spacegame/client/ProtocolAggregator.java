package spacegame.client;

import java.util.ArrayList;

public class ProtocolAggregator extends AbstractProtocol {

	private ArrayList<AbstractProtocol> protocols;
	private volatile boolean processing = false;
	
	public ProtocolAggregator(Client client) {
		super(client);
		protocols = new ArrayList<AbstractProtocol>();
	}
	
	public void addProtocol(AbstractProtocol protocol){
		while(processing){Thread.yield();}
		protocols.add(protocol);
	}
	
	public boolean removeProtocol(AbstractProtocol protocol){
		while(processing){Thread.yield();}
		return protocols.remove(protocol);
	}

	@Override
	public void process(String command) {
		processing = true;
		for(AbstractProtocol protocol: protocols){
			protocol.process(command);
		}		
		processing = false;
	}

	
	
}
