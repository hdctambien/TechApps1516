import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import spacegame.map.MapAction;

public class MapPanel extends JFrame{
	public static int xCord, yCord;
	public static double heading = 45;
	
	
	public  static JFrame frame = new JFrame();
	public  static JPanel mapPanel;
	public  static MapComponent map;
	public static void mapPanel()
	{
	/*	final MapClient pilot = new MapClient();
		pilot.setup();
		pilot.subscribe();*/
		
	
		mapPanel = new JPanel();
		mapPanel.setSize(1000,1000);
		
		map = new MapComponent();
		map.setPreferredSize(new Dimension(1200,600));

//		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(map, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
	
		xCord = 200;
		yCord = 200;
		map.setHeading(heading);
		
		run();
		
	}
	public static void main(String[] args)
	{
		mapPanel();
	}
	public static void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
			map.setHeading(0);
			map.setPosition(xCord, yCord);
			frame.revalidate();
			map.repaint();
		}
	}

	public static void setY(int posY)
	{
		yCord = posY;
		System.out.println("set y to: "+ posY);
	}
	public static void setX(int posX)
	{
		xCord = posX;
		System.out.println("set x to: "+ posX);
	}
	public static void setHeading(double h)
	{
		heading = h;
	}
}
