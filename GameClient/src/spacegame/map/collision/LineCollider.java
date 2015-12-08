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
		//TODO: figure out if line like enities' position will be their middle (assumed)
		//TODO: or end
		//if middle,
		s = getPosition().subtract(v.multiply(-0.5));
		//if end, 
		//s = getPosition()
	}

	private Vector2 getPosition(){
		PositionComponent pos = (PositionComponent)getEntity().getComponent(EntityFactory.POSITION);
		return pos.getVector();
	}
	
	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsCircle(Vector2 c, double r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean collision(Collider other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sync(Component c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component clone(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasVariable(String varname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVariable(String varname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setVariable(String varname, String value) {
		// TODO Auto-generated method stub
		return false;
	}

}
