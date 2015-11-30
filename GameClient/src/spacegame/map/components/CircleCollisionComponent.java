package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.Vector2;

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
	
	@Override
	public boolean containsPoint(double x, double y) {
		return (radius*radius > x*x+y*y);
	}

	@Override
	public boolean containsPoint(Vector2 s) {
		
		return false;
	}

	@Override
	public boolean intersectsLine(double sx, double sy, double vx, double vy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsLine(Vector2 s, Vector2 v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsArc(double cx, double cy, double r, double sa,
			double ea) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsArc(Vector2 c, double r, double sa, double ea) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}
}
