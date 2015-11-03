package spacegame.client;

import spacegame.map.GameMap;
import spacegame.map.MapEvent;
import spacegame.map.Updater;

public class ClientUpdater extends Updater {

	private GameMap renderMap;
	private volatile boolean renderLock = false;
	private volatile boolean dirty = false;
	private volatile boolean drawDirty = true;
	private volatile boolean scheduleIOPush = false;
	private GameMap ioPush;
	
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
	
	public synchronized boolean isDrawDirty(){
		return drawDirty;
	}
	
	public synchronized void setDrawDirty(boolean dd){
		drawDirty = dd;
	}
	
	public GameMap getRenderMap(){
		return renderMap;
	}
	
	public void addUserAction(String entity, String variable, String value, Client client){
		int index = getMap().getIndexByName(entity);
		MapEvent event = new MapEvent(entity, index, variable, value);
		addIOAction(event);
		client.sendMessage("mapset "+entity+" "+variable+" "+value);
	}
	
	public void scheduleMapPush(GameMap ioMap){
		ioPush = ioMap;
		scheduleIOPush = true;
	}	
	
	@Override
	public void afterUpdate() {//SYNC RENDER MAP
		dirty = true;
		{
			if(!isRenderLocked()){//drawing is using the renderMap
				setRenderLock(true);
				map.sync(renderMap);
				dirty = false;
				setDrawDirty(true);
				setRenderLock(false);
			}else{return;}
		}		
		if(scheduleIOPush){
			ioPush.sync(getMap());
			ioPush = null;
			scheduleIOPush = false;
		}
		
	}

}
