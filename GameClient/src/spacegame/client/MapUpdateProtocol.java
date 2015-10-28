package spacegame.client;

import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.map.MapEvent;
import spacegame.map.MapListener;

public class MapUpdateProtocol extends AbstractProtocol implements MapListener{

	public static final int ERR_CMD_FORMAT = 1;
	
	private String ship;
	private SerialProtocol serial;
	private ClientUpdater updater;
	private boolean expectingSerialMap=false;
	
	public static final int ENTITY_NOT_EXPECTED = 0;
	public static final int ENTITY_EXPECTED = 1;
	public static final int ENTITY_SERIAL_EXPECTED =2;
	//private int expectingEntity = ENTITY_NOT_EXPECTED;
	
	public MapUpdateProtocol(Client client, ClientUpdater updater, String shipName, SerialProtocol serial) {
		super(client);
		this.serial = serial;
		this.updater = updater;
		updater.getMap().addMapListener(this);
		ship = shipName;
	}
	
	public void changeMap(String entity, String variable, String value){
		client.sendMessage("mapset "+entity+" "+variable+" "+value);
		GameMap map = updater.getMap();
		int index = map.getIndexByName(entity);
		updater.addIOAction(new MapEvent(entity,index,variable,value));
	}

	@Override
	public void process(String command) {
		/*if(expectingEntity == ENTITY_SERIAL_EXPECTED && serial.hasEntity()){
			map.setUpdating(true);
			map.addEntity(serial.getEntityFromSerial());
			map.setUpdating(false);
			expectingEntity = ENTITY_NOT_EXPECTED;
		}*/
		if(expectingSerialMap&&serial.hasSerial()){
			updater.scheduleMapPush(serial.getMapFromSerial());
			expectingSerialMap = false;
		}
		String[] cmds = command.split(" ");
		switch(cmds[0]){
			case "set":
				if(cmds.length<3){
					client.sendMessage("ERR "+ERR_CMD_FORMAT+" "+command);
				}else{
					doSet(command,cmds[1],cmds[2]);
				}
				break;
			case "mapset":
				if(cmds.length<4){
					client.sendMessage("ERR "+ERR_CMD_FORMAT+" "+command);
				}else{
					doMapSet(command,cmds[1],cmds[2],cmds[3]);
				}
				break;
			case "mappush":
				if(serial.hasSerial()){
					updater.scheduleMapPush(serial.getMapFromSerial());
					expectingSerialMap = false;
				}else{
					expectingSerialMap = true;
				}
				break;
			/*case "delete":
				if(cmds.length<2){
					client.sendMessage("ERR "+ERR_CMD_FORMAT+" "+command);
				}else{
					doDelete(command, cmds[1]);
				}
				break;
			case "add":
				expectingEntity++;
				break;
			case "$erial END":
				expectingEntity++;
				break;*/
		}
	}

	@Override
	public void mapChanged(MapEvent me) {
		switch(me.getEventType()){
			case MapEvent.ENTITY_CHANGED:
				client.sendMessage("mapset "+me.getEntityName()+" "+me.getVarName()+" "+me.getValue());
				break;
			//These events shouldn't be happening on the client side, only server should be deleting
			//and adding stuff
			case MapEvent.ENTITY_ADDED:
				break;
			case MapEvent.ENTITY_REMOVED:
				break;
		}		
	}

	/*public void doDelete(String message, String index){
		int i = Integer.parseInt(index);
		map.setUpdating(true);
		map.removeEntity(i);
		map.setUpdating(false);
	}*/
	
	public void doSet(String message, String var, String val){
		GameMap map = updater.getMap();
		if(!map.hasEntityWithName(ship)){
			client.sendMessage("UNK "+message);
		}else{
			int index = map.getIndexByName(ship);
			if(map.getEntities()[index].hasVariable(var)){
				client.sendMessage("UNK "+message);
			}else{
				updater.addIOAction(new MapEvent(ship,index,var,val));
				client.sendMessage("OK "+message);
			}	
		}
	}
	
	public void doMapSet(String message, String entityName, String var, String val){
		GameMap map = updater.getMap();
		if(!map.hasEntityWithName(entityName)){
			client.sendMessage("UNK "+message);
			return;
		}
		Entity entity = map.getEntityByName(entityName);
		if(entity.hasVariable(var)){
			updater.addIOAction(new MapEvent(entityName,map.getIndexByName(entityName),var,val));
			client.sendMessage("OK "+message);
		}else{
			client.sendMessage("UNK "+message);
		}		
	}
}
