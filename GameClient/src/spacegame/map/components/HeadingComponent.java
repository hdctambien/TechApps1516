package spacegame.map.components;

import spacegame.map.Entity;

public class HeadingComponent extends Component{

	private double heading;

	@Override
	public String serialize() {
		return "heading:"+heading;
	}

	@Override
	public void sync(Component c) {
		c.setVariable("heading", Double.toString(heading));		
	}

	@Override
	public Component clone(Entity entity) {
		HeadingComponent clone = new HeadingComponent();
		clone.heading = heading;
		clone.setEntity(entity);
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) {
		return varname.equals("heading");
	}

	@Override
	public String getVariable(String varname) {
		if(varname.equals("heading")){
			return Double.toString(heading);
		}else{
			return null;
		}		
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.equals("heading")){
			heading = Double.parseDouble(value);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasDouble(String varname){
		return varname.equals("heading");
	}
	
	public double getDouble(String varname){
		if(varname.equals("heading")){
			return heading;
		}else{
			return Double.NaN;
		}
	}
	
	public boolean setDouble(String varname, double value){
		if(varname.equals("heading")){
			heading = value;
			return true;
		}else{ return false; }
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof HeadingComponent){
			HeadingComponent head = (HeadingComponent) obj;
			return head.heading == heading;
		}else{return false;}
	}
	
	
}
