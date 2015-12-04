package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.Vector2;

public abstract class CollisionComponent extends Component {

	public CollisionComponent() {
		
	}
	public CollisionComponent(Entity e){
		super(e);
	}
	/*//*
	 * NOTE: default implementation throws a RuntimeException that says it isn't implemented, make sure
	 * that the subclass actually does implement it
	 * @param x X location of point
	 * @param y Y location of point
	 * @return whether the CollisionComponent contains that point
	 */
	/*public boolean containsPoint(double x, double y);
	public boolean containsPoint(Vector2 s);*/
	/**
	 * 
	 * @param sx X start location of line
	 * @param sy Y start location of line
	 * @param vx X-component of line vector
	 * @param vy Y-component of line vector
	 * @return whether the CollisionComponent intersects the line
	 */
	public abstract boolean intersectsLine(double sx, double sy, double vx, double vy);
	public abstract boolean intersectsLine(Vector2 s, Vector2 v);
	public abstract boolean intersectsCircle(double cx, double cy, double r);
	public abstract boolean intersectsCircle(Vector2 c, double r);
	public abstract boolean collision(CollisionComponent other);
}
