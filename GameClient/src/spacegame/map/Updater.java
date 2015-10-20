package spacegame.map;

import spacegame.GameConstants;

public abstract class Updater implements Runnable {

	private GameMap ioMap, updateMap, renderMap;
	private boolean isGraphic;
	private volatile boolean done;
	
	private long prevNanoTime;
	
	public Updater(GameMap inputMap, boolean willRender){
		ioMap = inputMap;
		updateMap = ioMap.clone();
		isGraphic = willRender;
		if(isGraphic){
			renderMap = updateMap.clone();
		}
	}
	
	public GameMap getIOMap(){
		return ioMap;
	}
	
	public GameMap getUpdateMap(){
		return updateMap;
	}
	
	public GameMap getRenderMap(){
		return renderMap;
	}
	
	public void stopUpdating(){
		done = true;
	}
	
	@Override
	public void run() {
		done = false;
		prevNanoTime = System.nanoTime();
		while(!done){
			update();
			syncRenderMap();		
			handleIO();
		}
		
	}
	
	public abstract void handleIO();
	public abstract void syncRenderMap();
	
	public void update(){
		long nanoTime = System.nanoTime();
		long timeDiff = nanoTime - prevNanoTime;
		updateMap.update(timeDiff);
		prevNanoTime = nanoTime;
	}
	
	
}
