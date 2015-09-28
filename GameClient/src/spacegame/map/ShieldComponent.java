package spacegame.map;

public class ShieldComponent extends Component
{
	public static final double NANO = 1_000_000_000;
	private final int MAX_SHIELD = 1500;
	private final double SHIELD_REGEN_RATE = 50.0;
	private int maxShield = MAX_SHIELD;
	private double shieldRegenRate = SHIELD_REGEN_RATE;
	private int shield;
	
	private Component power;
	private Component health;
	
	public ShieldComponent(Entity entity) 
	{
		super(entity);
		maxShield = MAX_SHIELD;
		shieldRegenRate = SHIELD_REGEN_RATE;
	}
	
	public void takeDamage(int damageAmount)
	{
		//TODO
	}
	

	@Override
	public void sync(Component c) 
	{
		c.setVariable("maxShield", Integer.toString(maxShield));
		c.setVariable("shield", Integer.toString(shield));
		c.setVariable("shieldRegenRate", Double.toString(shieldRegenRate));
	}

	@Override
	public Component clone(Entity entity) 
	{
		ShieldComponent clone = new ShieldComponent(entity);
		clone.maxShield = maxShield;
		clone.shield = shield;
		clone.shieldRegenRate = shieldRegenRate;
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
			case "shieldRegenRate" : return Double.toString(shieldRegenRate);
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
			case "shieldRegenRate" : shieldRegenRate = Double.parseDouble(value);
				return true;
			default: return false;
		}
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unserialize(String serial) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createReferences() {
		power = getEntity().getComponent("Power");
		health = getEntity().getComponent("shield");		
	}

}
