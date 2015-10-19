package spacegame.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class HeadingDial extends JComponent {

	private double heading; //in radians
	private Color dialColor;
	private Color lineColor;
	
	private int radius;
	private int padding;
	
	public static int DEFAULT_RADIUS = 30;
	public static int DEFAULT_PADDING = 10;
	
	public HeadingDial(){
		int size = 2*(DEFAULT_RADIUS+DEFAULT_PADDING);
		setPreferredSize(new Dimension(size,size));
		setSize(new Dimension(size,size));
		dialColor = Color.BLACK;
		lineColor = Color.YELLOW;
		radius = DEFAULT_RADIUS;
		padding = DEFAULT_PADDING;
	}
	
	public void setHeading(double radians){
		heading = radians;
		heading %= 2*Math.PI;
	}
	
	public double getHeading(){
		return heading;
	}
	
	public void incrementHeading(double increment){
		heading += increment;
		heading %= 2*Math.PI;
	}
	
	public void setRadius(int radius){
		this.radius = radius;
		int size = 2*(radius+padding);
		setPreferredSize(new Dimension(size,size));
		setSize(new Dimension(size,size));
	}
	
	public void setPadding(int padding){
		this.padding = padding;
		int size = 2*(radius+padding);
		setPreferredSize(new Dimension(size,size));
		setSize(new Dimension(size,size));
	}
	
	public int getRadius(){
		return radius;
	}
	
	public int getPadding(){
		return padding;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(dialColor);
		g2.fillOval(padding, padding, 2*radius, 2*radius);
		g2.setColor(lineColor);
		int offset = radius + padding;
		int x = offset+(int)Math.round(radius * Math.cos(heading));
		int y = offset-(int)Math.round(radius * Math.sin(heading));
		g2.drawLine(offset, radius+padding, x, y);
	}
}
