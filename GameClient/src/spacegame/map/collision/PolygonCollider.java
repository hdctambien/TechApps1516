package spacegame.map.collision;

import java.util.ArrayList;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.Vector2;
import spacegame.map.components.Component;
import spacegame.map.components.HeadingComponent;
import spacegame.map.components.PositionComponent;

public class PolygonCollider extends Collider {

	private ArrayList<Vector2> absolutePoints;
	private ArrayList<Vector2> relativePoints;
	
	public PolygonCollider(){
		absolutePoints = new ArrayList<Vector2>();
		relativePoints = new ArrayList<Vector2>();
	}
	public PolygonCollider(Entity e){
		super(e);
		absolutePoints = new ArrayList<Vector2>();
		relativePoints = new ArrayList<Vector2>();
	}
	public PolygonCollider(ArrayList<Vector2> points, Vector2 trans){
		super(trans);
		absolutePoints = new ArrayList<Vector2>();
		relativePoints = (ArrayList<Vector2>) points.clone();
	}
	public PolygonCollider(ArrayList<Vector2> points, Vector2 trans, Entity e){
		super(trans,e);
		absolutePoints = new ArrayList<Vector2>();
		relativePoints = (ArrayList<Vector2>) points.clone();
	}
	
	@Override
	public String serialize() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<relativePoints.size();i++){
			builder.append("px");
			builder.append(i);
			builder.append(':');
			builder.append(relativePoints.get(i).getX());
			builder.append(" py");
			builder.append(i);
			builder.append(':');
			builder.append(relativePoints.get(i).getX());
			builder.append(' ');
		}
		return builder.toString().trim();
	}

	@Override
	public boolean intersectsLine(double sx, double sy, double vx, double vy) {
		Vector2 s = Vector2.rect(sx, sy);
		Vector2 v = Vector2.rect(vx, vy);
		return intersectsLine(s,v);
	}

	@Override
	public boolean intersectsLine(Vector2 s, Vector2 v) {
		for(int i = 0; i < absolutePoints.size(); i++){
			Vector2 p = absolutePoints.get(i);
			Vector2 vl;
			if(i+1==absolutePoints.size()){
				vl = absolutePoints.get(0).subtract(p);
			}else{
				vl = absolutePoints.get(i+1).subtract(p);
			}
			if(Intersect.lineSegmentsIntersect(p, vl, s, v)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersectsCircle(double cx, double cy, double r) {
		for(int i = 0; i < absolutePoints.size(); i++){
			Vector2 p = absolutePoints.get(i);
			Vector2 vl;
			if(i+1==absolutePoints.size()){
				vl = absolutePoints.get(0).subtract(p);
			}else{
				vl = absolutePoints.get(i+1).subtract(p);
			}
			if(Intersect.lineCircleIntersect(p.getX(), p.getY(), vl.getX(), vl.getY(), cx, cy, r)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersectsCircle(Vector2 c, double r) {
		return intersectsCircle(c.getX(),c.getY(),r);
	}

	@Override
	public boolean collision(Collider other) {
		for(int i = 0; i < absolutePoints.size(); i++){
			Vector2 p = absolutePoints.get(i);
			Vector2 vl;
			if(i+1==absolutePoints.size()){
				vl = absolutePoints.get(0).subtract(p);
			}else{
				vl = absolutePoints.get(i+1).subtract(p);
			}
			if(other.intersectsLine(p, vl)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void sync(Component c) {
		PolygonCollider pcc = (PolygonCollider)c;
		if(!pcc.relativePoints.equals(relativePoints)){
			pcc.relativePoints = (ArrayList<Vector2>) relativePoints.clone();
		}
	}

	@Override
	public Component clone(Entity entity) {
		return new PolygonCollider(relativePoints,getTranslation(),entity);
	}

	@Override
	public boolean hasVariable(String varname) {
		if(varname.startsWith("px")&&varname.length()>2){
			String raw=varname.substring(2);
			return Integer.parseInt(raw)<relativePoints.size();
		}else if(varname.startsWith("py")&&varname.length()>2){
			String raw=varname.substring(2);
			return Integer.parseInt(raw)<relativePoints.size();
		}else{
			return false;
		}
	}

	@Override
	public String getVariable(String varname) {
		if(varname.startsWith("px")&&varname.length()>2){
			String raw=varname.substring(2);
			int index = Integer.parseInt(raw);
			if(index<relativePoints.size()){
				return Double.toString(relativePoints.get(index).getX());
			}			
		}else if(varname.startsWith("py")&&varname.length()>2){
			String raw=varname.substring(2);
			int index = Integer.parseInt(raw);
			if(index<relativePoints.size()){
				return Double.toString(relativePoints.get(index).getY());
			}
		}
		return null;
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.startsWith("px")&&varname.length()>2){
			String raw=varname.substring(2);
			int index = Integer.parseInt(raw);
			double y = (index<relativePoints.size())?relativePoints.get(index).getY():0;
			double x = Double.parseDouble(value);
			relativePoints.set(index, Vector2.rect(x,y));
		}else if(varname.startsWith("py")&&varname.length()>2){
			String raw=varname.substring(2);
			int index = Integer.parseInt(raw);
			double x = (index<relativePoints.size())?relativePoints.get(index).getX():0;
			double y = Double.parseDouble(value);
			relativePoints.set(index, Vector2.rect(x,y));
		}
		return false;
	}
	public void recalculatePoints(){
		Vector2 c = getCenter();
		double h = getHeading();
		for(int i = 0; i<relativePoints.size();i++){
			Vector2 p = relativePoints.get(i);
			Vector2 r = p.addT(h);//rotate by heading
			Vector2 a = r.add(c);//translate by position center
			absolutePoints.set(i, a);
		}
	}
}
