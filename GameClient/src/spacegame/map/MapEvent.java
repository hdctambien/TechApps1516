package spacegame.map;

public class MapEvent {
	private String entityName;
	private int index;
	private String varName;
	private String value;
	
	public MapEvent(String entity, int index, String var, String val){
		entityName = entity;
		this.index = index;
		varName = var;
		value = val;
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
	
}
