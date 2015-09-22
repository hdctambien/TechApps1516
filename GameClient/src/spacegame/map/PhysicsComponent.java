package spacegame.map;

public class PhysicsComponent extends Component
{
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double heading;
	private double throttle;
	
	public final double MAX_ACCELERATION = 10; //Pixels / second
	
	
	@Override
	public void update(long timeElapsed) 
	{
		xAcc = (throttle/100 * MAX_ACCELERATION) * Math.cos(heading*Math.PI/180);
		yAcc = (throttle/100 * MAX_ACCELERATION) * Math.sin(heading*Math.PI/180);
		
		xVel += timeElapsed/(1_000_000_000.0) * xAcc;
		yVel += timeElapsed/(1_000_000_000.0) * yAcc;
		
		xPos += timeElapsed/(1_000_000_000.0) * xVel;
		yPos += timeElapsed/(1_000_000_000.0) * yVel;
	}

	
	@Override
	public void sync(Component c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
