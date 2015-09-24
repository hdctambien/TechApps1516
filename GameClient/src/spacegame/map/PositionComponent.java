package spacegame.map;

public class PositionComponent extends Component
{
	private double x;
	private double y;

	@Override
	public void sync(Component c) 
	{
		
	}

	@Override
	public Component clone() 
	{
		return null;
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
		case "x":
		case "y": 
			return true;
		default: return false;
		}
	}

	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
		case "x": return Double.toString(x);
		case "y": return Double.toString(y);
		default: return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
		case "x": x = Double.parseDouble(value);return true;
		case "y": y = Double.parseDouble(value);return true;
		default:return false;
		}
	}
}
