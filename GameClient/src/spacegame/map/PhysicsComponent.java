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
	
	public void move(long timeElapsed) 
	{
		Component position = getEntity().getComponent("Position");
		xPos += timeElapsed/(1_000_000_000.0) * xVel;
		yPos += timeElapsed/(1_000_000_000.0) * yVel;
	}
	
	public void accelerate(long timeElapsed)
	{
		xVel += timeElapsed/(1_000_000_000.0) * xAcc;
		yVel += timeElapsed/(1_000_000_000.0) * yAcc;
	}
	
	public void throttleAcceleration()
	{
		xAcc = (throttle/100 * MAX_ACCELERATION) * Math.cos(heading*Math.PI/180);
		yAcc = (throttle/100 * MAX_ACCELERATION) * Math.sin(heading*Math.PI/180);
	}
	
	@Override
	public void sync(Component c) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component clone() 
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
			case "velocityX":
			case "velocityY":
			case "heading":
			case "posX":
			case "posY":
			case "throttle":
				return true;
			default: return false;
		}
	}


	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
			case "velocityX": return Double.toString(xVel);
			case "velocityY": return Double.toString(yVel);
			case "heading": return Double.toString(heading);
			case "posX": return Double.toString(xPos);
			case "posY": return Double.toString(yPos);
			case "throttle": return Double.toString(throttle);
			default: return null;
		}
	}


	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
			case "velocityX": xVel = Double.parseDouble(value); return true;
			case "velocityY": yVel = Double.parseDouble(value); return true;
			case "heading": heading = Double.parseDouble(value); return true;
			case "posX": xPos = Double.parseDouble(value); return true;
			case "posY": yPos = Double.parseDouble(value); return true;
			case "throttle": throttle = Double.parseDouble(value); return true;
			default: return false;
		}
	}
	
}
