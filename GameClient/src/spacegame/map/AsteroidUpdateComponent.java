package spacegame.map;

public class AsteroidUpdateComponent extends UpdateComponent {

	private PhysicsComponent physics;
	
	public AsteroidUpdateComponent(Entity entity) {
		super(entity);
		physics = (PhysicsComponent) entity.getComponent("Physics");
	}

	@Override
	public void update(long timeElapsed) {
		physics.accelerate(timeElapsed);
		physics.move(timeElapsed);		
	}

	@Override
	public Component clone(Entity entity) {
		return new AsteroidUpdateComponent(entity);
	}

}
