package spacegame.map;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class GameMap implements ISerializable, EntityListener {

	private ArrayList<Entity> entities;
	private ArrayList<MapListener> listeners;
	private LinkedBlockingQueue<MapAction> actionQueue;
	private LinkedBlockingQueue<MapEvent> eventQueue;
	private volatile boolean updating = false;
	private volatile boolean locked = false;
	private volatile boolean trackChanges = false;
	private volatile boolean trackActions = false;
	private volatile long serverNanoTime;
	
	public GameMap(){
		entities = new ArrayList<Entity>();
		listeners = new ArrayList<MapListener>();
		actionQueue = new LinkedBlockingQueue<MapAction>();
		eventQueue = new LinkedBlockingQueue<MapEvent>();
	}
	public GameMap(boolean trackChanges){
		this();
		this.trackChanges = trackChanges; 
	}
	
	public ArrayList<Entity> select(String key){
		ArrayList<Entity> selected = new ArrayList<Entity>();
		selected.ensureCapacity(entities.size());
		for(Entity entity: entities){
			if(entity.hasComponent(key)){
				selected.add(entity);
			}
		}
		return selected;
	}
	
	public boolean isUpdating(){
		return updating;
	}
	public boolean hasActions(){
		return !actionQueue.isEmpty();
	}	
	public boolean isLocked(){
		return locked;
	}
	public boolean isTrackingChanges(){
		return trackChanges;
	}
	public boolean isTrackingActions(){
		return trackActions;
	}
	public boolean isTrackingBoth(){
		return trackActions && trackChanges;
	}
	
	public void setUpdating(boolean b){
		updating = b;
	}
	
	public void setLocked(boolean b){
		locked = b;
	}
	public void setTrackChanges(boolean b){
		trackChanges = b;
	}
	public void setTrackActions(boolean b){
		trackActions = b;
	}
	public void setTrackBoth(boolean b){
		trackChanges = b;
		trackActions = b;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
		e.addEntityListener(this);
		if(trackActions){
			actionQueue.add(new MapAction(e.getUFID(),entities.size()-1,MapAction.ENTITY_ADD));
		}else if(!updating){
			fireMapEvent(new MapEvent(
					new MapAction(e.getUFID(),entities.size()-1,MapAction.ENTITY_ADD)));
		}
	}
	
	public Entity[] getEntities(){
		return entities.toArray(new Entity[entities.size()]);
	}
	
	public void removeEntity(int index){
		if(index>=0&&index<entities.size()){
			Entity removed = entities.remove(index);
			int ufid = removed.getUFID();
			removed.removeEntityListener(this);
			if(trackActions){
				actionQueue.add(new MapAction(ufid,index,MapAction.ENTITY_REMOVE));
			}else if(!updating){
				fireMapEvent(new MapEvent(new MapAction(ufid,index,MapAction.ENTITY_REMOVE)));
			}
		}
	}
	
	public void removeEntity(Entity e){
		int index = entities.indexOf(e);
		removeEntity(index);
	}
	
	public void update(long timeElapsed){
		for(Entity e: entities){
			e.update(timeElapsed);
		}
	}
	
	public void sync(GameMap map){
		map.serverNanoTime = serverNanoTime;
		if(trackChanges){
			while(!eventQueue.isEmpty()){
				MapEvent event = eventQueue.poll();
				map.executeMapEvent(event);							
			}
		}else{
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
	}
	
	public void executeMapEvent(MapEvent event){
		switch(event.getEventType()){
			case MapEvent.ENTITY_ADDED:
				addEntity(entities.get(event.getMapAction().getMapIndex()));
				break;
			case MapEvent.ENTITY_REMOVED:
				entities.remove(event.getMapAction().getMapIndex());
				break;
			case MapEvent.ENTITY_CHANGED:
				entities.get(event.getIndex()).setVariable(event.getVarName(), event.getValue());
				break;
		}
	}
	
	public long getServerNano(){
		return serverNanoTime;
	}
	
	public void setServerNano(long nanoTime){
		serverNanoTime = nanoTime;
	}
	
	public GameMap clone(){
		GameMap map = new GameMap();
		for(Entity e: entities){
			map.addEntity(e.clone());
		}
		for(Entity e: map.entities){
			e.createReferences(map);
		}
		return map;
	}

	@Override
	public String serialize() {
		StringBuilder serial = new StringBuilder();
		serial.append("serial GameMap size=");
		serial.append(entities.size());
		serial.append(" nano=");
		serial.append(serverNanoTime);
		for(int i = 0; i < entities.size();i++){
			serial.append("\n");
			serial.append("$serial Entity ");
			serial.append(i);
			serial.append(" ");
			serial.append(entities.get(i).serialize());
		}
		serial.append("\n$serial END");
		return serial.toString();
	}

	@Override
	public void unserialize(String serial) {
		//System.out.println(serial);
		try{
			String[] entitiesSerial = serial.split("\n\\$");
			String[] mapMeta = entitiesSerial[0].split(" ");
			
			//THIS IS A PRINTLN FOR MAP META USED IN DEBUGGING
			//for(int i = 0; i < mapMeta.length;i++){System.out.println("mapMeta["+i+"] : "+mapMeta[i]);}
			//mapMeta[0] : serial
			//mapMeta[1] : GameMap
			//mapMeta[2] : size={entities.size()}
			//mapMeta[3] : nano={serverNanoTime}
			//{variable/expression/method-invocation} = some number.
			
			//parse size
			String[] sizeSerial = mapMeta[2].split("=");
			int size = Integer.parseInt(sizeSerial[1]);
			entities.ensureCapacity(size);
			//parse serverNanoTime
			String[] nanoSerial = mapMeta[3].split("=");
			serverNanoTime = Long.parseLong(nanoSerial[1]);
			
			for(int i = 1; i<entitiesSerial.length;i++){
				if(entitiesSerial[i].equals("serial END")){
					break;
				}else{
					Entity entity = new Entity("Entity",-1);
					entity.unserialize(entitiesSerial[i].split(" ",4)[3]);
					entities.add(entity);
							//entitiesSerial[i].substring(
							//entitiesSerial[i].indexOf("Entity")+("Entity "+(i-1)).length()));
				}				
			}
		} catch(RuntimeException e){
			e.printStackTrace();
			throw new SerialException("Map unserialize() failure",e);
		}
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof GameMap){
			boolean equal = true;
			GameMap map = (GameMap)obj;
			if(map.entities.size()!=entities.size()){
				System.out.println("GameMap.equals(obj) :: GameMap sizes differ, "
						+entities.size()+":"+map.entities.size());
				return false;
			}
			for(int i = 0; i < entities.size(); i++){
				if(!entities.get(i).equals(map.entities.get(i))){
					System.out.println("GameMap.equals(obj) :: Entity not equal, index="+i);
					return false;
				}
			}
			return true;
		}else{
			System.out.println("GameMap.equals(obj) :: obj not instance of GameMap");
			return false;
		}
	}
	
	public Entity getEntityByName(String name){
		for(Entity entity: entities){
			if(entity.getName().equals(name)){
				return entity;
			}
		}
		return null;
	}
	public int getIndexByName(String name){
		for(int i = 0; i <entities.size(); i++){
			if(entities.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	public boolean hasEntityWithName(String name){
		for(int i = 0; i <entities.size(); i++){
			if(entities.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public void addMapListener(MapListener listener){
		listeners.add(listener);
	}
	
	public boolean removeMapListener(MapListener listener){
		return listeners.remove(listener);
	}
	
	private void fireMapEvent(MapEvent e){
		if(!listeners.isEmpty()){
			for(MapListener listener: listeners){
				listener.mapChanged(e);
			}
		}
		if(trackChanges){
			eventQueue.add(e);
		}
	}

	@Override
	public void entityChanged(EntityEvent ee) {
		String name = ee.getEntityName();
		fireMapEvent(new MapEvent(name,getIndexByName(name), ee.getVarName(), ee.getValue()));
	}
	
}
