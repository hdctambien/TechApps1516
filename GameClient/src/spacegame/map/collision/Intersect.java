package spacegame.map.collision;

import spacegame.map.Vector2;

public class Intersect {

	public static boolean lineCircleIntersect(double sx, double sy, double vx, double vy, double cx,
			double cy, double r){
		return lineCircleIntersect(sx,sy,vx,vy,cx,cy,r,false);
	}
	public static boolean lineCircleIntersect(double sx, double sy, double vx, double vy,
			double cx,double cy, double r,boolean infinite){
		if(isVertical(vx,vy)){
			double x = sx-cx;
			return (Math.abs(x)<=r);
		}else{
			//Equations obtained from http://math.stackexchange.com/questions/228841/how-do-i-calculate-the-intersections-of-a-straight-line-and-a-circle
			//Find constants in line equation: y=mx+b
			double m = vy/vx;
			double b = -m * sx + sy;
			//Find constants in quadratic equation: Ax^2+Bx+C = 0
			double A = m*m+1;
			double B = 2*(m*(b-cy)-cx);
			double C = cy*cy-r*r+cx*cx-2*b*cy+b*b;
			//Find determinant: B^2-4AC
			double det = B*B-4*A*C;
			return (det >=0)?segmentHits(sx,vx,det,A,B)||infinite:false;
		}
		
	}
	public static boolean lineCircleIntersect(Vector2 s, Vector2 v, Vector2 c, double r,
			boolean infinite){
		if(v.isVertical()){
			double x = s.getX()-c.getX();
			return (Math.abs(x)<=r);
		}else{
			//Equations obtained from http://math.stackexchange.com/questions/228841/how-do-i-calculate-the-intersections-of-a-straight-line-and-a-circle
			//doubles for commonly used values
			double cx = c.getX();
			double cy = c.getY();
			double sx = s.getX();
			//Find constants in line equation: y=mx+b
			double m = v.getSlope();
			double b = -m * sx + s.getY();
			//Find constants in quadratic equation: Ax^2+Bx+C = 0
			double A = m*m+1;
			double B = 2*(m*(b-cy)-cx);
			double C = cy*cy-r*r+cx*cx-2*b*cy+b*b;
			//Find determinant: B^2-4AC
			double det = B*B-4*A*C;
			return (det >=0)?segmentHits(sx,v.getX(),det,A,B)||infinite:false;	
		}
	}
	public static boolean lineCircleIntersect(Vector2 s, Vector2 v, Vector2 c, double r){
		return lineCircleIntersect(s,v,c,r,false);
	}
	public static boolean linesIntersect(Vector2 s1, Vector2 v1, Vector2 s2, Vector2 v2){
		//test parallel (only case where lines may not intersect)
		Vector2 u1 = v1.getUnitVector();
		Vector2 u2 = v2.getUnitVector();
		Vector2 u2i = u2.invert();
		if(u1.equals(u2)||u1.equals(u2i)){
			//test same line (only case where parallel lines intersect (and intersect at all points)
			if(s1.equals(s2)){//unit vector will be zero, other code won't work
				return true;
			}
			Vector2 u1to2 = s2.subtract(s1).getUnitVector();
			if(u1to2.equals(u2)||u1to2.equals(u2i)){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}		
	}
	public static boolean lineSegmentsIntersect(Vector2 s1, Vector2 v1, Vector2 s2, Vector2 v2){
		//needed in multiple places
		double s1x = s1.getX();
		double s1y = s1.getY();
		double s2x = s2.getX();
		double s2y = s2.getY();
		Vector2 e1 = s1.add(v1);//Endpoint of first line segment
		Vector2 e2 = s2.add(v2);//Endpoint of 2nd line segment
		double e1x = e1.getX();
		double e1y = e1.getY();
		double e2x = e2.getX();
		double e2y = e2.getY();
		//test parallel
		Vector2 u1 = v1.getUnitVector();
		Vector2 u2 = v2.getUnitVector();
		Vector2 u2i = u2.invert();
		if(u1.equals(u2)||u1.equals(u2i)){
			//test same line (only case where parallel lines intersect (and intersect at all points)
			if(s1.equals(s2)){//unit vector will be zero, other code won't work
				return true;
			}
			Vector2 u1to2 = s2.subtract(s1).getUnitVector();
			if(u1to2.equals(u2)||u1to2.equals(u2i)){
				boolean compare = e1x>s1x;
				double lx = compare?s1x:e1x;//lower bound
				double hx = compare?e1x:s1x;//higher bound
				return ((s2x>=lx)&&(s2x<=hx))||((e2x>=lx)&&(e2x<=hx));
				//[^ABOVE^] return if s2x or e2x is between the lower and higher x bounds of line segment 1
			}else{
				return false;
			}
		}
		//Vertical cases (infinite slope won't work with the other math)
		if(v1.isVertical()){
			if(v2.isHorizontal()){
				return pointBetween(s2x,e2x,s1x)&&pointBetween(s1y,e1y,s2y);
			}
			double x = s1x;
			if(!pointBetween(s2x,e2x,x)){return false;}//No point in continuing calculations
			double v = v2.getInverseSlope();//inverse of slope (v2x/v2y)
			double y = (s1x+s2x)/v-s2y;
			return pointBetween(s1y,e1y,y);
		}
		if(v2.isVertical()){
			if(v1.isHorizontal()){
				return pointBetween(s1x,e1x,s2x)&&pointBetween(s2y,e2y,s1y);
			}
			double x = s2x;
			if(!pointBetween(s1x,e1x,x)){return false;}//No point in continuing calculations
			double v = v1.getInverseSlope();//inverse of slope (v1x/v1y)
			double y = (s2x+s1x)/v-s1y;
			return pointBetween(s2y,e2y,y);
		}
		//Regular case: find point vector <x,y> of intersection and see if it lies on both line segments
		double m1 = v1.getSlope();
		double m2 = v2.getSlope();
		double x = (s2y+m2*s2x-s1y-m1*s1x)/(m1-m2);
		return pointBetween(s1x,e1x,x)&&pointBetween(s2x,e2x,x);
	}
	/**
	 * Used by the lineSegmentsIntersect method to determine if value d3 lies between values d1 and d2 given that
	 * d1, d2, and d3 are all on the same line or axis
	 * @param d1 1st boundary value for point tested
	 * @param d2 2nd boundary value for point tested
	 * @param d3 value tested for being inbetween
	 * @return whether d3 lies between d1 and d2
	 */
	public static boolean pointBetween(double d1, double d2, double d3){
		boolean compare = d1>d2;
		double low = compare?d2:d1;//lower bound
		double high = compare?d1:d2;//higher bound
		return (d3>=low)&&(d3<=high);
	}
	/**
	 * Used by the lineCircleIntersect method to determine whether the intersection between the line
	 * and the circle is inside or outside the segment
	 * @return whether a line segment hits the quadratic given
	 */
	public static boolean segmentHits(double sx, double vx, double det, double A, double B){
		double sqrt = Math.sqrt(det);
		double x1 = (-B + sqrt)/(2*A);
		double x2 = (-B - sqrt)/(2*A);
		double x = sx+vx;
		return ((x1<x)&&(x1>sx))||((x2<x)&&(x2>sx));
	}
	
	public static boolean isVertical(double x, double y){
		return (y!=0)&&(x==0);
	}

}
