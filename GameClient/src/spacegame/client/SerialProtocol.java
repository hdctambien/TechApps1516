package spacegame.client;

import spacegame.map.GameMap;

public class SerialProtocol extends AbstractProtocol{

	private StringBuilder serialBuilder;
	//private StringBuilder entityBuilder;
    //private Queue<String> entitySerialQueue;
	private volatile String serial;
	private volatile boolean hasSerial;
	
	public SerialProtocol(Client client) {
		super(client);
		serialBuilder = new StringBuilder();
		//entityBuilder = new StringBuilder();
		//entitySerialQueue = new LinkedBlockingQueue<String>();
	}

	@Override
	public synchronized void process(String command) {
		if(command.startsWith("serial ")||command.startsWith("$serial ")){
			serialBuilder.append("\n"+command);
			if(command.equals("$serial END")){
				serial = serialBuilder.toString();
				hasSerial = true;
			}
		}/*else if(command.startsWith("$erial")){
			entityBuilder.append("\n"+command);
			if(command.equals("$erial END")){
				String entity = entityBuilder.toString().replace('$', 's');
				entitySerialQueue.add(entity);
			}
		}*/
	}
	
	public synchronized boolean hasSerial(){
		return hasSerial;
	}
	
	public synchronized String getSerial(){
		hasSerial = false;
		return serial;
	}
	
	/*public synchronized boolean hasEntity(){
		return !entitySerialQueue.isEmpty();
	}
	
	public synchronized String getEntitySerial(){
		return entitySerialQueue.poll();
	}
	
	public Entity getEntityFromSerial(){
		String serial = getEntitySerial();
		Entity entity = EntityFactory.createSerial();
		entity.unserialize(serial);
		return entity;
	}*/
	
	public GameMap getMapFromSerial(){
		GameMap map = new GameMap();
		map.unserialize(getSerial());
		return map;
	}

}
