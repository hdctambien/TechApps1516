package spacegame.map;

public class PowerComponent extends Component
{
	private int power;
	private int powerComms;
	private int powerFuel;
	private int powerShield;
	private int powerGuns;
	
	public PowerComponent(Entity entity) 
	{
		super(entity);
	}

	public void calculatePower()
	{
		if(powerComms + powerFuel + powerShield + powerGuns > power)
		{
			powerComms = 0;
			powerFuel = 0;
			powerShield = 0;
			powerGuns = 0;
		}
	}

	@Override
	public void sync(Component c) 
	{
		c.setVariable("powerComms", Integer.toString(powerComms));
		c.setVariable("powerFuel", Integer.toString(powerFuel));
		c.setVariable("powerShield", Integer.toString(powerShield));
		c.setVariable("powerGuns", Integer.toString(powerGuns));
		c.setVariable("power", Integer.toString(power));
	}

	@Override
	public Component clone(Entity entity) 
	{
		PowerComponent clone = new PowerComponent(entity);
		clone.powerComms = powerComms;
		clone.powerFuel = powerFuel;
		clone.powerGuns = powerGuns;
		clone.powerShield = powerShield;
		clone.power = power;
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
			case "power":
			case "powerComms" :
			case "powerFuel " :
			case "powerShield":
			case "powerGuns"  :
				return true;
			default: return false;
		}
	}

	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
			case "power": return Integer.toString(power);
			case "powerComms" : return Integer.toString(powerComms);
			case "powerFuel " : return Integer.toString(powerFuel);
			case "powerShield": return Integer.toString(powerShield);
			case "powerGuns"  : return Integer.toString(powerGuns);
			default: return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
			case "power": power = Integer.parseInt(value); return true;
			case "powerComms" : powerComms = Integer.parseInt(value); return true;
			case "powerFuel " : powerFuel = Integer.parseInt(value); return true;
			case "powerShield": powerShield = Integer.parseInt(value); return true;
			case "powerGuns"  : powerGuns = Integer.parseInt(value); return true;
			default: return false;
		}
	}
	
}
