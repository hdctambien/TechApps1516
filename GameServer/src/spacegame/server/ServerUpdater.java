package spacegame.server;

import java.util.concurrent.LinkedBlockingQueue;

import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.map.Updater;

public class ServerUpdater extends Updater {

	private long lastNanoTime;
	
	public static final long MAP_PUSH_TIME = 0_001_000_000L; //0.001s = 0,001,000,000ns	
	
	private LinkedBlockingQueue<Entity> additionQueue;
	
	private volatile boolean forcePush;
	
	public ServerUpdater(GameMap map) {
		super(map);
		additionQueue = new LinkedBlockingQueue<Entity>();
		lastNanoTime = System.nanoTime();
	}

	@Override
	public void afterUpdate() {//PUSH MAP STATE
		while(!additionQueue.isEmpty()){
			getMap().addEntity(additionQueue.poll());
			if(!forcePush){forcePush = true;}
		}
		long nanoTime = System.nanoTime();
		if(((nanoTime-lastNanoTime)>MAP_PUSH_TIME)||forcePush){//Map push time
			lastNanoTime = nanoTime;
			String message = "pushmap\n"+getMap().serialize()+"\nmappush";
			broadcast(message);
		}
	}

	public void addEntity(Entity e){
		additionQueue.add(e);
	}
	
	private void broadcast(String message){
		ClientInfo[] clients = Main.server.getAllClientInfo();
		for(ClientInfo client: clients){
			client.sendMessage(message);
		}
	}
}
