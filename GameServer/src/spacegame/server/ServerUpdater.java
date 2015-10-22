package spacegame.server;

import spacegame.map.GameMap;
import spacegame.map.Updater;

public class ServerUpdater extends Updater {

	private long lastNanoTime;
	
	public static final long MAP_PUSH_TIME = 5_000_000; //5s = 5,000,000ns
	
	public ServerUpdater(GameMap map) {
		super(map);
		lastNanoTime = System.nanoTime();
	}

	@Override
	public void afterUpdate() {//PUSH MAP STATE
		long nanoTime = System.nanoTime();
		if((nanoTime-lastNanoTime)>MAP_PUSH_TIME){//Map push time
			lastNanoTime = nanoTime;
			//TODO: push the map
		}
	}

}
