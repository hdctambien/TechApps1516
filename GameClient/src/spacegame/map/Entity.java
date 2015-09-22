package spacegame.map;

import java.util.ArrayList;

public class Entity {

	ArrayList<Component> components;
	public Entity(){
		
	}
	
	public void addComponent(Component c){
		components.add(c);
	}
	
	
}
