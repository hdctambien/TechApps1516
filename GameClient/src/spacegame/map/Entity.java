package spacegame.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import spacegame.map.components.AsteroidUpdateComponent;
import spacegame.map.components.Component;
import spacegame.map.components.EntityReferenceComponent;
import spacegame.map.components.FuelComponent;
import spacegame.map.components.GunComponent;
import spacegame.map.components.HeadingComponent;
import spacegame.map.components.HealthComponent;
import spacegame.map.components.MultipleReferenceComponent;
import spacegame.map.components.PhysicsComponent;
import spacegame.map.components.PositionComponent;
import spacegame.map.components.PowerComponent;
import spacegame.map.components.ReferenceComponent;
import spacegame.map.components.RenderComponent;
import spacegame.map.components.ShieldComponent;
import spacegame.map.components.ShipUpdateComponent;
import spacegame.map.components.UpdateComponent;

public class Entity implements ISerializable {

	private Hashtable<String,Component> components;
	private ArrayList<EntityListener> listeners;
	private String name;
	private int ufid; //Unique Factory Identifier
	private boolean isUpdating;
	
	protected Entity(String name, int ufid){
		this.name = name;
		this.ufid = ufid;
		components = new Hashtable<String,Component>();
		isUpdating = false;
		listeners = new ArrayList<>();
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
		return components.containsKey(key);
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
			Component c = components.get(key);
			Component cc = c.clone(entity);
			/*if(c instanceof EntityReferenceComponent){
				cc = c.clone(entity);
				EntityReferenceComponent erc = (EntityReferenceComponent)c;
				erc.createReference(otherMap);
			}*/
			entity.addComponent(key,cc);
		}
		return entity;
	}
	
	public void createReferences(GameMap map){
		List<String> keys = new ArrayList<String>(components.keySet());
		for(String key: keys){
			Component c = components.get(key);
			if(c instanceof ReferenceComponent){
				ReferenceComponent rc = (ReferenceComponent)c;
				rc.createReferences(map);
			}
		}
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
				if(!isUpdating){
					fireEntityEvent(new EntityEvent(name, varname, value));
				}
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
	
	public boolean isUpdating(){
		return isUpdating;
	}
	public void setUpdating(boolean updating){
		isUpdating = updating;
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
			//Was used in debugging
			//System.out.println(meta[0]);
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
					case EntityFactory.RENDER: c = new RenderComponent(null); break;
					case EntityFactory.HEADING: c = new HeadingComponent(); break;
					case EntityFactory.SHIELD: c = new ShieldComponent(); break;
					case EntityFactory.HEALTH: c = new HealthComponent(); break;
					case EntityFactory.GUN: c = new GunComponent(); break;
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
						if(key.startsWith(EntityFactory.REF_HEADER)){
							c = new EntityReferenceComponent((String)null);
						}else if(key.startsWith(EntityFactory.LIST_REF_HEADER)){
							c = new MultipleReferenceComponent();
						}else{
							throw new RuntimeException("Unknown Component: "+key);
						}
				}
				if(!update){
					c.unserialize(data[2]);
				}
				components.put(key, c);
				c.setEntity(this);
			}
		}catch(RuntimeException e){//for all those index out of bounds that good occur but I don't want
			//to bother testing for since they should cause a serial exception anyway
			throw new SerialException("Entity unserialize failure",e);
		}		
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Entity){
			Entity entity = (Entity) obj;
			List<String> keys = new ArrayList<String>(components.keySet());
			/*List<String> keys2 = new ArrayList<String>(entity.components.keySet());
			for(String key: keys){
				System.out.println(key);
			}
			for(String key: keys2){
				System.out.println(key);
			}*/
			for(String key: keys){
				if(entity.hasComponent(key)){
					if(!entity.getComponent(key).equals(components.get(key))){
						System.out.println("Entity.equals(obj) :: Components not equal: "+key);
						return false;
					}
				}else{
					System.out.println("Entity.equals(obj) :: Entity doesn't contain key: "+key);
					return false;
				}
			}
			return true;
		}else{
			System.out.println("Entity.equals(obj) :: obj not instance of Entity");
			return false;
		}
	}
	
	public void addEntityListener(EntityListener listener){
		listeners.add(listener);
	}
	
	public boolean removeEntityListener(EntityListener listener){
		return listeners.remove(listener);
	}
	
	private void fireEntityEvent(EntityEvent e){
		if(!listeners.isEmpty()){
			for(EntityListener listener: listeners){
				listener.entityChanged(e);
			}
		}
	}
	
}
