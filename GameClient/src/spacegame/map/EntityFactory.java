package spacegame.map;

public class EntityFactory {

	private int ufid = 0;
	
	public EntityFactory(){
		//Wow! What a capital investment! You now have a factory that creates entities!
	}
	
	public Entity createShip(){
		Entity ship = new Entity("Ship"+ufid, ufid++);
		//ship.addComponent("Physics",new PhysicsComponent());
		
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = new Entity("Asteroid"+ufid,ufid++);
		//asteroid.addComponent("Physics",new PhysicsComponent());
		
		return asteroid;
	}
	
}
