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

public class MapPanel{
	public int xCord, yCord;
	public JFrame frame = new JFrame();
	public JPanel mapPanel;
	public MapComponent ship;
	public void mapPanel()
	{
		Scanner input = new Scanner( System.in );
		boolean running = true;
		
		final MapClient pilot = new MapClient();
		pilot.setup();
		pilot.subscribe();
		
	
		mapPanel = new JPanel();
		mapPanel.setSize(1000,1000);
		
		ship = new MapComponent();
		ship.setPreferredSize(new Dimension(1200,600));
		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(ship, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);		
		run();
	}
	public void main(String[] args)
	{
		mapPanel();
	}
	public void run()
	{
		while(true)
		{
			ship.setPosition(xCord, yCord);
			ship.repaint();
			frame.revalidate();
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
}
