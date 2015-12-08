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
		translator = new Vector2();
	}
	public Collider(Entity e){
		super(e);
		translator = new Vector2();
	}
	public Collider(Vector2 trans){
		translator = trans;
	}
	public Collider(Vector2 trans, Entity e){
		super(e);
		translator = trans;
	}
	public Vector2 getTranslation(){
		return translator;
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
	
	public boolean hasVar(String varname){
		switch(varname){
			case "tx": case "ty": return true;
			default: return false;
		}
	}
	public String getVar(String varname){
		switch(varname){
			case "tx": return Double.toString(translator.getX());
			case "ty": return Double.toString(translator.getY());
			default: return null;
		}	
	}
	public boolean setVar(String varname, String value){
		switch(varname){
			case "tx": 
				translator = Vector2.rect(Double.parseDouble(value),translator.getY());
				break;
			case "ty": 
				translator = Vector2.rect(Double.parseDouble(value),translator.getY());
				break;
			default: return false;
		}
		return true;
	}
	public String serial(){
		return "tx:"+translator.getX()+" ty:"+translator.getY();
	}
	public void syncTranslate(Component other){
		if(other instanceof Collider){
			Collider c = (Collider)other;
			c.translator = translator.clone();
		}
	}
	
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
