package spacegame.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Entity implements ISerializable {

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
	
	public Component getComponent(String key){
		return components.get(key);
	}
	
	public boolean hasComponent(String key){
		return components.contains(key);
	}
	
	public Component[] getComponents(){
		return new ArrayList<Component>(components.values()).toArray(new Component[components.size()]);
	}
	
	public void update(long timeElapsed){
		if(hasComponent("Update")){
			UpdateComponent updater = (UpdateComponent) getComponent("Update");
			updater.update(timeElapsed);
		}
	}
	
	public void sync(Entity e){
		List<String> keys = new ArrayList<String>(components.keySet());
		for(int i = 0 ; i < keys.size(); i++){
			components.get(keys.get(i)).sync(e.components.get(keys.get(i)));
		}
	}
	
	public Entity clone(){
		Entity entity = new Entity(name, ufid);
		List<String> keys = new ArrayList<String>(components.keySet());
		for(String key: keys){
			entity.addComponent(key,components.get(key).clone(entity));
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

	protected void createReferences(){
		for(Component c: getComponents()){
			c.createReferences();
		}
	}
	
	@Override
	public String serialize() {
		StringBuilder serial = new StringBuilder();
		serial.append(name);
		serial.append(" size=");
		serial.append(components.size());		
		List<String> keys = new ArrayList<String>(components.keySet());
		for(String key: keys){
			serial.append("\n");
			serial.append("serial ");
			serial.append(key);
			serial.append(" ");
			serial.append(components.get(key).serialize());
		}		
		return serial.toString();
	}

	@Override
	public void unserialize(String serial) {
		// TODO Auto-generated method stub
		
	}
	
}
