package spacegame.map;

public class Ship {

	public static final double SHEILD_RADIUS = 2.0;
	private double velocityX;
	private double velocityY;
	private double heading;
	private double posX;
	private double posY;
	private boolean hasLink;
	private double throttle;
	
	private int shield;
	private int health;
	private int power;
	private int powerComms;
	private int powerFuel;
	private int powerShield;
	private int powerGuns;
	private int currentFuel;
	private int maxFuel;
	private int maxShield;
	private int maxHealth;
	
	public double getX() {
		return posX;
	}
	public double getY() {
		return posY;
	}
	public double getRadius() {
		return SHEILD_RADIUS;
	}
	public double getVelocityX() {
		return velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public double getHeading(){
		return heading;
	}
	public boolean hasLink(){
		return hasLink;
	}
	public double getThrottle(){
		return throttle;
	}
	public int getShield(){
		return shield;
	}
	public int getHealth(){
		return health;
	}
	public int getPower(){
		return power;
	}
	public int getPowerComms(){
		return powerComms;
	}
	public int getPowerFuel(){
		return powerFuel;
	}
	public int getPowerShield(){
		return powerShield;
	}
	public int getPowerGuns(){
		return powerGuns;
	}
	public int getCurrentFuel(){
		return currentFuel;
	}
	public int getMaxFuel(){
		return maxFuel;
	}
	public int getMaxShield(){
		return maxShield;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public String serialize() {
		return velocityX+","+velocityY+","+heading+","+posX+","+posY+","+hasLink+","+throttle+","+shield+","+
				health+","+power+","+powerComms+","+powerFuel+","+powerShield+","+powerGuns+","+currentFuel+","+
				maxFuel+","+maxShield+","+maxHealth;
	}
	public void unserialize(String data) {
		String[] pieces = data.split(",");
		if(pieces.length<18){
			System.err.println("Unserialization failed in class Ship!");
		}else{
			velocityX = Double.parseDouble(pieces[0]);
			velocityY = Double.parseDouble(pieces[1]);
			heading = Double.parseDouble(pieces[2]);
			posX = Double.parseDouble(pieces[3]);
			posY = Double.parseDouble(pieces[4]);
			hasLink = Boolean.parseBoolean(pieces[5]);
			throttle = Double.parseDouble(pieces[6]);
			shield = Integer.parseInt(pieces[7]);
			health = Integer.parseInt(pieces[8]);
			power = Integer.parseInt(pieces[9]);
			powerComms = Integer.parseInt(pieces[10]);
			powerFuel = Integer.parseInt(pieces[11]);
			powerShield = Integer.parseInt(pieces[12]);
			powerGuns = Integer.parseInt(pieces[13]);
			currentFuel = Integer.parseInt(pieces[14]);
			maxFuel = Integer.parseInt(pieces[15]);
			maxShield = Integer.parseInt(pieces[16]);
			maxHealth = Integer.parseInt(pieces[17]);
		}		
	}
}
