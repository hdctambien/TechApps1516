package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.Vector2;

public class PositionComponent extends Component
{
	private double posX;
	private double posY;
	
	public PositionComponent(){
		posX = 0;
		posY = 0;
	}

	@Override
	public void sync(Component c) 
	{
		c.setVariable("posX", Double.toString(posX));
		c.setVariable("posY", Double.toString(posY));
	}

	@Override
	public Component clone(Entity entity) 
	{
		PositionComponent position = new PositionComponent();
		position.posX = posX;
		position.posY = posY;
		position.setEntity(entity);
		return position;
	}
	
	public double getX(){
		return posX;
	}
	
	public double getY(){
		return posY;
	}
	
	public Vector2 getVector(){
		return Vector2.rect(posX, posY);
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
		case "posX":
		case "posY": 
			return true;
		default: return false;
		}
	}

	@Override
	public boolean hasDouble(String varname){
		switch(varname){
			case "posX": case "posY": return true;
			default: return false;
		}
	}
	
	@Override
	public double getDouble(String varname){
		switch(varname){
			case "posX": return posX;
			case "posY": return posY;
			default: return Double.NaN;
		}
	}
	
	@Override
	public boolean setDouble(String varname,double value){
		switch(varname){
			case "posX": posX = value; return true;
			case "posY": posY = value; return true;
			default: return false;
		}
	}
	
	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
		case "posX": return Double.toString(posX);
		case "posY": return Double.toString(posY);
		default: return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
		case "posX": posX = Double.parseDouble(value);return true;
		case "posY": posY = Double.parseDouble(value);return true;
		default:return false;
		}
	}

	@Override
	public String serialize() {
		return "posX:"+posX+" posY:"+posY;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof PositionComponent){
			boolean equal = true;
			PositionComponent p = (PositionComponent) obj;
			equal = equal && (posX == p.posX);
			equal = equal && (posY == p.posY);
			return equal;
		}else{
			return false;
		}
	}
	
}
