package spacegame.map;

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
