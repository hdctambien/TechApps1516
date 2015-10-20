package spacegame.map;


public class MapEvent {
	private String entityName;
	private int index;
	private String varName;
	private String value;
	
	private MapAction mapAction;
	private int eventType;
	
	public static final int ENTITY_ADDED = MapAction.ENTITY_ADD;
	public static final int ENTITY_REMOVED = MapAction.ENTITY_REMOVE;
	public static final int ENTITY_CHANGED = 3;
	
	public MapEvent(String entity, int index, String var, String val){
		entityName = entity;
		this.index = index;
		varName = var;
		value = val;
		eventType = ENTITY_CHANGED;
	}
	public MapEvent(MapAction action){
		eventType = action.getAction();
		mapAction = action;
	}
	
	public String getEntityName(){
		return entityName;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getVarName(){
		return varName;
	}
	
	public String getValue(){
		return value;
	}
	
	public MapAction getMapAction(){
		return mapAction;
	}
	public int getEventType(){
		return eventType;
	}
}
