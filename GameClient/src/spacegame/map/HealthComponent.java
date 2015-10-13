package spacegame.map;

public class HealthComponent extends Component
{
	public final int MAX_HEALTH = 500;
	private int health;
	
	public HealthComponent()
	{
		health = MAX_HEALTH;
	}
	
	public void takeDamage(int damageAmount)
	{
		health -= damageAmount;
	}
	
	public boolean isAlive()
	{
		if(health >0)
			return true;
		return false;
	}

	@Override
	public String serialize() 
	{
		return "health:"+health;
	}

	@Override
	public void sync(Component c) 
	{
		c.setVariable("health", Integer.toString(health));
	}

	@Override
	public Component clone(Entity entity) 
	{
		HealthComponent clone = new HealthComponent();
		clone.setVariable("health", Integer.toString(health));
		clone.setEntity(entity);
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
			case "health":
				return true;
			default: 
				return false;
		}
	}

	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
			case "health":
				return Integer.toString(health);
			default: 
				return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
			case "health": health = Integer.parseInt(value);
				return true;
			default: 
				return false;
		}
	}	
}
