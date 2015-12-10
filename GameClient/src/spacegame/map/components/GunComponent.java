package spacegame.map.components;

import spacegame.GlobalFlags;
import spacegame.map.Entity;

public class GunComponent extends Component{

	private double gunHeading=3.14;
	private String imagePath = "gun.png";
	private Boolean shoot = false;

	@Override
	public String serialize() {
		return "gunHeading:"+gunHeading+" imagePath:"+imagePath;
	}

	@Override
	public void sync(Component c) {
		c.setVariable("gunHeading", Double.toString(gunHeading));		
	}

	@Override
	public Component clone(Entity entity) {
		GunComponent clone = new GunComponent();
		clone.gunHeading = gunHeading;
		clone.setEntity(entity);
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		return varname.equals("gunHeading") || varname.equals("imagePath");
	}

	@Override
	public String getVariable(String varname) 
	{
		if(varname.equals("gunHeading"))
		{
			return Double.toString(gunHeading);
		}
		else if(varname.equals("imagePath"))
		{
			return imagePath;
		}
		else
		{
			return null;
		}		
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		if(varname.equals("gunHeading")){
			gunHeading = Double.parseDouble(value);
			return true;
		}
		else if(varname.equals("imagePath"))
		{
			imagePath = value;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean hasDouble(String varname){
		return varname.equals("gunHeading");
	}
	
	public double getDouble(String varname){
		if(GlobalFlags.DEBUG_HEADING_GET_DOUBLE){
			System.out.println("HeadingComponent.getDouble("+varname+")");
			GlobalFlags.DEBUG_HEADING_GET_DOUBLE = false;
		}
		if(varname.equals("gunHeading")){
			return gunHeading;
		}else{
			return Double.NaN;
		}
	}
	
	public boolean setDouble(String varname, double value){
		//System.out.println("HeadingComponent.setDouble("+varname+","+value+")");
		if(varname.equals("gunHeading")){
			gunHeading = value;
			return true;
		}else{ return false; }
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof HeadingComponent){
			GunComponent head = (GunComponent) obj;
			return head.gunHeading == gunHeading;
		}else{return false;}
	}
	
	
}
