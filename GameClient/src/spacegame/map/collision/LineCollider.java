package spacegame.map.collision;

import spacegame.map.Entity;
import spacegame.map.Vector2;
import spacegame.map.components.Component;

public class LineCollider extends Collider {

	public LineCollider() {
	}

	public LineCollider(Entity e) {
		super(e);
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
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
