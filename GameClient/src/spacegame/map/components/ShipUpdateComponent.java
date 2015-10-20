package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;

public class ShipUpdateComponent extends UpdateComponent{
	
	public ShipUpdateComponent(){
		
	}
	
	public ShipUpdateComponent(Entity entity) {
		super(entity);
		
	}

	@Override
	public void update(long timeElapsed) {
		//Get necessary Components from Entity
		PhysicsComponent physics = (PhysicsComponent) getEntity().getComponent("Physics");
		PowerComponent power = (PowerComponent) getEntity().getComponent("Power");
		FuelComponent fuel = (FuelComponent) getEntity().getComponent("Fuel");
		
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
	public String serialize() {
		return EntityFactory.SHIP_UPDATE;
	}
	
	@Override
	public boolean equals(Object obj){
		return (obj instanceof ShipUpdateComponent);
	}
	
}
