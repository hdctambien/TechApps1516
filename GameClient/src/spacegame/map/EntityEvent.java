package spacegame.map;

public class EntityEvent {

	public String entityName;
	public String varName;
	public String value;
	
	public EntityEvent(String name, String var, String val){
		entityName = name;
		varName = var;
		value = val;
	}
	
	public String getEntityName(){
		return entityName;
	}
	
	public String getVarName(){
		return varName;
	}
	
	public String getValue(){
		return value;
	}
	
}
