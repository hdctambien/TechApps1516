package spacegame.map.collision;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.Vector2;
import spacegame.map.components.Component;
import spacegame.map.components.HeadingComponent;
import spacegame.map.components.PositionComponent;

public abstract class Collider extends Component {

	private Vector2 translator;
	
	public Collider() {
		
	}
	public Collider(Entity e){
		super(e);
	}
	public Collider(Vector2 trans){
		translator = trans;
	}
	public void setTranslation(Vector2 trans){
		translator = trans;
	}
	public void setTransX(double x){
		translator = Vector2.rect(x, translator.getY());
	}
	public void setTransY(double y){
		translator = Vector2.rect(translator.getX(),y);
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
	public abstract boolean collision(Collider other);
	
	Vector2 getCenter(){
		return getPosition().add(translator);
	}
	Vector2 getPosition(){
		PositionComponent pos = (PositionComponent)getEntity().getComponent(EntityFactory.POSITION);
		return pos.getVector();
	}
	double getHeading(){
		HeadingComponent head = (HeadingComponent)getEntity().getComponent(EntityFactory.HEADING);
		return head.getHeading();
	}
}
