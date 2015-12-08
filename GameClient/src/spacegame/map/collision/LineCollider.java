package spacegame.map.collision;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.Vector2;
import spacegame.map.components.Component;
import spacegame.map.components.PositionComponent;

public class LineCollider extends Collider {

	private Vector2 s;
	private Vector2 v;
	
	public LineCollider() {
		
	}

	public LineCollider(Entity e) {
		super(e);
	}
	
	public LineCollider(Vector2 v){
		this.v = v;
	}
	
	public LineCollider(Vector2 v, Entity e){
		this.v = v;
	}
	
	public void recalculate(){
		s = getCenter().subtract(v.multiply(-0.5));
	}
	
	@Override
	public String serialize() {
		return "sx:"+s.getX()+" sy:"+s.getY()+" vx: "+v.getX()+" vy: "+v.getY();
	}

	@Override
	public boolean intersectsLine(double sx, double sy, double vx, double vy) {
		return Intersect.lineSegmentsIntersect(s,v,Vector2.rect(sx,sy),Vector2.rect(vx, vy));
	}

	@Override
	public boolean intersectsLine(Vector2 s2, Vector2 v2) {
		return Intersect.lineSegmentsIntersect(s, v, s2, v2);
	}

	@Override
	public boolean intersectsCircle(double cx, double cy, double r) {
		return Intersect.lineCircleIntersect(s.getX(), s.getY(), v.getX(), v.getY(), cx, cy, r);
	}

	@Override
	public boolean intersectsCircle(Vector2 c, double r) {
		return Intersect.lineCircleIntersect(s,v,c,r);
	}

	@Override
	public boolean collision(Collider other) {
		return other.intersectsLine(s, v);
	}

	@Override
	public void sync(Component c) {
		if(c instanceof LineCollider){
			LineCollider lc = (LineCollider)c;
			if(!lc.v.equals(v)){
				lc.v = v.clone();
			}
		}
	}

	@Override
	public Component clone(Entity entity) {
		return new LineCollider(v, entity);
	}

	@Override
	public boolean hasVariable(String varname) {
		switch(varname){
			case "sx": case "sy": case "vx": case "vy":
				return true;
			default:
				return false;
		}
	}

	@Override
	public String getVariable(String varname) {
		switch(varname){
			case "sx":
				return Double.toString(s.getX());
			case "sy":
				return Double.toString(s.getY());
			case "vx":
				return Double.toString(v.getX());
			case "vy":
				return Double.toString(v.getY());
			default:
				return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		switch(varname){
			case "sx":
				s = Vector2.rect(Double.parseDouble(value),s.getY());
				break;
			case "sy":
				s = Vector2.rect(s.getX(),Double.parseDouble(value));
				break;
			case "vx":
				v = Vector2.rect(Double.parseDouble(value),v.getY());
				break;
			case "vy":
				v = Vector2.rect(v.getX(),Double.parseDouble(value));
				break;
			default:
				return false;
		}
		return true;
	}

}