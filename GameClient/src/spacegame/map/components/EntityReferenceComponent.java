package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.GameMap;

public class EntityReferenceComponent extends ReferenceComponent {

	String name;
	Entity refEntity;
	
	public EntityReferenceComponent(String entityName){
		name = entityName;
	}
	
	public EntityReferenceComponent(String entityName, GameMap map) {
		name = entityName;
		refEntity = map.getEntityByName(entityName);
	}	

	public EntityReferenceComponent(Entity referenceEntity) {
		name = referenceEntity.getName();
		refEntity = referenceEntity;
	}
	
	public EntityReferenceComponent(String entityName, Entity referenceEntity){
		name = entityName;
		refEntity = referenceEntity;
	}
	
	@Override
	public void createReferences(GameMap map){
		refEntity = map.getEntityByName(name);
	}
	
	@Override
	public String serialize() {
		return "refEntity:"+name;
	}

	@Override
	public void sync(Component c) {
		if(c instanceof EntityReferenceComponent){
			EntityReferenceComponent erc = (EntityReferenceComponent)c;
			erc.name = name;
		}
	}

	@Override
	public Component clone(Entity entity) {
		EntityReferenceComponent erc = new EntityReferenceComponent(name);
		return erc;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof EntityReferenceComponent){
			EntityReferenceComponent erc = (EntityReferenceComponent) obj;
			return erc.name.equals(name);
		}else{return false;}
	}
	
	@Override
	public boolean hasVariable(String varname) {
		return varname.equals("refEntity");
	}

	@Override
	public String getVariable(String varname) {
		if(varname.equals("refEntity")){
			return name;
		}else{ return null;}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.equals("refEntity")){
			name = value;
			return true;
		}else{return false;}
	}

}
