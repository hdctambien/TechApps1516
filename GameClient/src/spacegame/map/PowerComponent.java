package spacegame.map;

public class PowerComponent extends Component
{
	private double power; //Arbitrary or? Powerups? Other things? etc etc
	private double powerComms;
	private double powerFuel;
	private double powerShield;
	private double powerGuns;
	
	@Override
	public void update(long timeElapsed) 
	{
		if(powerComms/100 + powerFuel/100 + powerShield/100 + powerGuns/100 > 1)
		{
			//TODO: POWER OVERLOAD or something
			powerComms = 0;
			powerFuel = 0;
			powerShield = 0;
			powerGuns = 0;
		}
		
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

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
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
			case "powerComms" : return Double.toString(powerComms);
			case "powerFuel " : return Double.toString(powerFuel);
			case "powerShield": return Double.toString(powerShield);
			case "powerGuns"  : return Double.toString(powerGuns);
			default: return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
			case "powerComms" : powerComms = Double.parseDouble(varname); return true;
			case "powerFuel " : powerFuel = Double.parseDouble(varname); return true;
			case "powerShield": powerShield = Double.parseDouble(varname); return true;
			case "powerGuns"  : powerGuns = Double.parseDouble(varname); return true;
			default: return false;
		}
	}
	
}
