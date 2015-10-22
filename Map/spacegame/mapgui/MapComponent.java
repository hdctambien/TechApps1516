package spacegame.mapgui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class MapComponent extends JComponent {
	private double heading = 0, heading1;
	private int width = 50;
	private int x = 100, y = 100;
	
	private Shape shape ;
	public MapComponent(){
		shape = new Rectangle(x,y,width,width);
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		System.out.println("X,Y: "+x+ ", "+y);
		g.setColor(Color.RED);
	//	g2.drawRect(x, y, width, width);
		if(heading1 != heading)
		{	
			System.out.println("Heading1: "+heading1);
			System.out.println("Heading: "+heading);
			transform.rotate(Math.toRadians(heading1 - heading),x+width/2,y+width/2);
			shape = transform.createTransformedShape(shape);
			
			heading = heading1;
		}
		g2.draw(shape);
		
	}
	public void setHeading(double heading)
	{
		this.heading1 = heading;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	

	
}
