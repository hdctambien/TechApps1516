package mapgui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MapComponent extends JComponent {
	private double heading = 0;
	private int width = 50;
	private int x = 200, y = 200;
	

	public MapComponent(){
		
	}
	
	public void setPosition(int x, int y){
		System.out.println("Set Position "+x+", "+y);
		this.x = x;
		this.y = y;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		Shape shape = new Rectangle(x-width/2,y-width/2,width,width);
		AffineTransform transform = new AffineTransform();
		System.out.println("X,Y: "+x+ ", "+y);
		System.out.println("width: "+width);
		g.setColor(Color.RED);
		
		System.out.println("Heading1: "+heading);
		transform.rotate(Math.toRadians(heading),x,y);
		shape = transform.createTransformedShape(shape);
		
		g2.draw(shape);
		
	}
	public void setHeading(double heading)
	{
		this.heading = heading;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
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
