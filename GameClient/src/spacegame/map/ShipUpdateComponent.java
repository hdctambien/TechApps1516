package spacegame.map;

public class ShipUpdateComponent extends UpdateComponent{

	private PositionComponent position;
	private PhysicsComponent physics;
	private PowerComponent power;
	private FuelComponent fuel;
	
	public ShipUpdateComponent(Entity entity) {
		super(entity);
		
	}

	@Override
	public void update(long timeElapsed) {
		power.calculatePower();
		if(!fuel.isFull()){
			fuel.regenFuel(timeElapsed);
		}
		fuel.consumeFuel(timeElapsed);
		if(fuel.checkThrottle()){
			physics.throttleAcceleration(fuel.getDouble("throttle"));
		}
		physics.accelerate(timeElapsed);
		physics.move(timeElapsed);
	}

	@Override
	public Component clone(Entity entity) {
		return new ShipUpdateComponent(entity);
	}

	@Override
	public void createReferences() {
		position = (PositionComponent) getEntity().getComponent("Position");
		physics = (PhysicsComponent) getEntity().getComponent("Physics");
		power = (PowerComponent) getEntity().getComponent("Power");
		fuel = (FuelComponent) getEntity().getComponent("Fuel");		
	}

	@Override
	public String serialize() {
		return "ShipUpdate";
	}

	@Override
	public void unserialize(String serial) {
		if(serial.equals("ShipUpdate")){
			//GOOD
		}else{
			System.out.println("Serialization error with ShipUpdateComponent. String recieved: "+serial);
		}
	}



}
