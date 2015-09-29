package spacegame.map;

public class AsteroidUpdateComponent extends UpdateComponent {

	private PhysicsComponent physics;
	
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
		return "AsteroidUpdate";
	}

	@Override
	public void unserialize(String serial) {
		if(serial.equals("AsteroidUpdate")){
			//GOOD
		}else{
			System.out.println("Serialization error with AsteroidUpdateComponent. String recieved: "+serial);
		}
	}

}
