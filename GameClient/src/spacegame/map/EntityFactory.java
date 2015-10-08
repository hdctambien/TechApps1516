package spacegame.map;

public class EntityFactory {

	public static final String POSITION = "Position";
	public static final String PHYSICS = "Physics";
	public static final String POWER = "Power";
	public static final String FUEL = "Fuel";
	public static final String UPDATE = "Update";
	public static final String ASTEROID_UPDATE = "AsteroidUpdate";
	public static final String SHIP_UPDATE = "ShipUpdate";
	public static final String RENDER = "Render";
	
	private int ufid = 0;
	
	public EntityFactory(){
		//Wow! What a capital investment! You now have a factory that creates entities!
	}
	
	public Entity createShip(){
		Entity ship = new Entity("Ship."+ufid, ufid++);
		ship.addComponent(POSITION, new PositionComponent());
		ship.addComponent(PHYSICS, new PhysicsComponent());
		ship.addComponent(POWER, new PowerComponent());
		ship.addComponent(FUEL, new FuelComponent());
		ship.addComponent(UPDATE, new ShipUpdateComponent());
		ship.addComponent(RENDER, new RenderComponent("MayMime.png"));
		ship.createReferences();//DO NOT FORGET THIS, IT IS EXTRMEMELY IMPORTANT
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = new Entity("Asteroid."+ufid,ufid++);
		asteroid.addComponent(POSITION, new PositionComponent());
		asteroid.addComponent(PHYSICS,new PhysicsComponent());
		asteroid.addComponent(UPDATE, new AsteroidUpdateComponent());
		asteroid.addComponent(RENDER, new RenderComponent("MayMime.png"));
		asteroid.createReferences();
		return asteroid;
	}
	
	public static Entity createSerial(){
		Entity serial = new Entity("Entity",-1);
		return serial;
	}
	
}
