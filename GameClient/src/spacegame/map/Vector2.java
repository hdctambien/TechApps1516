package spacegame.map;

public class Vector2 {

	private double x;
	private double y; 
	private double r; //radius
	private double t; //angle (theta)
	
	private static double ERROR_TOLERANCE = 0.000_000_001;
	public static final double MAX_ERROR_TOLERANCE = 0.1;
	
	public static final int FLAG_POLAR = 0b10;
	public static final int FLAG_RECT = 0b01;
	public static final int FLAG_BOTH = 0b11;
	public static final int FLAG_NEITHER = 0b00;//used for comparisons between vectors
	
	private int flag;
	
	public Vector2(){
		x=0;
		y=0;
		r=0;
		t=0;
		flag = FLAG_BOTH;
	}
	
	public Vector2(double a, double b,int flag) {
		switch(flag){
			case FLAG_POLAR:
				r=a;
				t=b;
				break;
			case FLAG_RECT:
				x=a;
				y=b;
				break;
		}		
		this.flag = flag;
	}
	public Vector2(double x, double y, double r, double t){
		this.x=x;
		this.y=y;
		this.r=r;
		this.t=t;
		flag = FLAG_BOTH;
	}
	
	public static double getErrorTolerance(){
		return ERROR_TOLERANCE;
	}
	public static void setErrorTolerance(double tolerance){
		if(Math.abs(tolerance)<=MAX_ERROR_TOLERANCE){
			ERROR_TOLERANCE=tolerance;
		}
	}
	
	public boolean isPolar(){
		return (flag&FLAG_POLAR)!=0;
	}
	public boolean isRectangular(){
		return (flag&FLAG_RECT)!=0;
	}
	public boolean isntPolar(){
		return (flag&FLAG_POLAR)==0;
	}
	public boolean isntRectangular(){
		return (flag&FLAG_RECT)==0;
	}
	public boolean isBoth(){
		return (flag&FLAG_BOTH)==FLAG_BOTH;
	}
	public boolean isVertical(){
		if(isntRectangular()){
			convertRectangular();
		}
		return (y!=0)&&(x==0);
	}
	public static boolean isVertical(double x, double y){
		return (y!=0)&&(x==0);
	}
	public boolean isHorizontal(){
		if(isntRectangular()){
			convertRectangular();
		}
		return (x!=0)&&(y==0);
	}
	public static boolean isHorizontal(double x, double y){
		return (x!=0)&&(y==0);
	}
	public double getSlope(){
		if(isntRectangular()){
			return Math.tan(t);
		}else{
			return y/x;
		}
	}
	public double getInverseSlope(){
		if(isntRectangular()){
			return 1/Math.tan(t);
		}else{
			return x/y;
		}
		
	}
	public void convertRectangular(){
		x = r*Math.cos(t);
		y = r*Math.sin(t);
		flag|=FLAG_RECT;
	}
	public void convertPolar(){
		r = Math.sqrt(x*x+y*y);
		t = Math.atan2(y, x);
		flag|=FLAG_POLAR;
	}
	
	public double magnitude(){
		if(isntPolar()){
			convertPolar();
		}
		return r;
	}
	public Vector2 getUnitVector(){
		if(isBoth()){
			return new Vector2(x/r,y/r,1,t);
		}else if(isntRectangular()){
			return new Vector2(1,t,FLAG_POLAR);
		}else{
			convertPolar();
			return new Vector2(x/r,y/r,1,t);
		}
	}
	
	public double getX(){
		//if(isntRectangular()){convertRectangular();}
		return x;
	}
	public double getY(){
		//if(isntRectangular()){convertRectangular();}
		return y;
	}
	public double getR(){
		//if(isntPolar()){convertPolar();}
		return r;
	}
	public double getT(){
		//if(isntPolar()){convertPolar();}
		return t;
	}
	public Vector2 subtract(Vector2 other){
		if(other.isntRectangular()){
			other.convertRectangular();
		}
		return new Vector2(x-other.x,y-other.y,FLAG_RECT);
	}
	public Vector2 add(Vector2 other){
		if(other.isntRectangular()){
			other.convertRectangular();
		}
		return new Vector2(x+other.x,y+other.y,FLAG_RECT);
	}
	public Vector2 multiply(double s){
		if(isntRectangular()){
			return new Vector2(s*r,t, FLAG_POLAR);
		}else{
			return new Vector2(s*x,s*y, FLAG_RECT);
		}
	}
	public Vector2 invert(){
		return multiply(-1);
	}

	@Override
	public boolean equals(Object obj){
		return (obj instanceof Vector2)?equals((Vector2)obj):false;
	}
	
	public boolean equals(Vector2 other){
		switch(other.flag&flag){
			case FLAG_NEITHER:
				if(isntPolar()){//isRectangular
					other.convertRectangular();
				}else{//isPolar
					convertRectangular();
				}
			case FLAG_BOTH:
			case FLAG_RECT:				
				return (approxEqual(this.x,other.x)&&approxEqual(this.y,other.y));
			//relying on cascading case statements above
			case FLAG_POLAR:
				return (approxEqual(this.r,other.r)&&approxEqual(this.t,other.t));
			default: return false;//This should never happen, but it could and therefore the complier requires it
		}
	}
	
	public boolean approxEqual(double d1, double d2){
		return Math.abs(d1-d2)<ERROR_TOLERANCE;
	}
	
	public Vector2 clone(){
		if(isBoth()){
			return new Vector2(x,y,r,t);
		}else if(isRectangular()){
			return new Vector2(x,y,FLAG_RECT);
		}else{//Polar
			return new Vector2(r,t,FLAG_POLAR);
		}
	}
}
