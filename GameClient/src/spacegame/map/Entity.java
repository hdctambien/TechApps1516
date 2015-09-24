package spacegame.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Entity {

	private Hashtable<String,Component> components;
	private String name;
	private int ufid; //Unique Factory Identifier
	
	protected Entity(String name, int ufid){
		this.name = name;
		this.ufid = ufid;
		components = new Hashtable<String,Component>();
	}
	
	public String getName(){
		return name;
	}
	
	public int getUFID(){
		return ufid;
	}
	
	public void addComponent(String key, Component c){
		components.put(key,c);
	}
	
	public Component[] getComponents(){
		return new ArrayList<Component>(components.values()).toArray(new Component[components.size()]);
	}
	
	public void update(long timeElapsed){
		for(Component c: getComponents()){
			c.update(timeElapsed);
		}
	}
	
	public void sync(Entity e){
		for(int i = 0 ; i < components.size(); i++){
			components.get(i).sync(e.components.get(i));
		}
	}
	
	public Entity clone(){
		Entity entity = new Entity(name, ufid);
		List<String> keys = new ArrayList<String>(components.keySet());
		for(String key: keys){
			entity.addComponent(key,components.get(key).clone());
		}
		return entity;
	}
	
	public boolean hasVariable(String varname){
		for(Component c: getComponents()){
			if(c.hasVariable(varname)){
				return true;
			}
		}
		return false;
	}
	
	public boolean setVariable(String varname, String value){
		for(Component c: getComponents()){
			if(c.hasVariable(varname)){
				return c.setVariable(varname, value);
			}
		}
		return false;
	}
	
	public String getVariable(String varname){
		for(Component c: getComponents()){
			if(c.hasVariable(varname)){
				return c.getVariable(varname);
			}
		}
		return "Bruh, I don't have this variable...";
	}
	
}
