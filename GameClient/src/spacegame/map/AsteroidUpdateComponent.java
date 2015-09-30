package spacegame.map;

public class AsteroidUpdateComponent extends UpdateComponent {

	private PhysicsComponent physics;
	
	public AsteroidUpdateComponent(){
		
	}
	
	public AsteroidUpdateComponent(Entity entity) {
		super(entity);		
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

	@Override
	public void createReferences() {
		physics = (PhysicsComponent) getEntity().getComponent("Physics");
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
