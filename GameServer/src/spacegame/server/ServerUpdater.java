package spacegame.server;

import spacegame.map.GameMap;
import spacegame.map.Updater;

public class ServerUpdater extends Updater {

	private long lastNanoTime;
	
	public static final long MAP_PUSH_TIME = 0_001_000_000L; //0.001s = 0,001,000,000ns	
	
	public ServerUpdater(GameMap map) {
		super(map);
		lastNanoTime = System.nanoTime();
	}

	@Override
	public void afterUpdate() {//PUSH MAP STATE
		long nanoTime = System.nanoTime();
		if((nanoTime-lastNanoTime)>MAP_PUSH_TIME){//Map push time
			lastNanoTime = nanoTime;
			String message = "pushmap\n"+getMap().serialize()+"\nmappush";
			broadcast(message);
		}
	}

	private void broadcast(String message){
		ClientInfo[] clients = Main.server.getAllClientInfo();
		for(ClientInfo client: clients){
			client.sendMessage(message);
		}
	}
}
