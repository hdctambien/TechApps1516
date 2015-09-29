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
		c.setEntity(this);
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
		String[] lines = serial.split("\n");
		String[] meta = lines[0].split(" ");
		try{
			name = meta[0];
			String[] nameUfid = meta[0].split("\\.");
			ufid = Integer.parseInt(nameUfid[1]);
			//String[] sizeS = meta[1].split("=");
			//int size = Integer.parseInt(sizeS[1]);
			for(int i = 1; i < lines.length; i++){
				String[] data = lines[i].split(" ",3);
				//data[0]==="serial"
				String key = data[1];
				Component c;
				boolean update = false;
				switch(key){
					case EntityFactory.PHYSICS: c = new PhysicsComponent(); break;
					case EntityFactory.FUEL: c = new FuelComponent(); break;
					case EntityFactory.POSITION: c = new PositionComponent(); break;
					case EntityFactory.POWER: c = new PowerComponent(); break;
					case EntityFactory.UPDATE:
						update = true;
						switch(data[2]){
							case EntityFactory.ASTEROID_UPDATE:
								c = new AsteroidUpdateComponent();
								break;
							case EntityFactory.SHIP_UPDATE:
								c = new ShipUpdateComponent();
								break;
							default:
								throw new RuntimeException("Unknown Update Component: "+key);
						}
						break;
					default:
						throw new RuntimeException("Unknown Component: "+key);
				}
				components.put(key, c);
				if(!update){
					c.unserialize(data[2]);
				}
			}
		}catch(RuntimeException e){//for all those index out of bounds that good occur but I don't want
			//to bother testing for since they should cause a serial exception anyway
			throw new SerialException("Entity unserialize failure",e);
		}
		
	}
	
}
