package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;

public class AsteroidUpdateComponent extends UpdateComponent {
	
	public AsteroidUpdateComponent(){
		
	}
	
	public AsteroidUpdateComponent(Entity entity) {
		super(entity);		
	}

	@Override
	public void update(long timeElapsed) {
		PhysicsComponent physics = (PhysicsComponent) getEntity().getComponent("Physics");
		physics.accelerate(timeElapsed);
		physics.move(timeElapsed);		
	}

	@Override
	public Component clone(Entity entity) {
		return new AsteroidUpdateComponent(entity);
	}

	@Override
	public String serialize() {
		return EntityFactory.ASTEROID_UPDATE;
	}

	@Override
	public boolean equals(Object obj){
		return (obj instanceof AsteroidUpdateComponent);
	}

}
