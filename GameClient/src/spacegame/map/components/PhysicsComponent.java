package spacegame.map.components;

import spacegame.map.Entity;

public class PhysicsComponent extends Component
{
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;

	public final double MAX_ACCELERATION = 10; //Pixels / second
	public final double NANO = 1_000_000_000.0;
	
	public PhysicsComponent()
	{
		
	}

	public void move(long timeElapsed) 
	{
		Component position = getEntity().getComponent("Position");
		position.setDouble("posX", position.getDouble("posX") + timeElapsed/(NANO) * xVel );
		position.setDouble("posY", position.getDouble("posY") + timeElapsed/(NANO) * yVel );
	}
	
	public void accelerate(long timeElapsed)
	{
		xVel += timeElapsed/(NANO) * xAcc;
		yVel += timeElapsed/(NANO) * yAcc;
	}
	
	public void throttleAcceleration(double throttle)
	{
		Component head = getEntity().getComponent("Heading");
		double heading = head.getDouble("heading");
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
	}

	@Override
	public Component clone(Entity entity) 
	{
		PhysicsComponent clone = new PhysicsComponent();
		clone.xVel = xVel;
		clone.yVel = yVel;
		clone.xAcc = xAcc;
		clone.yAcc = yAcc;
		clone.setEntity(entity);
		return clone;
	}


	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
			case "velocityX":
			case "velocityY":
			case "heading":
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
			case "xAcc": xAcc = Double.parseDouble(value); return true;
			case "yAcc": yAcc = Double.parseDouble(value); return true;
			default: return false;
		}
	}

	@Override
	public String serialize() {
		return "velocityX:"+xVel+" velocityY:"+yVel+" xAcc:"+xAcc+" yAcc:"+yAcc;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof PhysicsComponent){
			boolean equal = true;
			PhysicsComponent p = (PhysicsComponent) obj;
			equal = equal && (xVel == p.xVel);
			equal = equal && (yVel == p.yVel);
			equal = equal && (xAcc == p.xAcc);
			equal = equal && (yAcc == p.yAcc);
			return equal;
		}else{
			return false;
		}
	}
	
}
