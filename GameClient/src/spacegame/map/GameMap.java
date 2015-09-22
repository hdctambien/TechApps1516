package spacegame.map;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class GameMap {

	ArrayList<Entity> entities;
	LinkedBlockingQueue<MapAction> actionQueue;
	private boolean cloned = false;
	
	public GameMap(){
		entities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e){
		entities.add(e);
		if(cloned){
			actionQueue.add(new MapAction(e.getUFID(),entities.size()-1,MapAction.ENTITY_ADD));
		}		
	}
	
	public Entity[] getEntities(){
		return entities.toArray(new Entity[entities.size()]);
	}
	
	public void removeEntity(Entity e){
		int index = entities.indexOf(e);
		if(index!=-1){
			int ufid = entities.remove(index).getUFID();
			actionQueue.add(new MapAction(ufid,index,MapAction.ENTITY_REMOVE));
		}
	}
	
	public void update(long timeElapsed){
		for(Entity e: entities){
			e.update(timeElapsed);
		}
	}
	
	public void sync(GameMap map){
		//additions and deletions handled first
		while(!actionQueue.isEmpty()){
			MapAction action = actionQueue.poll();
			if(action.getAction()==MapAction.ENTITY_ADD){
				map.addEntity(entities.get(action.getMapIndex()));
			}else if(action.getAction()==MapAction.ENTITY_REMOVE){
				map.entities.remove(action.getMapIndex());
			}
		}
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i).getUFID()!=map.entities.get(i).getUFID()){
				System.err.println("Error: entities synched in map do not have matching UFIDs!");
			}
			entities.get(i).sync(map.entities.get(i));
		}
		
	}
	
	public GameMap clone(){
		GameMap map = new GameMap();
		for(Entity e: entities){
			map.addEntity(e.clone());
		}
		cloned = true;
		return map;
	}
}
