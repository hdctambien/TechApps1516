package spacegame.map;

public class PhysicsComponent extends Component
{
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double heading;
	private Component position;
	
	public final double MAX_ACCELERATION = 10; //Pixels / second
	
	public PhysicsComponent(Entity entity) 
	{
		super(entity);
		position = getEntity().getComponent("Position");
	}

	public void move(long timeElapsed) 
	{
		position.setVariable("posX", position.getVariable("posX") + timeElapsed/(1_000_000_000.0) * xVel );
		position.setVariable("posY", position.getVariable("posY") + timeElapsed/(1_000_000_000.0) * yVel );
	}
	
	public void accelerate(long timeElapsed)
	{
		xVel += timeElapsed/(1_000_000_000.0) * xAcc;
		yVel += timeElapsed/(1_000_000_000.0) * yAcc;
	}
	
	public void throttleAcceleration(double throttle)
	{
		xAcc = (throttle/100 * MAX_ACCELERATION) * Math.cos(heading*Math.PI/180);
		yAcc = (throttle/100 * MAX_ACCELERATION) * Math.sin(heading*Math.PI/180);
	}
	
	@Override
	public void sync(Component c) 
	{
		c.setVariable("xVel", Double.toString(xVel));
		c.setVariable("yVel", Double.toString(yVel));
		c.setVariable("xAcc", Double.toString(xAcc));
		c.setVariable("yAcc", Double.toString(yAcc));
		c.setVariable("heading", Double.toString(heading));
	}

	@Override
	public Component clone(Entity entity) 
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
			case "throttle":
			case "xAcc":
			case "yAcc":
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
			case "xAcc": return Double.toString(xAcc);
			case "yAcc": return Double.toString(yAcc);
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
			case "xAcc": xAcc = Double.parseDouble(value); return true;
			case "yAcc": yAcc = Double.parseDouble(value); return true;
			default: return false;
		}
	}
	
}
