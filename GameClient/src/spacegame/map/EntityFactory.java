package spacegame.map;

import spacegame.map.components.AsteroidUpdateComponent;
import spacegame.map.components.FuelComponent;
import spacegame.map.components.PhysicsComponent;
import spacegame.map.components.PositionComponent;
import spacegame.map.components.PowerComponent;
import spacegame.map.components.RenderComponent;
import spacegame.map.components.ShipUpdateComponent;

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
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = new Entity("Asteroid."+ufid,ufid++);
		asteroid.addComponent(POSITION, new PositionComponent());
		asteroid.addComponent(PHYSICS,new PhysicsComponent());
		asteroid.addComponent(UPDATE, new AsteroidUpdateComponent());
		asteroid.addComponent(RENDER, new RenderComponent("MayMime.png"));
		return asteroid;
	}
	
	public static Entity createSerial(){
		Entity serial = new Entity("Entity",-1);
		return serial;
	}
	
}
