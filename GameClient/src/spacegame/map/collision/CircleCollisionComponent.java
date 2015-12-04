package spacegame.map.collision;

import spacegame.map.Entity;
import spacegame.map.Vector2;
import spacegame.map.components.Component;
import spacegame.map.components.PositionComponent;

public class CircleCollisionComponent extends CollisionComponent {

	private double radius;
	
	public CircleCollisionComponent() {}
	public CircleCollisionComponent(Entity e) {super(e);}

	@Override
	public String serialize() {
		return "radius:"+radius;
	}

	private PositionComponent getPos(){
		return (PositionComponent) getEntity().getComponent("Position");
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(double r){
		radius = r;
	}

	public boolean containsPoint(double x, double y) {
		PositionComponent pos = getPos();
		x-=pos.getDouble("posX");
		y-=pos.getDouble("posY");
		return (radius*radius > x*x+y*y);
	}

	public boolean containsPoint(Vector2 s) {
		return containsPoint(s.getX(),s.getY());
	}
	
	public Vector2 getCenter(){
		PositionComponent pos = getPos();
		return new Vector2(pos.getDouble("posX"),pos.getDouble("posY"),Vector2.FLAG_RECT);
	}

	@Override
	public boolean intersectsLine(double sx, double sy, double vx, double vy) {
		PositionComponent pos = getPos();
		double cx = pos.getDouble("posX");
		double cy = pos.getDouble("posY");
		return Intersect.lineCircleIntersect(sx, sy, vx, vy,cx,cy, radius);
	}

	@Override
	public boolean intersectsLine(Vector2 s, Vector2 v) {
		Vector2 c = getCenter();
		return Intersect.lineCircleIntersect(s, v, c, radius);
	}

	@Override
	public boolean intersectsCircle(double cx, double cy, double r) {
		PositionComponent pos = getPos();
		double c2x = pos.getDouble("posX");
		double c2y = pos.getDouble("posY");
		//distances between circle centers
		double x = c2x-cx;
		double y = c2y-cy;
		double d2 = x*x+y*y;//d squared (b/c sqrts are expensive)
		double r2 = (r+radius)*(r+radius);//total radii squared
		return d2<=r2;
	}

	@Override
	public boolean intersectsCircle(Vector2 c, double r) {
		PositionComponent pos = getPos();
		double cx = pos.getDouble("posX");
		double cy = pos.getDouble("posY");
		//distances between circle centers
		double x = cx-c.getX();
		double y = cy-c.getY();
		double d2 = x*x+y*y;//d squared (b/c sqrts are expensive)
		double r2 = (r+radius)*(r+radius);//total radii squared
		return d2<=r2;
	}

	@Override
	public void sync(Component c) {
		if(c instanceof CircleCollisionComponent){
			CircleCollisionComponent ccc = (CircleCollisionComponent)c;
			ccc.radius = radius;
		}
	}

	@Override
	public Component clone(Entity entity) {
		CircleCollisionComponent ccc = new CircleCollisionComponent(entity);
		ccc.radius = radius;
		return ccc;
	}

	@Override
	public boolean hasVariable(String varname) {
		return varname.equals("radius");
	}

	@Override
	public String getVariable(String varname) {
		if(varname.equals("radius")){
			return Double.toString(radius);
		}else{
			return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.equals("radius")){
			radius = Double.parseDouble(value);
			return true;
		}
		return false;
	}

	public double getDouble(String varname){
		if(varname.equals("radius")){
			return radius;
		}
		return super.getDouble(varname);
	}
	public boolean setDouble(String varname,double value){
		if(varname.equals("radius")){
			radius = value;
			return true;
		}
		return false;
	}
	@Override
	public boolean collision(CollisionComponent other) {
		return other.intersectsCircle(getCenter(), radius);
	}
}
