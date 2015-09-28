package spacegame.map;

public class EntityFactory {

	private int ufid = 0;
	
	public EntityFactory(){
		//Wow! What a capital investment! You now have a factory that creates entities!
	}
	
	public Entity createShip(){
		Entity ship = new Entity("Ship"+ufid, ufid++);
		ship.addComponent("Position", new PositionComponent(ship));
		ship.addComponent("Physics", new PhysicsComponent(ship));
		ship.addComponent("Power", new PowerComponent(ship));
		ship.addComponent("Fuel", new FuelComponent(ship));
		ship.addComponent("Update", new ShipUpdateComponent(ship));
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = new Entity("Asteroid"+ufid,ufid++);
		asteroid.addComponent("Position", new PositionComponent(asteroid));
		asteroid.addComponent("Physics",new PhysicsComponent(asteroid));
		
		return asteroid;
	}
	
}
