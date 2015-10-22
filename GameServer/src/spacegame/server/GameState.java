package spacegame.server;

import java.util.ArrayList;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;

/**
 * This class hold Game-specific variables and contains functionality to get and set those variables
 * @author Caleb Wilson
 */
public class GameState {
	
	private GameMap map;
	private ServerUpdater updater;
	private Thread updaterThread;
	private ArrayList<GameStateListener> listeners;

	@SuppressWarnings("unused")
	private MapUpdateBroadcaster broadcaster;
	
	public GameState(){
		map = createTestMap();
		broadcaster = new MapUpdateBroadcaster(map);
		updater = new ServerUpdater(map);
		updaterThread = new Thread(updater);
		updaterThread.start();
		listeners = new ArrayList<GameStateListener>();
	}
	
	public void stopUpdater(){
		updater.stopUpdating();
		Thread.yield();
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(updaterThread.isAlive()){
			updaterThread.interrupt();
			System.out.println("Update loop failed to close: forcibly terminated.");
		}*/
	}
	
	public void addGameStateListener(GameStateListener listener){
		listeners.add(listener);
	}
	public boolean removeGameStateListener(GameStateListener listener){
		return listeners.remove(listener);
	}
	
	public GameMap getMap(){
		return map;
	}	
	public synchronized void doGet(String var, ClientInfo info, Request r){
		if(!map.hasEntityWithName(info.getShip())){
			r.reply("UNK");
			return;
		}
		Entity ship = map.getEntityByName(info.getShip());
		if(ship.hasVariable(var)){
			info.sendMessage("set "+var+" "+ship.getVariable(var));
		}else{
			r.reply("UNK");
		}
	}
	public synchronized void doSet(String var, String val, ClientInfo info, Request r){
		if(!map.hasEntityWithName(info.getShip())){
			r.reply("UNK");
			return;
		}
		Entity ship = map.getEntityByName(info.getShip());
		if(ship.hasVariable(var)){
			ship.setVariable(var, val);
			r.reply("OK");
			fireGameStateEvent(new GameStateEvent(var,val));
		}else{
			r.reply("UNK");
		}
	}
	public synchronized void doMapSet(String entityName, String var, String val, ClientInfo info, Request r){
		if(!map.hasEntityWithName(entityName)){
			r.reply("UNK");
			return;
		}
		Entity entity = map.getEntityByName(entityName);
		if(entity.hasVariable(var)){
			entity.setVariable(var, val);
			r.reply("OK");
			//fireGameStateEvent(new GameStateEvent(var,val));
		}else{
			r.reply("UNK");
		}		
	}
	
	private void fireGameStateEvent(GameStateEvent e){
		if(!listeners.isEmpty()){
			for(GameStateListener listener: listeners){
				listener.stateUpdated(e);
			}
		}
	}
	public boolean tryBooleanParse(String var, String val){
		boolean temp = Boolean.parseBoolean(val);
		if(!temp&&!val.toLowerCase().equals("false")){
			return false;
		}
		switch(var){
			
		}
		return true;
	}
	public boolean tryDoubleParse(String var, String val){
		boolean success = true;
		double d = Double.NaN;
		try{
			d = Double.parseDouble(val);
			switch(var){
				
			}
		}catch(NumberFormatException e){
			success = false;
		}		
		return success;
	}
	
	private static GameMap createTestMap(){
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		map.addEntity(factory.createAsteroid());
		map.addEntity(factory.createShip());
		return map;
	} 
}