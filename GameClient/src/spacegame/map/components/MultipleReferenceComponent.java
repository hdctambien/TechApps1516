package spacegame.map.components;

import java.util.ArrayList;

import spacegame.map.Entity;
import spacegame.map.GameMap;

public class MultipleReferenceComponent extends ReferenceComponent {

	String[] names;
	Entity[] entities;
	
	public MultipleReferenceComponent(){
		names=new String[]{};
		entities = new Entity[]{};
	}
	
	public MultipleReferenceComponent(String[] nameList){
		names = copyStrings(nameList);
		entities = new Entity[]{};
	}
	
	public MultipleReferenceComponent(String[] names, GameMap map) {
		this.names = copyStrings(names);
		entities = new Entity[names.length];
		for(int i = 0; i<names.length;i++){
			entities[i] = map.getEntityByName(names[i]);
		}
	}	

	public MultipleReferenceComponent(Entity[] references) {
		entities = copyEntities(references);
		names = new String[entities.length];
		for(int i = 0; i< entities.length;i++){
			names[i] = entities[i].getName();
		}
	}
	
	public MultipleReferenceComponent(String[] names, Entity[] references){
		this.names = copyStrings(names);
		entities = copyEntities(references);
	}

	private static String[] copyStrings(String[] array){
		String[] clone = new String[array.length];
		for(int i = 0; i < array.length;i++){
			clone[i] = array[i];
		}
		return clone;
	}
	
	private static Entity[] copyEntities(Entity[] array){
		Entity[] clone = new Entity[array.length];
		for(int i = 0; i < array.length;i++){
			clone[i] = array[i];
		}
		return clone;
	}
	
	@Override
	public void createReferences(GameMap map){
		entities = new Entity[names.length];
		for(int i = 0; i<names.length;i++){
			entities[i] = map.getEntityByName(names[i]);
		}
	}
	
	public Entity[] getEntities(){
		return copyEntities(entities);
	}
	
	public String[] getNames(){
		return copyStrings(names);
	}
	
	@Override
	public String serialize() {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<names.length;i++){
			builder.append("refEntity");
			builder.append(i);
			builder.append(":");
			builder.append(names[i]);
			if(i<(names.length-1)){
				builder.append(" ");
			}
		}
		return builder.toString();
	}

	@Override
	public void sync(Component c) {
		if(c instanceof MultipleReferenceComponent){
			MultipleReferenceComponent mrc = (MultipleReferenceComponent)c;
			if(names.length!=mrc.names.length){
				mrc.names = new String[names.length];
			}
			for(int i = 0; i<names.length;i++){
				mrc.names[i] = names[i];
			}
		}
	}

	@Override
	public Component clone(Entity entity) {
		MultipleReferenceComponent mrc = new MultipleReferenceComponent(names);
		return mrc;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof MultipleReferenceComponent){
			MultipleReferenceComponent mrc = (MultipleReferenceComponent)obj;
			if(names.length==mrc.names.length){
				boolean equal = true;
				for(int i = 0; i<names.length;i++){
					equal = equal && mrc.names[i].equals(names[i]);
				}
				return equal;
			}else{
				//System.out.println("names.length!=mrc.names.length");
				return false;
			}
		}else{
			return false;
		}
	}
	
	@Override
	public boolean hasVariable(String varname) {
		if(varname.startsWith("refEntity")){
			try{
				int i = Integer.parseInt(varname.substring("refEntity".length()));
				return (i>=0)&&(i<names.length);
			}catch(NumberFormatException e){return false;}
		}
		return false;
	}

	@Override
	public String getVariable(String varname) {
		if(varname.startsWith("refEntity")){
			try{
				int i = Integer.parseInt(varname.substring("refEntity".length()));
				return ((i>=0)&&(i<names.length))?names[i]:null;
			}catch(NumberFormatException e){return null;}
		}
		return null;
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.startsWith("refEntity")){
			try{
				int i = Integer.parseInt(varname.substring("refEntity".length()));
				if((i<names.length)){
					names[i] = value;
					return true;
				}
				ArrayList<String> list = new ArrayList<String>();
				for(String name: names){
					list.add(name);
				}
				list.add(value);
				names = list.toArray(new String[list.size()]);
				return true;
				
			}catch(NumberFormatException e){return false;}
		}
		return false;
	}
}
