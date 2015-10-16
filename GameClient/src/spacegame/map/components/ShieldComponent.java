package spacegame.map.components;

import spacegame.GameConstants;
import spacegame.map.Entity;

public class ShieldComponent extends Component
{
	public static final double NANO = GameConstants.NANO;
	private final int MAX_SHIELD = 1500;
	private final double SHIELD_REGEN_RATE = 50.0;
	private int maxShield = MAX_SHIELD;
	private int shield;
	
	public ShieldComponent() 
	{
		maxShield = MAX_SHIELD;
	}
	
	public void takeDamage(int damageAmount)
	{
		HealthComponent health = (HealthComponent)getEntity().getComponent("shield");
		
		shield -= damageAmount;
		if(shield <= 0)
		{
			health.takeDamage(0-shield);
			shield = 0;
		}
	}
	
	public void regenShields(long timeElapsed)
	{
		Component power = getEntity().getComponent("Power");
		
		String shieldPowerString = power.getVariable("powerShield");
		double powerShield = Double.parseDouble(shieldPowerString);
		shield += powerShield*SHIELD_REGEN_RATE*timeElapsed/NANO;
		if(shield>maxShield){
			shield=maxShield;
		}
	}

	@Override
	public void sync(Component c) 
	{
		c.setVariable("maxShield", Integer.toString(maxShield));
		c.setVariable("shield", Integer.toString(shield));
	}

	@Override
	public Component clone(Entity entity) 
	{
		ShieldComponent clone = new ShieldComponent();
		clone.maxShield = maxShield;
		clone.shield = shield;
		clone.setEntity(entity);
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) 
	{
		switch(varname)
		{
			case "maxShield"       :
			case "shield"          :
			case "shieldRegenRate" :
				return true;
			default: return false;
		}
	}

	@Override
	public String getVariable(String varname) 
	{
		switch(varname)
		{
			case "maxShield"       : return Integer.toString(maxShield);
			case "shield"          : return Integer.toString(shield);
			default: return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		switch(varname)
		{
			case "maxShield"       : maxShield = Integer.parseInt(value);
			case "shield"          : shield = Integer.parseInt(value);
				return true;
			default: return false;
		}
	}

	@Override
	public String serialize() 
	{
		return "maxShield:"+maxShield+" shield:"+shield;
	}
}
