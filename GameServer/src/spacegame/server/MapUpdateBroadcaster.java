package spacegame.server;

import spacegame.map.GameMap;
import spacegame.map.MapAction;
import spacegame.map.MapEvent;
import spacegame.map.MapListener;

public class MapUpdateBroadcaster implements MapListener {

	private GameMap map;
	
	public MapUpdateBroadcaster(GameMap map){
		this.map = map;
		map.addMapListener(this);
	}

	@Override
	public void mapChanged(MapEvent me) {
		ClientInfo[] allClients = Main.server.getAllClientInfo();
		String command = "NOOP";
		switch(me.getEventType()){
			case MapEvent.ENTITY_ADDED:
				MapAction ma = me.getMapAction();
				command = "add Entity "+ma.getMapIndex()+"\n$erial " +
					map.getEntities()[ma.getMapIndex()].serialize().replace(
							(CharSequence)"serial", (CharSequence)"$erial");
				break;
			case MapEvent.ENTITY_CHANGED:
				command = "mapset "+me.getEntityName()+" "+me.getVarName()+" "+me.getValue();
				break;
			case MapEvent.ENTITY_REMOVED:
				MapAction action = me.getMapAction();
				command = "delete "+action.getMapIndex();
				break;
		}
		for(ClientInfo client: allClients){
			client.sendMessage(command);
		}
	}
	
}
