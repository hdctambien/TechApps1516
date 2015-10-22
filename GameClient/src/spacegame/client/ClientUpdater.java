package spacegame.client;

import spacegame.map.GameMap;
import spacegame.map.Updater;

public class ClientUpdater extends Updater {

	private GameMap renderMap;
	private volatile boolean renderLock = false;
	private volatile boolean dirty = false;
	
	public ClientUpdater(GameMap map) {
		super(map);
		renderMap = map.clone();
	}

	public synchronized boolean isRenderLocked(){
		return renderLock;
	}
	
	public synchronized void setRenderLock(boolean lock){
		renderLock = lock;
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public GameMap getRenderMap(){
		return renderMap;
	}
	
	@Override
	public void afterUpdate() {//SYNC RENDER MAP
		dirty = true;
		{
			if(!isRenderLocked()){//drawing is using the renderMap
				setRenderLock(true);
			}else{return;}
		}
		map.sync(renderMap);
		dirty = false;
	}

}
