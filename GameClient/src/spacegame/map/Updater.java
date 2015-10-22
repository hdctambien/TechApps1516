package spacegame.map;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class Updater implements Runnable {

	protected GameMap map;
	private LinkedBlockingQueue<MapEvent> ioActions;
	
	private volatile boolean done;	
	
	private long prevNanoTime;
	public static final long IO_UPDATE_TIME = 5_000_000; //5ms == 5,000,000ns
	
	public Updater(GameMap map){
		this.map = map;
		ioActions = new LinkedBlockingQueue<MapEvent>();
		map.setTrackChanges(false);
	}
	
	public GameMap getMap(){
		return map;
	}
	
	public void stopUpdating(){
		done = true;
	}
	
	public void addIOAction(MapEvent event){
		ioActions.add(event);
	}
	
	@Override
	public void run() {
		done = false;
		prevNanoTime = System.nanoTime();
		while(!done){
			handleIO();
			update();
			afterUpdate();
		}
	}
	
	public abstract void afterUpdate();
	
	public void handleIO(){
		long beginNanoTime = System.nanoTime();
		while((!ioActions.isEmpty())&&((System.nanoTime()-beginNanoTime)<IO_UPDATE_TIME)){
			MapEvent event = ioActions.poll();
			map.executeMapEvent(event);
		}
	}
	
	public void update(){
		long nanoTime = System.nanoTime();
		long timeDiff = nanoTime - prevNanoTime;
		map.update(timeDiff);
		prevNanoTime = nanoTime;
	}
	
	
}
