package spacegame.client;

import java.util.ArrayList;

public class ProtocolAggregator extends AbstractProtocol {

	private ArrayList<AbstractProtocol> protocols;
	
	public ProtocolAggregator(Client client) {
		super(client);
		protocols = new ArrayList<AbstractProtocol>();
	}
	
	public void addProtocol(AbstractProtocol protocol){
		protocols.add(protocol);
	}
	
	public boolean removeProtocol(AbstractProtocol protocol){
		return protocols.remove(protocol);
	}

	@Override
	public void process(String command) {
		for(AbstractProtocol protocol: protocols){
			protocol.process(command);
		}		
	}

	
	
}
