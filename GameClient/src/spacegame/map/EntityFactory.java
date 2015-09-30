package spacegame.map;

public class EntityFactory {

	public static final String POSITION = "Position";
	public static final String PHYSICS = "Physics";
	public static final String POWER = "Power";
	public static final String FUEL = "Fuel";
	public static final String UPDATE = "Update";
	public static final String ASTEROID_UPDATE = "AsteroidUpdate";
	public static final String SHIP_UPDATE = "ShipUpdate";
	
	private int ufid = 0;
	
	public EntityFactory(){
		//Wow! What a capital investment! You now have a factory that creates entities!
	}
	
	public Entity createShip(){
		Entity ship = new Entity("Ship."+ufid, ufid++);
		ship.addComponent("Position", new PositionComponent());
		ship.addComponent("Physics", new PhysicsComponent());
		ship.addComponent("Power", new PowerComponent());
		ship.addComponent("Fuel", new FuelComponent());
		ship.addComponent("Update", new ShipUpdateComponent());
		ship.createReferences();//DO NOT FORGET THIS, IT IS EXTRMEMELY IMPORTANT
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = new Entity("Asteroid."+ufid,ufid++);
		asteroid.addComponent("Position", new PositionComponent());
		asteroid.addComponent("Physics",new PhysicsComponent());
		asteroid.addComponent("Update", new AsteroidUpdateComponent());
		asteroid.createReferences();
		return asteroid;
	}
	
}
