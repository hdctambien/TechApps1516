package spacegame.map;

import spacegame.map.components.*;

public class EntityFactory {

	public static final String POSITION = "Position";
	public static final String PHYSICS = "Physics";
	public static final String POWER = "Power";
	public static final String FUEL = "Fuel";
	public static final String UPDATE = "Update";
	public static final String ASTEROID_UPDATE = "AsteroidUpdate";
	public static final String SHIP_UPDATE = "ShipUpdate";
	public static final String RENDER = "Render";
	public static final String HEADING = "Heading";
	
	public static final String REF_HEADER = "Ref"; //for EntityReferenceComponent
	public static final String LIST_REF_HEADER = "Listref"; //for MultipleReferenceComponent
	
	private int ufid = 0;
	
	public EntityFactory(){
		//Wow! What a capital investment! You now have a factory that creates entities!
	}
	
	public Entity createShip(){
		Entity ship = createEntity("Ship");
		ship.addComponent(POSITION, new PositionComponent());
		ship.addComponent(HEADING, new HeadingComponent());
		ship.addComponent(PHYSICS, new PhysicsComponent());
		ship.addComponent(POWER, new PowerComponent());
		ship.addComponent(FUEL, new FuelComponent());
		ship.addComponent(UPDATE, new ShipUpdateComponent());
		ship.addComponent(RENDER, new RenderComponent("Ship.png"));
		return ship;
	}
	
	public Entity createAsteroid(){
		Entity asteroid = createEntity("Asteroid");
		asteroid.addComponent(POSITION, new PositionComponent());
		asteroid.addComponent(HEADING, new HeadingComponent());
		asteroid.addComponent(PHYSICS,new PhysicsComponent());
		asteroid.addComponent(UPDATE, new AsteroidUpdateComponent());
		asteroid.addComponent(RENDER, new RenderComponent("Asteroid.png"));
		return asteroid;
	}
	
	public Entity[] createEntityReferenceSerialTest(){
		Entity test = createEntity("SerialTest");
		Entity ref = createEntity("TestRef");
		test.addComponent(HEADING, new HeadingComponent());
		ref.addComponent(POSITION, new PositionComponent());
		test.addComponent(REF_HEADER+"testSub", new EntityReferenceComponent(ref.getName(),ref));
		ref.addComponent(REF_HEADER+"testSuper", new EntityReferenceComponent(test.getName(),test));
		return new Entity[]{test,ref};
	}
	
	public Entity[] createMultipleReferenceSerialTest(){
		Entity test = createEntity("TestMaster");
		Entity ref = createEntity("TestSlave");
		Entity ref2 = createEntity("TestSlave");
		Entity ref3 = createEntity("TestSlave");
		test.addComponent(HEADING, new HeadingComponent());
		ref.addComponent(POSITION, new PositionComponent());
		ref2.addComponent(POSITION, new PositionComponent());
		ref3.addComponent(POSITION, new PositionComponent());
		test.addComponent(LIST_REF_HEADER+"testSlaves", new MultipleReferenceComponent(
				new String[]{ref.getName(),ref2.getName(),ref3.getName()},
				new Entity[]{ref,ref2,ref3}));
		ref.addComponent(REF_HEADER+"testMaster", new EntityReferenceComponent(test.getName(),test));
		return new Entity[]{test,ref,ref2,ref3};
	}
	
	private Entity createEntity(String name){
		return new Entity(name+"."+ufid,ufid++);
	}
	
	public static Entity createSerial(){
		Entity serial = new Entity("Entity",-1);
		return serial;
	}
	
}
