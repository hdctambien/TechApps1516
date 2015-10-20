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
	public int xCord, yCord;
	public double heading = 45;
	
	
	public JFrame frame = new JFrame();
	public JPanel mapPanel;
	public MapComponent map;
	public MapPanel()
	{
	/*	final MapClient pilot = new MapClient();
		pilot.setup();
		pilot.subscribe();*/
		
	
		mapPanel = new JPanel();
		mapPanel.setSize(1000,1000);
		
		map = new MapComponent();
		map.setPreferredSize(new Dimension(1200,600));

		frame.add(map, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
	
		xCord = 200;
		yCord = 200;
		map.setHeading(heading);
		map.setWidth(100);
		run();
	}
	public void run()
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
			map.setHeading(heading);
			map.setPosition(xCord, yCord);

			frame.revalidate();
			map.repaint();
		
		}
	}

	public void setY(int posY)
	{
		yCord = posY;
		System.out.println("set y to: "+ posY);
	}
	public void setX(int posX)
	{
		xCord = posX;
		System.out.println("set x to: "+ posX);
	}
	public void setHeading(double h)
	{
		heading = h;
	}
	public void setWidth(int width)
	{
		map.setWidth(width);
	}
}
