package spacegame.map;

public class Ship implements Collidable {

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
	
	@Override
	public double getX() {
		return posX;
	}
	@Override
	public double getY() {
		return posY;
	}
	@Override
	public double getRadius() {
		return SHEILD_RADIUS;
	}
	@Override
	public double getVelocityX() {
		return velocityX;
	}
	@Override
	public double getVelocityY() {
		return velocityY;
	}
}
