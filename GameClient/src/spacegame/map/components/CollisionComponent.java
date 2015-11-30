package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.Vector2;

public abstract class CollisionComponent extends Component {

	public CollisionComponent() {
		
	}
	public CollisionComponent(Entity e){
		super(e);
	}
	/**
	 * 
	 * @param x X location of point
	 * @param y Y location of point
	 * @return whether the CollisionComponent contains that point
	 */
	public abstract boolean containsPoint(double x, double y);
	public abstract boolean containsPoint(Vector2 s);
	/**
	 * 
	 * @param sx X start location of line
	 * @param sy Y start location of line
	 * @param vx X-component of line vector
	 * @param vy Y-component of line vector
	 * @return whether the CollisionComponent intersects the arc
	 */
	public abstract boolean intersectsLine(double sx, double sy, double vx, double vy);
	public abstract boolean intersectsLine(Vector2 s, Vector2 v);
	public abstract boolean intersectsArc(double cx, double cy, double r, double sa, double ea);
	public abstract boolean intersectsArc(Vector2 c, double r, double sa, double ea);
	public abstract boolean collision(CollisionComponent other);
}
