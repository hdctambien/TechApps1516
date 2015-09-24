package spacegame.map;

public class FuelComponent extends Component{

	private double currentFuel;
	private double maxFuel;
	private double throttle;

	private Component power;
	
	public static final double NANO = 1_000_000_000;
	public static final double FUEL_CONS_RATE = 0.1;
	public static final double FUEL_REGEN_RATE = 0.05;
	public static final double DEFAULT_MAX_FUEL = 100;
	
	public FuelComponent(Entity entity) {
		super(entity);
		maxFuel = DEFAULT_MAX_FUEL;
		power = entity.getComponent("Power");
	}
	
	public void consumeFuel(long timeElapsed){
		currentFuel -= throttle*FUEL_CONS_RATE*timeElapsed/NANO;
	}
	
	public void regenFuel(long timeElapsed){
		String fuelPowerString = power.getVariable("powerFuel");
		double powerFuel = Double.parseDouble(fuelPowerString);
		currentFuel += powerFuel*FUEL_REGEN_RATE*timeElapsed/NANO;
		if(currentFuel>maxFuel){
			currentFuel=maxFuel;
		}
	}
	
	public boolean isFull(){
		return currentFuel==maxFuel;
	}
	
	public void checkThrottle(){
		if(currentFuel == 0){
			throttle = 0;
		}else if(currentFuel<0){
			currentFuel = 0;
			throttle = 0;
		}
	}

	@Override
	public void sync(Component c) {
		FuelComponent fuel = (FuelComponent)c;
		fuel.currentFuel = currentFuel;
		fuel.maxFuel = maxFuel;
		fuel.throttle = throttle;
	}

	@Override
	public Component clone(Entity entity) {
		FuelComponent clone = new FuelComponent(entity);
		clone.currentFuel = currentFuel;
		clone.maxFuel = maxFuel;
		clone.throttle = throttle;
		return clone;
	}

	@Override
	public boolean hasVariable(String varname) {
		switch(varname){
			case "currentFuel":
			case "maxFuel":
			case "throttle":
				return true;
			default:
				return false;
		}
	}

	@Override
	public String getVariable(String varname) {
		switch(varname){
			case "currentFuel":
				return Double.toString(currentFuel);
			case "maxFuel":
				return Double.toString(maxFuel);
			case "throttle":
				return Double.toString(throttle);
			default:
				return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		switch(varname){
			case "currentFuel":
				currentFuel = Double.parseDouble(value);
				return true;
			case "maxFuel":
				maxFuel = Double.parseDouble(value);
				return true;
			case "throttle":
				throttle = Double.parseDouble(value);
				return true;
			default:
				return false;
				
		}
	}

}
