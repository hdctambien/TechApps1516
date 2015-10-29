package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.ISerializable;

public abstract class Component implements ISerializable{

	private Entity owner;
	
	public Component(){
		
	}
	
	public Component(Entity entity){
		setEntity(entity);
	}
	
	public void setEntity(Entity entity){
		owner = entity;
	}
	
	public Entity getEntity(){
		return owner;
	}
	
	public abstract void sync(Component c);
	public abstract Component clone(Entity entity);
	public abstract boolean hasVariable(String varname);
	public abstract String getVariable(String varname);
	public abstract boolean setVariable(String varname, String value);
	
	public boolean hasDouble(String name){return false;}
	public double getDouble(String name){return Double.NaN;}
	public boolean setDouble(String name, double value){return false;}
	
	public boolean hasInt(String name){return false;}
	public int getInt(String name){return Integer.MIN_VALUE;}
	public boolean setInt(String name, int value){return false;}
	
	public void unserialize(String serial){
		String[] vars = serial.split(" ");
		for(int i = 0; i < vars.length; i++){
			String[] entry = vars[i].split(":");
			if(entry.length<2){
				throw new RuntimeException("Error in parsing component vars during unserialize()");
			}else{
				if(!setVariable(entry[0],entry[1])){
					System.err.println("This component was given vars it doesn't have");
					System.err.println(entry[0]+":"+entry[1]);
				}
			}		
		}
	}
	
}
