package spacegame.map;

public abstract class Component{

	private Entity owner;
	
	public Component(Entity entity){
		owner = entity;
	}
	
	public Entity getEntity(){
		return owner;
	}
	
	public abstract void sync(Component c);
	public abstract Component clone();
	public abstract boolean hasVariable(String varname);
	public abstract String getVariable(String varname);
	public abstract boolean setVariable(String varname, String value);
	
}
