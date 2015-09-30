package spacegame.client;

import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.map.MapEvent;
import spacegame.map.MapListener;

public class MapUpdateProtocol extends AbstractProtocol implements MapListener{

	public static final int ERR_CMD_FORMAT = 1;
	
	private GameMap map;
	private String ship;
	
	public MapUpdateProtocol(Client client, GameMap map, String shipName) {
		super(client);
		this.map = map;
		map.addMapListener(this);
		ship = shipName;
	}

	@Override
	public void process(String command) {
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
		}
	}

	@Override
	public void mapChanged(MapEvent me) {
		client.sendMessage("mapset "+me.getEntityName()+" "+me.getVarName()+" "+me.getValue());
	}

	public void doSet(String message, String var, String val){
		if(!map.hasEntityWithName(ship)){
			client.sendMessage("UNK "+message);
		}else{
			Entity entity = map.getEntityByName(ship);
			if(entity.hasVariable(var)){
				entity.setUpdating(true);
				entity.setVariable(var, val);
				entity.setUpdating(false);
				client.sendMessage("OK "+message);
			}else{
				client.sendMessage("UNK "+message);
			}		
		}
	}
	
	public void doMapSet(String message, String entityName, String var, String val){
		if(!map.hasEntityWithName(entityName)){
			client.sendMessage("UNK "+message);
			return;
		}
		Entity entity = map.getEntityByName(entityName);
		if(entity.hasVariable(var)){
			entity.setUpdating(true);
			entity.setVariable(var, val);
			entity.setUpdating(false);
			client.sendMessage("OK "+message);
		}else{
			client.sendMessage("UNK "+message);
		}		
	}
}
