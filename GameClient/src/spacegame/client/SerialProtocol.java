package spacegame.client;

import java.util.concurrent.LinkedBlockingQueue;

import spacegame.map.GameMap;

public class SerialProtocol extends AbstractProtocol{

	private StringBuilder serialBuilder;
	//private StringBuilder entityBuilder;
    //private Queue<String> entitySerialQueue;
	private LinkedBlockingQueue<String> serialQueue;
	
	public SerialProtocol(Client client) {
		super(client);
		serialBuilder = new StringBuilder();
		serialQueue = new LinkedBlockingQueue<String>();
		//entityBuilder = new StringBuilder();
		//entitySerialQueue = new LinkedBlockingQueue<String>();
	}

	@Override
	public synchronized void process(String command) {
		if(command.startsWith("serial ")||command.startsWith("$serial ")){
			serialBuilder.append("\n"+command);
			if(command.equals("$serial END")){
				String serial = serialBuilder.toString();
				serialQueue.add(serial);
				serialBuilder = new StringBuilder();
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
		return !serialQueue.isEmpty();
	}
	
	public synchronized String getSerial(){
		return serialQueue.poll();
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
		String serial = getSerial();
		/*if(serial.startsWith("\n")){
			System.out.println("Starts with Newline: BAD");
			serial = serial.substring(1, serial.length());
		}*/
		System.out.println("GET SERIAL\n"+serial);
		map.unserialize(serial);
		return map;
	}

}
