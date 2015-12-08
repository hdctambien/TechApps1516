package spacegame.map.collision;

import spacegame.map.Entity;
import spacegame.map.Vector2;
import spacegame.map.components.Component;
import spacegame.map.components.PositionComponent;

public class CircleCollider extends Collider {

	private double radius;
	
	public CircleCollider() {}
	public CircleCollider(Entity e) {super(e);}
	public CircleCollider(Vector2 trans){super(trans);}
	public CircleCollider(Vector2 trans,Entity e){super(trans,e);}

	@Override
	public String serialize() {
		return super.serial()+" radius:"+radius;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(double r){
		radius = r;
	}

	public boolean containsPoint(double x, double y) {
		Vector2 center = getCenter();
		double cx = center.getX();
		double cy = center.getY();
		x-=cx;
		y-=cy;
		return (radius*radius > x*x+y*y);
	}

	public boolean containsPoint(Vector2 s) {
		return containsPoint(s.getX(),s.getY());
	}

	@Override
	public boolean intersectsLine(double sx, double sy, double vx, double vy) {
		Vector2 center = getCenter();
		double cx = center.getX();
		double cy = center.getY();
		return Intersect.lineCircleIntersect(sx, sy, vx, vy,cx,cy, radius);
	}

	@Override
	public boolean intersectsLine(Vector2 s, Vector2 v) {
		Vector2 c = getCenter();
		return Intersect.lineCircleIntersect(s, v, c, radius);
	}

	@Override
	public boolean intersectsCircle(double cx, double cy, double r) {
		Vector2 center = getCenter();
		double c2x = center.getX();
		double c2y = center.getY();
		//distances between circle centers
		double x = c2x-cx;
		double y = c2y-cy;
		double d2 = x*x+y*y;//d squared (b/c sqrts are expensive)
		double r2 = (r+radius)*(r+radius);//total radii squared
		return d2<=r2;
	}

	@Override
	public boolean intersectsCircle(Vector2 c, double r) {
		Vector2 center = getCenter();
		double cx = center.getX();
		double cy = center.getY();
		//distances between circle centers
		double x = cx-c.getX();
		double y = cy-c.getY();
		double d2 = x*x+y*y;//d squared (b/c sqrts are expensive)
		double r2 = (r+radius)*(r+radius);//total radii squared
		return d2<=r2;
	}

	@Override
	public void sync(Component c) {
		syncTranslate(c);
		if(c instanceof CircleCollider){
			CircleCollider ccc = (CircleCollider)c;
			ccc.radius = radius;
		}
	}

	@Override
	public Component clone(Entity entity) {
		CircleCollider ccc = new CircleCollider(getTranslation(),entity);
		ccc.radius = radius;
		return ccc;
	}

	@Override
	public boolean hasVariable(String varname) {
		return varname.equals("radius")?true:super.hasVar(varname);
	}

	@Override
	public String getVariable(String varname) {
		if(varname.equals("radius")){
			return Double.toString(radius);
		}else{
			return super.getVar(varname);
		}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.equals("radius")){
			radius = Double.parseDouble(value);
			return true;
		}
		return super.setVar(varname, value);
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
	public boolean collision(Collider other) {
		return other.intersectsCircle(getCenter(), radius);
	}
}
