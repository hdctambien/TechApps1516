package spacegame.map.components;

import spacegame.GlobalFlags;
import spacegame.map.Entity;

public class PhysicsComponent extends Component
{
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;

	public final double MAX_ACCELERATION = 10; //Pixels / second^2
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
		xAcc = (throttle/100 * MAX_ACCELERATION) * Math.cos(heading);
		yAcc = (throttle/100 * MAX_ACCELERATION) * Math.sin(heading);
		if(GlobalFlags.DEBUG_PHYSICS){
			System.out.println("heading:"+heading+" throttle:"+throttle+ " throttle/100="+throttle/100
					+" MAX_ACC:"+MAX_ACCELERATION+" deg(heading)="+heading);
			System.out.println("xAcc:"+xAcc+" Math.cos(deg(heading))="+Math.cos(heading));
			System.out.println("xycc:"+yAcc+" Math.sin(deg(heading))="+Math.sin(heading));
			GlobalFlags.DEBUG_PHYSICS = false;
		}
	}
	
	@Override
	public void sync(Component c) 
	{
		if(c instanceof PhysicsComponent){
			PhysicsComponent physics = (PhysicsComponent)c;
			physics.xVel = xVel;
			physics.yVel = yVel;
			physics.xAcc = xAcc;
			physics.yAcc = yAcc;
		}
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
	public boolean hasDouble(String varname) 
	{
		switch(varname)
		{
			case "velocityX":
			case "velocityY":
			case "xAcc":
			case "yAcc":
				return true;
			default: return false;
		}
	}


	@Override
	public double getDouble(String varname) 
	{
		switch(varname)
		{
			case "velocityX": return xVel;
			case "velocityY": return yVel;
			case "xAcc": return xAcc;
			case "yAcc": return yAcc;
			default: return Double.NaN;
		}
	}


	@Override
	public boolean setDouble(String varname, double value) 
	{
		switch(varname)
		{
			case "velocityX": xVel = (value); return true;
			case "velocityY": yVel = (value); return true;
			case "xAcc": xAcc = (value); return true;
			case "yAcc": yAcc = (value); return true;
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
