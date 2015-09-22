package spacegame.map;

import java.util.ArrayList;

public class Entity {

	private ArrayList<Component> components;
	private String name;
	private int ufid; //Unique Factory Identifier
	
	protected Entity(String name, int ufid){
		this.name = name;
		this.ufid = ufid;
	}
	
	public String getName(){
		return name;
	}
	
	public int getUFID(){
		return ufid;
	}
	
	public void addComponent(Component c){
		components.add(c);
	}
	
	public Component[] getComponents(){
		return components.toArray(new Component[components.size()]);
	}
	
	public void update(long timeElapsed){
		for(Component c: components){
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
		for(Component c: components){
			entity.addComponent(c.clone());
		}
		return entity;
	}
	
	public boolean hasVariable(String varname){
		for(Component c: components){
			if(c.hasVariable(varname)){
				return true;
			}
		}
		return false;
	}
	
	public boolean setVariable(String varname, String value){
		for(Component c: components){
			if(c.hasVariable(varname)){
				return c.setVariable(varname, value);
			}
		}
		return false;
	}
	
	public String getVariable(String varname){
		for(Component c: components){
			if(c.hasVariable(varname)){
				return c.getVariable(varname);
			}
		}
		return "Bruh, I don't have this variable...";
	}
	
}
