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
		power = entity.getComponent("Power");
		health = entity.getComponent("shield");
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVariable(String varname) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setVariable(String varname, String value) 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
