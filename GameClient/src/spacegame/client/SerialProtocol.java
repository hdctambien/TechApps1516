package spacegame.client;

import spacegame.map.GameMap;

public class SerialProtocol extends AbstractProtocol{

	private StringBuilder serialBuilder;
	private volatile String serial;
	private volatile boolean hasSerial;
	
	public SerialProtocol(Client client) {
		super(client);
		serialBuilder = new StringBuilder();
	}

	@Override
	public synchronized void process(String command) {
		if(command.startsWith("serial ")||command.startsWith("$serial ")){
			serialBuilder.append("\n"+command);
			if(command.equals("$serial END")){
				serial = serialBuilder.toString();
				hasSerial = true;
			}
		}
	}
	
	public synchronized boolean hasSerial(){
		return hasSerial;
	}
	
	public synchronized String getSerial(){
		hasSerial = false;
		return serial;
	}
	
	public GameMap getMapFromSerial(){
		GameMap map = new GameMap();
		map.unserialize(getSerial());
		return map;
	}

}
