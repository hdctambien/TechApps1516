package spacegame.map;

public class Vector2 {

	private double x;
	private double y; 
	private double r; //radius
	private double t; //angle (theta)
	
	public static final int FLAG_POLAR = 0b10;
	public static final int FLAG_RECT = 0b01;
	public static final int FLAG_BOTH = 0b11;
	
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
	
	
}
